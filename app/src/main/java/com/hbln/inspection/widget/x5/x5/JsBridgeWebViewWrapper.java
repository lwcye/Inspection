package com.hbln.inspection.widget.x5.x5;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_common.base.BaseApp;
import com.cmcc.lib_utils.utils.AppUtils;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.NetworkUtils;
import com.cmcc.lib_utils.utils.ViewUtils;
import com.hbln.inspection.BuildConfig;
import com.hbln.inspection.R;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.inspection.widget.x5.utils.BridgeUtil;
import com.hbln.inspection.widget.x5.utils.BridgeWebView;
import com.hbln.inspection.widget.x5.utils.BridgeWebViewClient;
import com.hbln.inspection.widget.x5.utils.JsonUtil;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


/**
 * 基于JsBridge的WebView封装
 *
 * @author mos
 * @date 2017.04.18
 * @note 1. 此封装不会添加沉浸式状态栏，请在外部自行添加。
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class JsBridgeWebViewWrapper implements View.OnClickListener {
    /** 页面引用 */
    private WeakReference<BaseActivity> mActivityRef;
    /** 页面动作 */
    private IPageAction mPageAction;
    /** 页面状态监听 */
    private IPageListener mPageListener;
    /** 关闭按钮容器 */
    private ViewGroup mVgIconClose;
    /** 出错错误布局 */
    private ViewGroup mVgErrorLayout;
    /** 选项 */
    private WebViewOption mOption;
    /** 颜色选项 */
    private WebViewColorOption mColorOption;
    /** web view */
    private BridgeWebView mWebView;
    /** 加载进度 */
    private MaterialProgressBar mMpbLoadingProgress;
    /** 是否出现错误 */
    private boolean mIsError = false;
    /** 是否已经注册Js */
    private boolean mIsRegisterJs = false;
    /** 外部解析url映射 */
    private Map<String, IOverrideUrlLoading> mExternalOverrideUrlLoading = new HashMap<>();
    /** Javascript注入规则映射 */
    private Map<String, String> mJavascriptInjectionMap = new HashMap<>();
    
    /**
     * 私有化构造函数
     */
    private JsBridgeWebViewWrapper() {
    }
    
    /**
     * 获取实例
     *
     * @param activity 页面(可以为空)
     * @param containerView 容器View
     * @param option 参数
     * @param colorOption 颜色参数
     * @param action 页面动作
     * @return 实例
     */
    public static JsBridgeWebViewWrapper createInstance(BaseActivity activity, ViewGroup containerView,
                                                        WebViewOption option, WebViewColorOption colorOption,
                                                        IPageAction action, IPageListener listener) {
        if (containerView == null || activity == null) {
            
            return null;
        }
        
        JsBridgeWebViewWrapper wrapper = new JsBridgeWebViewWrapper();
        wrapper.mActivityRef = new WeakReference<>(activity);
        wrapper.mPageAction = action;
        wrapper.mPageListener = listener;
        wrapper.mOption = option;
        wrapper.mColorOption = colorOption;
        
        // 若没传递参数，则生成默认参数
        if (wrapper.mOption == null) {
            wrapper.mOption = new WebViewOption();
            wrapper.mOption.url = "";
            wrapper.mOption.title = "";
            wrapper.mOption.cookie = "";
            wrapper.mOption.showTitleBar = false;
        }
        
        // 初始化WebView
        wrapper.initWebView(activity, containerView);
        
        // 初始化数据
        wrapper.initData();
        
        return wrapper;
    }
    
    /**
     * 添加外部重写Url加载
     *
     * @param startUrl url的开始部分
     * @param overrideUrlLoading 重写Url加载回调
     * @note 若当前加载的url匹配url的开始部分，则交给overrideUrlLoading处理。例如：
     * 页面的跳转url为"https://www.baidu.com/s?wd=virtualbox"，那么https://www.baidu.com或
     * www.baidu.com均匹配
     */
    public void addExternalOverrideUrlLoading(String startUrl, IOverrideUrlLoading overrideUrlLoading) {
        if (TextUtils.isEmpty(startUrl) || overrideUrlLoading == null) {
            
            return;
        }
        
        mExternalOverrideUrlLoading.put(startUrl, overrideUrlLoading);
    }
    
    /**
     * 添加javascript注入
     *
     * @param startUrl url的起始部分
     * @param javascriptPath javascript文件路径
     * @note 若startUrl为*，那么对应的 javascriptPath会直接注入
     */
    public void addJavascriptInjection(String startUrl, String javascriptPath) {
        if (TextUtils.isEmpty(startUrl) || TextUtils.isEmpty(javascriptPath)) {
            
            return;
        }
        
        mJavascriptInjectionMap.put(startUrl, javascriptPath);
    }
    
    /**
     * 注册Js回调
     *
     * @note 必须在createInstance之后执行。
     */
    public void registerJsCallback() {
        //  初始化必须在这里执行，顺序不能乱
        initWebViewJsCallback();
        
        if (!mIsRegisterJs) {
            mIsRegisterJs = true;
            // 注册Js回调到WebView中
            WebViewManager.getInstance().registerJsCallback(mWebView);
        }
    }
    
    /**
     * 初始化WebView相关的Js回调
     */
    private void initWebViewJsCallback() {
        // 在这里添加需要操作WebView的Js回调
    }
    
    /**
     * 注入javascript代码
     *
     * @param webview webview
     * @param url url
     */
    private void injectJavascript(WebView webview, String url) {
        List<String> injectedRecord = new ArrayList<>();
        
        for (Map.Entry<String, String> entry : mJavascriptInjectionMap.entrySet()) {
            String startUrl = entry.getKey();
            String javascriptPath = entry.getValue();
            if ("*".equals(startUrl) || WebViewManager.matchStartUrl(url, startUrl)) {
                if (!injectedRecord.contains(javascriptPath)) {
                    // 规则匹配则注入javascript
                    BridgeUtil.webViewLoadLocalJs(webview, javascriptPath);
                    // 调用注入完成
                    webview.loadUrl("javascript:try{onJsInjected();}catch(e){}");
                    // 标记为已注入
                    injectedRecord.add(javascriptPath);
                }
                
                continue;
            }
        }
    }
    
    /**
     * 释放
     *
     * @note 页面结束时，一定要调用此函数，避免内存泄露
     */
    public void onDestroy() {
        // 隐藏loading
        mPageAction.onHideIndeterminateLoading();
        
        // 清除Cookie
        WebViewSession.clearCookie();
        
        // 释放WebView
        if (mWebView != null) {
            mWebView.setOnKeyListener(null);
            mWebView.setWebViewClient(null);
            mWebView.setDownloadListener(null);
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
    
    /**
     * 初始化WebView
     *
     * @param containerView 容器view
     * @param activity 页面
     * @parma inflater 填充器
     */
    private void initWebView(BaseActivity activity, ViewGroup containerView) {
        LayoutInflater inflater = activity.getLayoutInflater();
        
        // 加载布局
        inflater.inflate(R.layout.layout_js_bridge_web_view, containerView);
        
        // 配置标题栏
        TitleUtil.attach(mActivityRef.get())
            .setTitle(mOption.title)
            .setBack(true);
        
        // 动态创建WebView
        ViewGroup vgContainer = (ViewGroup) containerView.findViewById(R.id.rellay_web_view_container);
        mWebView = WebViewManager.getInstance().createWebView(activity, vgContainer);
        
        // Debug下允许Chrome调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        
        // 配置WebView
        WebSettings settings = mWebView.getSettings();
        // 自适应窗口
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);
        // 允许本地存储数据
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        // 允许访问文件
        settings.setAllowFileAccess(true);
        //允许视频自动播放
        enableX5FullscreenFunc();
        // 是否允许访问缓存
        if (mOption.loadCache) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        
        // 屏蔽掉长按时间
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        
        // 允许调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (BaseApp.getInstance().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        
        // 允许下载
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 使用系统浏览器下载
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseApp.getInstance().startActivity(intent);
            }
        });
        
        // 状态处理
        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                
                // 跳转到统一的错误处理页面
                showErrorPage(view, description);
            }
            
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.d("lwc", "url = " + url);
                // 网络图片延迟加载
                if (mOption.blockNetworkImage) {
                    view.getSettings().setBlockNetworkImage(true);
                }
                
                // 隐藏错误页面
                hideErrorPage();
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 注入Javascript代码
                injectJavascript(view, url);
                
                // 网络图片延迟加载
                if (mOption.blockNetworkImage) {
                    view.getSettings().setBlockNetworkImage(false);
                }
                
                // 页面加载结束，若可以返回则显示关闭按钮
                if (view.canGoBack()) {
                    mVgIconClose.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /** 调试打印 */
                if (AppUtils.isAppDebug()) {
                    LogUtils.d("shouldOverrideUrlLoading: " + url);
                }
                
                // 通用处理
                final int process = WebViewManager.getInstance().processShouldOverrideUrlLoading(view, url);
                if (process != WebViewManager.PROCESS_NOTHING) {
                    // 通用处理失败后，在这里拦截
                    
                    return true;
                }
                
                // 外部重写优先处理
                boolean disposed = false;
                for (Map.Entry<String, IOverrideUrlLoading> entry : mExternalOverrideUrlLoading.entrySet()) {
                    String startUrl = entry.getKey();
                    if (WebViewManager.matchStartUrl(url, startUrl)) {
                        IOverrideUrlLoading overrideUrlLoading = entry.getValue();
                        disposed = overrideUrlLoading.onOverrideUrlLoading(view, url, mPageAction);
                        
                        break;
                    }
                }
                if (disposed) {
                    // 若外部已处理，则返回
                    
                    return true;
                }
                
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                
                //  重写标题
                setTitle(title);
            }
            
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                
                // 更新进度
                if (mOption != null && !mOption.indeterminate) {
                    // 进度条样式
                    if (newProgress == 100) {
                        mMpbLoadingProgress.setVisibility(View.GONE);
                    } else {
                        if (mMpbLoadingProgress.getVisibility() == View.GONE) {
                            mMpbLoadingProgress.setVisibility(View.VISIBLE);
                        }
                        // 由于0%的进度让用户无法感知，因此将0%故意处理为5%。
                        if (newProgress == 0) {
                            newProgress = 5;
                        }
                        mMpbLoadingProgress.setProgress(newProgress);
                    }
                } else {
                    // 转圈样式
                    if (mPageAction != null) {
                        if (newProgress == 100) {
                            mPageAction.onHideIndeterminateLoading();
                        } else {
                            mPageAction.onShowIndeterminateLoading();
                        }
                    }
                }
            }
            
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // Android 5.0+
                if (mPageAction != null) {
                    mPageAction.onOpenFileChooserAboveL(filePathCallback, fileChooserParams);
                }
                return true;
            }
            
            /**
             * 打开文件
             *
             * @param uploadMsg
             * @note For Android < 3.0
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }
            
            /**
             * 打开文件
             *
             * @param uploadMsg
             * @note For Android 3.0+
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg, acceptType, "");
            }
            
            /**
             * 打开文件
             *
             * @param uploadMsg
             * @param acceptType
             * @param capture
             * @note For Android 4.1+
             */
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                if (mPageAction != null) {
                    mPageAction.onOpenFileChooser(uploadMsg, acceptType, capture);
                }
            }
        });
        
        // 进度条(一定要放到WebView创建之后，否则z轴顺序不对)
        mMpbLoadingProgress = (MaterialProgressBar) containerView.findViewById(R.id.mpb_web_view_progress);
        mMpbLoadingProgress.bringToFront();
        
        // 创建错误布局(一定要放到WebView创建之后，否则z轴顺序不对)
        mVgErrorLayout = (ViewGroup) containerView.findViewById(R.id.vg_web_view_error_container);
        mVgErrorLayout.bringToFront();
        mVgErrorLayout.findViewById(R.id.tv_web_view_error_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLastFailingUrl();
            }
        });
    }
    
    
    /**
     * 设置标题
     *
     * @param title 标题
     */
    private void setTitle(String title) {
        if (title != null && mOption != null && mOption.overrideTitle && !mIsError) {
            TitleUtil.attach(mActivityRef.get()).setTitle(title);
        }
    }
    
    /**
     * 初始化数据
     */
    private void initData() {
        if (mOption != null) {
            // 参数修正
            if (mOption.url == null) {
                mOption.url = "";
            }
            
            if (mOption.url.startsWith("http") || mOption.url.startsWith("https")) {
                // 远程地址
                if (NetworkUtils.isConnected()) {
                    // 初始化Cookie
                    WebViewSession.setCookie(mOption.url, mOption.cookie);
                    
                    // 解析并设置header
                    final Map<String, String> headers = new HashMap<>();
                    JsonUtil.parseFlatJson(mOption.header, new JsonUtil.IParseCallback() {
                        @Override
                        public void onKeyValue(String key, String value) {
                            headers.put(key, value);
                        }
                    });
                    
                    // 状态回调
                    if (mPageListener != null) {
                        mPageListener.onBegin(mOption.url, mOption.title);
                    }
                    
                    // 加载链接
                    mWebView.loadUrl(mOption.url, headers);
                } else {
                    showErrorPage(mWebView, "network not available");
                }
            } else {
                // 其他地址
                mWebView.loadUrl(mOption.url);
            }
        }
    }
    
    /**
     * 设置视频播放全屏
     */
    private void enableX5FullscreenFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }
    
    /**
     * 回退
     */
    public void goBack() {
        if (mPageListener != null) {
            // 找到上一个地址
            WebBackForwardList webBackForwardList = mWebView.copyBackForwardList();
            int currentIndex = webBackForwardList.getCurrentIndex();
            String previousUrl = "";
            if (currentIndex > 0) {
                previousUrl = webBackForwardList.getItemAtIndex(currentIndex - 1).getUrl();
            }
            mPageListener.onBack(previousUrl);
        }
        
        if (mPageAction != null) {
            // 隐藏转圈
            mPageAction.onHideIndeterminateLoading();
        }
        if (mWebView.canGoBack()) {
            // 可以回退
            mWebView.goBack();
        } else {
            // 不能回退，则通知关闭
            if (mPageAction != null) {
                mPageAction.onFinish();
            }
        }
    }
    
    /**
     * 加载上次失败的Url
     */
    private void loadLastFailingUrl() {
        mWebView.reload();
    }
    
    /**
     * 显示错误页面
     *
     * @param view webview
     * @param reason 错误原因
     */
    private void showErrorPage(WebView view, String reason) {
        // 标记已经出现错误
        mIsError = true;
        
        // 显示错误
        TextView tvReason = ViewUtils.findViewById(mVgErrorLayout, R.id.tv_web_view_error_reason);
        if (!TextUtils.isEmpty(reason)) {
            tvReason.setText("(" + reason + ")");
        } else {
            tvReason.setText("reason");
        }
        mVgErrorLayout.setVisibility(View.VISIBLE);
    }
    
    /**
     * 隐藏错误页面
     */
    private void hideErrorPage() {
        // 若上一次是错误，则清除mWebView中内容，因为内容是"页面无法访问"
        if (mIsError) {
            mWebView.clearView();
            // 设置标题
            String title = mWebView.getTitle();
            setTitle(title);
        }
        mIsError = false;
        mVgErrorLayout.setVisibility(View.INVISIBLE);
    }
    
    /**
     * 获取WebView
     *
     * @return webview
     */
    public WebView getWebView() {
        
        return mWebView;
    }
    
    @Override
    public void onClick(View v) {
    }
    
    /**
     * 页面动作
     */
    public static interface IPageAction {
        /**
         * 显示转圈Loading
         */
        void onShowIndeterminateLoading();
        
        /**
         * 隐藏转圈Loading
         */
        void onHideIndeterminateLoading();
        
        /**
         * 显示toast
         *
         * @param msg 消息
         */
        void onShowToast(String msg);
        
        /**
         * 结束
         */
        void onFinish();
        
        /**
         * 打开文件选择器
         *
         * @param filePathCallback 文件选择器打开后的回调
         * @param acceptType acceptType
         * @param capture capture
         * @note Android 5.0版本以下
         */
        void onOpenFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture);
        
        /**
         * 打开文件选择器
         *
         * @param filePathCallback 文件选择器打开后的回调
         * @param fileChooserParams 参数
         * @note Android 5.0版本以上
         */
        void onOpenFileChooserAboveL(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
    }
    
    /**
     * 页面状态监听
     */
    public static interface IPageListener {
        /**
         * 开始加载
         *
         * @param url 地址
         */
        void onBegin(String url, String title);
        
        /**
         * 返回被点击
         *
         * @param previousUrl 上一个地址
         */
        void onBack(String previousUrl);
        
        /**
         * 结束被点击
         */
        void onFinish();
    }
    
    /**
     * 重写Url加载回调
     */
    public static interface IOverrideUrlLoading {
        /**
         * 重写Url加载
         *
         * @param webview webview
         * @param url url
         * @param pageAction 页面动作控制
         * @return true -- 已成功重写  false --  未成功重写
         */
        boolean onOverrideUrlLoading(WebView webview, String url, IPageAction pageAction);
    }
}

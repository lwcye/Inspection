package com.cmcc.inspection.widget.x5;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.widget.x5.x5.JsBridgeWebViewWrapper;
import com.cmcc.inspection.widget.x5.x5.WebViewColorOption;
import com.cmcc.inspection.widget.x5.x5.WebViewOption;
import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

/**
 * 基于JsBridge的WebViewActivity
 *
 * @author mos
 * @date 2017.04.13
 * @note 1. 若子类需要注册额外Js文件，则必须在onCreate之中，使用mWebViewWrapper.addExternalJavascriptPath完成。
 * 2. 若未传递启动参数，则显示空白页，调用者需保证，至少传递EXTRA_OPTIONS参数。
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class JsBridgeX5WebViewActivity extends BaseActivity {
    /** 选项(参数，WebViewOption) */
    public static final String EXTRA_OPTIONS = "extra_options";
    /** 颜色选项(参数，WebViewOption) */
    public static final String EXTRA_COLOR_OPTIONS = "extra_color_options";
    /** 打开文件请求码 */
    private static final int REQUEST_CODE_OPEN_FILE = 15888;

    /** WebView包装器 */
    protected JsBridgeWebViewWrapper mWebViewWrapper;
    /** WebView参数 */
    private WebViewOption mOption;
    /** WebView颜色参数 */
    private WebViewColorOption mColorOption;
    /** 文件路径回调 */
    private ValueCallback<Uri> mFilePathCallback;
    /** 文件路径回调 */
    private ValueCallback<Uri[]> mFilePathCallbackAboveL;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge_web_view);

        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        // 获取初始参数
        if (savedInstanceState != null) {
            mOption = savedInstanceState.getParcelable(EXTRA_OPTIONS);
            mColorOption = savedInstanceState.getParcelable(EXTRA_COLOR_OPTIONS);
        } else {
            mOption = getIntent().getParcelableExtra(EXTRA_OPTIONS);
            mColorOption = getIntent().getParcelableExtra(EXTRA_COLOR_OPTIONS);
        }

        // 创建WebView并添加到容器中
        ViewGroup containerView = (ViewGroup) findViewById(R.id.fralay_web_view_container);
        mWebViewWrapper = JsBridgeWebViewWrapper.createInstance(this, containerView,
                mOption, mColorOption, new JsBridgeWebViewWrapper.IPageAction() {
                    @Override
                    public void onShowIndeterminateLoading() {
                        showLoading("");
                    }

                    @Override
                    public void onHideIndeterminateLoading() {
                        hideLoading();
                    }

                    @Override
                    public void onShowToast(String msg) {
                        ToastUtils.showShortToastSafe(msg);
                    }

                    @Override
                    public void onFinish() {
                        JsBridgeX5WebViewActivity.this.finish();
                    }

                    @Override
                    public void onOpenFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
                        if (mFilePathCallback != null) {

                            return;
                        }
                        mFilePathCallback = filePathCallback;
                        openFileChooser(acceptType);
                    }

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onOpenFileChooserAboveL(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        if (mFilePathCallbackAboveL != null) {

                            return;
                        }
                        mFilePathCallbackAboveL = filePathCallback;
                        String[] acceptTypes = fileChooserParams.getAcceptTypes();
                        String acceptType = acceptTypes == null ? "" : acceptTypes[0];
                        openFileChooser(acceptType);
                    }
                }, new JsBridgeWebViewWrapper.IPageListener() {

                    @Override
                    public void onBegin(String url, String title) {
                        JsBridgeX5WebViewActivity.this.onBegin(url, title);
                    }

                    @Override
                    public void onBack(String previousUrl) {
                        JsBridgeX5WebViewActivity.this.onBack(previousUrl);
                    }

                    @Override
                    public void onFinish() {
                        JsBridgeX5WebViewActivity.this.onFinish();
                    }
                });
    }

    /**
     * 开始加载
     *
     * @param url url地址
     * @param title 标题
     */
    public void onBegin(String url, String title) {
        // 不处理
    }

    /**
     * 返回被点击
     *
     * @param previousUrl 上一个链接
     */
    public void onBack(String previousUrl) {
        // 不处理
    }

    /**
     * 结束被点击
     */
    public void onFinish() {
        // 不处理
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_OPTIONS, mOption);

        super.onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();

        // 销毁
        mWebViewWrapper.onDestroy();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();

        // 注册Js回调
        mWebViewWrapper.registerJsCallback();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 打开文件
        if (requestCode == REQUEST_CODE_OPEN_FILE) {
            if (mFilePathCallback != null) {
                handleOpenFileResult(resultCode, data);
            } else if (mFilePathCallbackAboveL != null) {
                handleOpenFileResultAboveL(resultCode, data);
            }
            mFilePathCallback = null;
            mFilePathCallbackAboveL = null;
        }
    }

    /**
     * 打开文件
     *
     * @param acceptType 类型
     */
    private void openFileChooser(String acceptType) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        if (TextUtils.isEmpty(acceptType)) {
            i.setType("*/*");
        } else {
            i.setType(acceptType);
        }
        startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_OPEN_FILE);
    }

    /**
     * 处理打开文件结果
     *
     * @param resultCode 结果
     * @param data 数据
     * @note Android 5.0以下
     */
    private void handleOpenFileResult(int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            mFilePathCallback.onReceiveValue(null);

            return;
        }

        Uri result = data.getData();
        mFilePathCallback.onReceiveValue(result);
    }

    /**
     * 处理打开文件结果
     *
     * @param data 数据
     * @note Android 5.0+
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleOpenFileResultAboveL(int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            mFilePathCallbackAboveL.onReceiveValue(null);

            return;
        }

        Uri[] results = null;

        String dataString = data.getDataString();
        ClipData clipData = data.getClipData();
        if (clipData != null) {
            results = new Uri[clipData.getItemCount()];
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                results[i] = item.getUri();
            }
        }
        if (dataString != null) {
            results = new Uri[]{Uri.parse(dataString)};
        }

        mFilePathCallbackAboveL.onReceiveValue(results);
    }

    @Override
    public void onBackPressed() {
        mWebViewWrapper.goBack();
    }
}

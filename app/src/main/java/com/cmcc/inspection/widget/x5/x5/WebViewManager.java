package com.cmcc.inspection.widget.x5.x5;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cmcc.inspection.widget.x5.bean.ActionResult;
import com.cmcc.inspection.widget.x5.utils.BridgeHandler;
import com.cmcc.inspection.widget.x5.utils.BridgeWebView;
import com.cmcc.inspection.widget.x5.utils.CallBackFunction;
import com.cmcc.inspection.widget.x5.utils.JsonUtil;
import com.cmcc.lib_common.base.BaseActivity;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * WebView管理器
 *
 * @author mos
 * @date 2017.04.03
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WebViewManager {
    /** 未处理 */
    public static int PROCESS_NOTHING = 0;
    /** 实例 */
    private static WebViewManager sOurInstance = new WebViewManager();

    static {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // 允许接收cookie
            CookieManager.getInstance().setAcceptCookie(true);
        }
    }

    /** JS回调函数映射 */
    private Map<String, JsCallback> mJs2NativeMap = new HashMap<>();

    /**
     * 私有化构造函数
     */
    private WebViewManager() {
    }

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static WebViewManager getInstance() {
        return sOurInstance;
    }

    /**
     * 是否匹配开头的Url
     *
     * @param baseUrl 基准url
     * @param compareUrl 参与比较的url
     * @return true -- 匹配 false -- 不匹配
     * @note 同时支持http，https，以及去掉协议头的字符串
     */
    public static boolean matchStartUrl(String baseUrl, String compareUrl) {
        boolean match = false;
        if (TextUtils.isEmpty(baseUrl) || TextUtils.isEmpty(compareUrl)) {

            return match;
        }

        if (baseUrl.startsWith(compareUrl)) {
            match = true;
        } else if (baseUrl.startsWith("http://") && baseUrl.substring(7).startsWith(compareUrl)) {
            match = true;
        } else if (baseUrl.startsWith("https://") && baseUrl.substring(8).startsWith(compareUrl)) {
            match = true;
        }

        return match;
    }

    /**
     * 添加js回调
     *
     * @param funcName 函数名称
     * @param callback 回调函数
     * @return this
     */
    public WebViewManager addJsCallback(String funcName, JsCallback callback) {
        if (TextUtils.isEmpty(funcName) || callback == null) {

            return this;
        }
        mJs2NativeMap.put(funcName, callback);

        return this;
    }

    /**
     * 获取Js调用Native的映射
     *
     * @return 映射map
     */
    private Map<String, JsCallback> getJs2NativeMap() {

        return mJs2NativeMap;
    }

    /**
     * 清除所有回调
     */
    public void clearJsCallbacks() {
        mJs2NativeMap.clear();
    }

    /**
     * 在容器中创建WebView
     *
     * @param activity 页面
     * @param container 容器
     * @return web view对象
     */
    public BridgeWebView createWebView(BaseActivity activity, ViewGroup container) {
        if (container == null || activity == null) {

            return null;
        }

        final BridgeWebView webView = new BridgeWebView(activity);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(webView);

        // 默认调用处理
        webView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                // 暂不处理
            }
        });

        // 按键处理
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 返回按键处理
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (webView.canGoBack()) {
                        webView.goBack();

                        return true;
                    }
                }

                return false;
            }
        });

        return webView;
    }

    /**
     * 注册Js回调
     *
     * @param webView BridgeWebView
     */
    public void registerJsCallback(BridgeWebView webView) {
        Map<String, JsCallback> js2NativeCallbacks = WebViewManager.getInstance().getJs2NativeMap();
        for (Map.Entry<String, JsCallback> entry : js2NativeCallbacks.entrySet()) {
            final String funcName = entry.getKey();
            final JsCallback callback = entry.getValue();

            webView.registerHandler(funcName, new BridgeHandler() {
                @Override
                public void handler(final String data, final CallBackFunction function) {
                    // 此函数在主线程被回调
                    Observable.just(null)
                            .compose(new Observable.Transformer<Object, Object>() {
                                @Override
                                public Observable<Object> call(Observable<Object> objectObservable) {
                                    //  确定执行线程
                                    if (callback.getThreadMode() == JsCallback.THREAD_MODE_IO) {

                                        return objectObservable.subscribeOn(Schedulers.io());
                                    }

                                    return objectObservable;
                                }
                            })
                            .map(new Func1<Object, String>() {
                                @Override
                                public String call(Object o) {
                                    // 数据转对象，并回调
                                    ActionResult innerResult;
                                    if (data == null) {
                                        innerResult = callback.onJsCallback(null);
                                    } else {
                                        innerResult = callback.onJsCallback(JsonUtil.stringToObject(data,
                                                callback.getTypeClass()));
                                    }

                                    return JsonUtil.objectToString(innerResult);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String actionResult) {
                                    function.onCallBack(actionResult);
                                }
                            });
                }
            });
        }
    }

    /**
     * 通用处理Url
     *
     * @param view web view
     * @param url url
     * @return 参见处理结果(PROCESS_NOTHING等)
     */
    public int processShouldOverrideUrlLoading(WebView view, String url) {

        return PROCESS_NOTHING;
    }

    /**
     * 会话监听
     */
    public static interface ISessionListener {
        /**
         * 会话过期回调
         *
         * @param updater cookie更新器
         * @param url url
         */
        public void onSessionTimeout(ICookieUpdater updater, String url);
    }

    /**
     * Js回调类
     *
     * @note T为Js调用Native时，传递的参数(JSON -> Class)
     */
    public static abstract class JsCallback<T, R extends ActionResult> {
        /** 主线程调度 */
        public static final int THREAD_MODE_MAIN = 0;
        /** IO线程调度 */
        public static final int THREAD_MODE_IO = 1;

        /** 类型 */
        private Class<T> mTypeClass;
        /** 调度线程类型 */
        private int mThreadMode;

        /**
         * 构造函数
         *
         * @param typeClass 回调参数类型
         * @param threadMode 调度线程类型(参见THREAD_MODE_MAIN等)
         */
        public JsCallback(Class<T> typeClass, int threadMode) {
            mTypeClass = typeClass;
            mThreadMode = threadMode;
        }

        /**
         * 构造函数
         *
         * @param typeClass 回调参数类型
         */
        public JsCallback(Class<T> typeClass) {
            mTypeClass = typeClass;
            mThreadMode = THREAD_MODE_MAIN;
        }

        /**
         * Js回调
         *
         * @param data js传递给native的数据
         * @return native处理后返回给js的数据
         * @note 子类重写此函数时，需要对参数进行判断，因为js调用可能传递参数错误
         */
        public abstract R onJsCallback(T data);

        /**
         * 获取类型
         *
         * @return 类型
         */
        public Class<T> getTypeClass() {

            return mTypeClass;
        }

        /**
         * 回去线程调度类型
         *
         * @return 调度类型
         */
        public int getThreadMode() {

            return mThreadMode;
        }
    }

    /**
     * 基于页面的Js回调类
     *
     * @note T为Js调用Native时，传递的参数(JSON -> Class)
     */
    public static abstract class ActivityJsCallback<T> extends JsCallback<T, ActionResult> {
        /** Activity弱引用 */
        private WeakReference<BaseActivity> mBaseActivityRef;

        /**
         * 构造函数
         *
         * @param activity activity
         * @param typeClass 模板参数T的class类型
         */
        public ActivityJsCallback(BaseActivity activity, Class typeClass) {
            super(typeClass);
            mBaseActivityRef = new WeakReference<>(activity);
        }

        /**
         * 构造函数
         *
         * @param activity activity
         * @param typeClass 类型
         * @param threadMode 调度线程类型(参见THREAD_MODE_MAIN等)
         */
        public ActivityJsCallback(BaseActivity activity, Class typeClass, int threadMode) {
            super(typeClass, threadMode);
            mBaseActivityRef = new WeakReference<>(activity);
        }

        /**
         * 获取Activity
         *
         * @return BaseActivity
         */
        public BaseActivity getActivity() {
            return mBaseActivityRef.get();
        }
    }
}

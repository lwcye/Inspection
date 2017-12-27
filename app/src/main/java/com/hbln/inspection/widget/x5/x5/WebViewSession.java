package com.hbln.inspection.widget.x5.x5;

import android.os.Build;
import android.text.TextUtils;

import com.cmcc.lib_common.base.BaseApp;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;

/**
 * WebView的会话
 *
 * @author mos
 * @date 2017.04.17
 * @note 若WebView需要Cookie支持，请在loadUrl之前，通过此类设置Cookie
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WebViewSession implements ICookieUpdater {
    /** WebView对象 */
    private WebView mWebView;

    /**
     * 构造函数
     *
     * @param view web view
     */
    public WebViewSession(WebView view) {
        mWebView = view;
    }

    /**
     * 设置Cookie
     *
     * @param url url
     * @param cookie cookie
     */
    public static void setCookie(String url, String cookie) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(cookie)) {

            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(BaseApp.getInstance());
        }

        // Cookie只能逐条设置
        CookieManager cookieManager = CookieManager.getInstance();
        String[] cookies = cookie.split(";");
        if (cookies != null && cookies.length > 0) {
            for (String oneCookie : cookies) {
                cookieManager.setCookie(url, oneCookie.trim());
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            cookieManager.flush();
        }
    }

    /**
     * 清除Cookie
     */
    public static void clearCookie() {
        CookieManager.getInstance().removeAllCookie();
    }

    @Override
    public void updateCookie(String url, String cookie) {
        setCookie(url, cookie);

        // 重新加载
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }
}

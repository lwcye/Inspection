package com.cmcc.inspection.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.cmcc.lib_common.base.BaseApp;
import com.cmcc.lib_utils.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/11/9
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MyApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        SDKInitializer.initialize(getApplicationContext());
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,null);
        UMConfigure.setLogEnabled(true);
    }
    /**
     * 初始化X5内核
     */
    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.i("X5WebView onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtils.i("X5WebView onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /**
     * Log的初始化
     */
    private void initLog() {
        // TODO: lwc 2017/12/15
        LogUtils.init(true, false, 'v', "lwc");
    }
}

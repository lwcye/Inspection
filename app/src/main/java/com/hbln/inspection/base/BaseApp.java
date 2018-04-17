package com.hbln.inspection.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.SPUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.cmcc.lib_utils.utils.Utils;
import com.hbln.inspection.constans.CommonSharePresf;
import com.hbln.inspection.utils.tinker.Log.MyLogImp;
import com.hbln.inspection.utils.tinker.util.SampleApplicationContext;
import com.hbln.inspection.utils.tinker.util.TinkerManager;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.commonsdk.UMConfigure;

import java.net.Proxy;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.functions.Action1;


/**
 * <p>Application</p><br>
 *
 * @author lwc
 * @date 2017/3/25 18:03
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.hbln.inspection.base.MyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class BaseApp extends DefaultApplicationLike {
    private static Application sBaseApp;
    /** 系统偏好设置 */
    private static SPUtils spUtils;

    /** 单列 */
    public static Application getInstance() {
        return sBaseApp;
    }

    public BaseApp(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                   long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to mLogain dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }
    /**
     * 获得SharedPreference进行操作
     *
     * @return SPUtils
     */
    public static SPUtils getSpUtils() {
        if (null == spUtils) {
            spUtils = new SPUtils(CommonSharePresf.FILE_NAME);
        }
        return spUtils;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = getApplication();
        init();
    }
    
    private void init() {
        //工具初始化
        Utils.init(getApplication());
        ToastUtils.init(true);
        // 崩溃记录
        initLog();
        initX5WebView();
        SDKInitializer.initialize(getApplication().getApplicationContext());
        UMConfigure.init(getApplication(), UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);

        // 文件下载器
        FileDownloader.init(getApplication(), new DownloadMgrInitialParams.InitCustomMaker()
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(10_000)
                        .readTimeout(10_000)
                        .proxy(Proxy.NO_PROXY)
                )));

        //极光推送
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(getApplication());            // 初始化 JPush

        Observable.just(null).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                try {
                    StatService.startStatService(getApplication().getApplicationContext(), null, StatConstants.VERSION);
                    LogUtils.e("初始化成功");
                } catch (MtaSDkException e) {
                    e.printStackTrace();
                    LogUtils.e("初始化失败");
                }
            }
        });
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
        QbSdk.initX5Environment(getApplication().getApplicationContext(), cb);
    }
    /**
     * Log的初始化
     */
    private void initLog() {
        //TODO: lwc 2017/12/15
        LogUtils.init(true, false, 'v', "cqcity");
    }
}

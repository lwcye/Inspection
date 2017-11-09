package com.cmcc.lib_common.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p> 崩溃相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 15:37
 * @note -
 * getInstance: 获取单例
 * init       : 初始化
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class CrashUtils implements UncaughtExceptionHandler {
    /** 单例 */
    private volatile static CrashUtils sInstance;
    /** 拦截器 */
    private UncaughtExceptionHandler mHandler;
    /** 初始化 */
    private boolean isInitialized;
    /** 错误文件放置位置 */
    private String crashDir;
    /** 版本名 */
    private String versionName;
    /** 版本号 */
    private int versionCode;
    
    /**
     * 构造类
     */
    private CrashUtils() {
    }
    
    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        if (sInstance == null) {
            synchronized (CrashUtils.class) {
                if (sInstance == null) {
                    sInstance = new CrashUtils();
                }
            }
        }
        return sInstance;
    }
    
    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(String filePath) {
        File file = new File(filePath);
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
    
    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init() {
        if (isInitialized) {
            return true;
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File baseCache;
            boolean isDebug = Utils.getContext().getApplicationInfo() != null && (Utils.getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            baseCache = Utils.getContext().getExternalCacheDir();
            if (baseCache == null) {
                return false;
            }
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        } else {
            File baseCache = Utils.getContext().getCacheDir();
            if (baseCache == null) {
                return false;
            }
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        try {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return isInitialized = true;
    }
    
    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        String now = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        final String fullPath = crashDir + now + ".txt";
        if (!createOrExistsFile(fullPath)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(fullPath, false));
                    pw.write(getCrashHead());
                    throwable.printStackTrace(pw);
                    Throwable cause = throwable.getCause();
                    while (cause != null) {
                        cause.printStackTrace(pw);
                        cause = cause.getCause();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(pw);
                }
            }
        }).start();
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }
    
    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
            "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
            "\nDevice Model       : " + Build.MODEL +// 设备型号
            "\nDevice CPU_ABI    : " + Build.CPU_ABI +// 主cpu,方法过时,但新方法要api21
            "\nDevice CPU_ABI2    : " + Build.CPU_ABI2 +// 副cpu,方法过时,但新方法要api21
            "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
            "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
            "\nApp VersionName    : " + versionName +
            "\nApp VersionCode    : " + versionCode +
            "\n************* Crash Log Head ****************\n\n";
    }
}

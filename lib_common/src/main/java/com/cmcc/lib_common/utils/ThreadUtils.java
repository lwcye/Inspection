package com.cmcc.lib_common.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 线程工具
 *
 * @author mos
 * @date 2017.03.15
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ThreadUtils {
    /**
     * 构造类
     */
    private ThreadUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 判断是否当前线程为UI线程
     *
     * @return true -- 是  false -- 否
     */
    public static boolean isUIThread() {

        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 判断是否当前线程为UI线程，否则抛出异常
     *
     * @param throwMsg 抛出异常时的消息
     * @return true -- 是  false -- 否
     */
    public static boolean isUIThreadOrThrow(String throwMsg) {
        if (Looper.myLooper() != Looper.getMainLooper()) {

            throw new RuntimeException(throwMsg);
        }

        return true;
    }

    /**
     * 在UI线程中执行任务
     *
     * @param runnable 任务
     * @return true -- 成功  false -- 失败
     */
    public static boolean runOnUiThread(Runnable runnable) {
        boolean success = false;

        Handler handler = new Handler(Looper.getMainLooper());
        if (handler != null) {
            handler.post(runnable);
            success = true;
        }

        return success;
    }

    /**
     * 在UI线程中执行任务
     *
     * @param runnable 任务
     * @param delayMillis 延时(毫秒)
     * @return true -- 成功  false -- 失败
     */
    public static boolean runOnUiThreadDelayed(Runnable runnable, long delayMillis) {
        boolean success = false;

        Handler handler = new Handler(Looper.getMainLooper());
        if (handler != null) {
            handler.postDelayed(runnable, delayMillis);
            success = true;
        }

        return success;
    }
}
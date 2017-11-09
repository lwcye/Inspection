package com.cmcc.lib_utils.utils;

import android.content.Context;

/**
 * <p> Utils初始化相关 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 16:00
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class Utils {
    private static Context context;
    private static SPUtils spUtils;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
        spUtils = new SPUtils("utilcode");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static SPUtils getSpUtils() {
        return spUtils;
    }
}
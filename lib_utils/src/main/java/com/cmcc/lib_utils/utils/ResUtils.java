package com.cmcc.lib_utils.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 资源类相关工具
 *
 * @author mos
 * @date 2017/3/10 15:58
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ResUtils {
    /**
     * 构造类
     */
    private ResUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取字符串资源
     *
     * @param resId 资源id
     * @return 字符串
     */
    public static String getString(int resId) {

        return Utils.getContext().getResources().getString(resId);
    }

    /**
     * 获取尺寸
     *
     * @param resId 资源id
     * @return 尺寸
     */
    public static float getDimension(int resId) {

        return Utils.getContext().getResources().getDimension(resId);
    }

    /**
     * 获取像素尺寸
     *
     * @param resId 资源id
     * @return 尺寸
     */
    public static float getDimensionPixelSize(int resId) {

        return Utils.getContext().getResources().getDimensionPixelSize(resId);
    }

    public static String getMetaData(String metaName) {
        ApplicationInfo appInfo = null;
        String msg = null;
        try {
            appInfo = Utils.getContext().getPackageManager()
                .getApplicationInfo(Utils.getContext().getPackageName(),
                    PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }
    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param gradeId 资源名
     * @return 资源id
     */
    public static int getDrawableResourceId(Context context, String gradeId) {
        return context.getApplicationContext().getResources()
            .getIdentifier(gradeId, "drawable", context.getPackageName());
    }
}
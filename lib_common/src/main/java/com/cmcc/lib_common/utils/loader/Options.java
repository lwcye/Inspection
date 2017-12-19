package com.cmcc.lib_common.utils.loader;

import android.support.annotation.DrawableRes;

/**
 * <p>下载参数设置</p><br>
 *
 * @author lwc
 * @date 2017/3/3 11:03
 * @note - 通过构造类设置参数
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class Options {
    /** 空数据 */
    public static final int RES_NONE = -1;
    /** 圆形类型 */
    public static final int TYPE_CIRCLE = 1;
    /** 圆角 */
    public static final int ROUNDED_CORNERS = 2;
    /** 微缩图显示指数 */
    public static final float THUMDNAIL_DETAIL = 0.2f;

    /** 加载中的资源id */
    int loadingResId = RES_NONE;
    /** 加载失败的资源id */
    int loadErrorResId = RES_NONE;
    /** 加载显示的微缩图 */
    float thumbnail = RES_NONE;
    /** 图片类型 */
    int type = RES_NONE;

    /**
     * 加载构造类
     *
     * @param loadingResId 加载图片
     * @param loadErrorResId 加载错误图片
     */
    public Options(@DrawableRes int loadingResId, @DrawableRes int loadErrorResId) {
        this.loadingResId = loadingResId;
        this.loadErrorResId = loadErrorResId;
    }

    /**
     * 加载构造类
     *
     * @param loadingResId 加载图片
     * @param loadErrorResId 加载错误图片
     * @param thumbnail 微缩指数
     * @param type 图片类型
     */
    public Options(@DrawableRes int loadingResId, @DrawableRes int loadErrorResId, float thumbnail, int type) {
        this.loadingResId = loadingResId;
        this.loadErrorResId = loadErrorResId;
        this.thumbnail = thumbnail;
        this.type = type;
    }

    /**
     * 加载构造类
     *
     * @param loadingResId 加载图片
     * @param loadErrorResId 加载错误图片
     * @param thumbnail 微缩指数
     */
    public Options(@DrawableRes int loadingResId, @DrawableRes int loadErrorResId, float thumbnail) {
        this.loadingResId = loadingResId;
        this.loadErrorResId = loadErrorResId;
        this.thumbnail = thumbnail;
    }

    /**
     * 加载构造类
     *
     * @param loadingResId 加载图片
     * @param loadErrorResId 加载错误图片
     * @param type 图片类型
     */
    public Options(@DrawableRes int loadingResId, @DrawableRes int loadErrorResId, int type) {
        this.loadingResId = loadingResId;
        this.loadErrorResId = loadErrorResId;
        this.type = type;
    }

    /**
     * 默认构造类，什么都不设置
     *
     * @return Options
     */
    public static Options defaultOptions() {
        return new Options(RES_NONE, RES_NONE);
    }
}

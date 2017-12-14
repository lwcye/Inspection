package com.cmcc.lib_common.utils.loader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * <p> 图片加载接口 </p><br>
 *
 * @author lwc
 * @date 2017/3/3 10:29
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface ILoader<T extends ImageView> {
    /**
     * 初始化
     *
     * @param context
     */
    void init(Context context, Options options);

    /**
     * 某些特殊图片框架需要构建特殊的 ImageView ,如 Fresco
     *
     * @param context 上下文
     */
    T createImageView(Context context);

    /**
     * 网络下载图片
     *
     * @param target ImageView控件
     * @param url 地址
     * @param options 参数设置
     */
    void loadNet(ImageView target, String url, Options options);

    /**
     * 网络下载图片
     *
     * @param target ImageView控件
     * @param url 地址
     */
    void loadNet(ImageView target, String url);

    /**
     * 资源文件加载图片
     *
     * @param target ImageView控件
     * @param resId 地址
     * @param options 参数设置
     */
    void loadResource(ImageView target, int resId, Options options);

    /**
     * 资源文件加载图片
     *
     * @param target ImageView控件
     * @param resId 地址
     */
    void loadResource(ImageView target, int resId);

    /**
     * asset加载图片
     *
     * @param target ImageView控件
     * @param assetName 地址
     * @param options 参数设置
     */
    void loadAssets(ImageView target, String assetName, Options options);

    /**
     * asset加载图片
     *
     * @param target ImageView控件
     * @param assetName 地址
     */
    void loadAssets(ImageView target, String assetName);
    /**
     * asset加载图片
     *
     * @param target ImageView控件
     * @param assetName 地址
     */
    void loadAssetsAsGif(ImageView target, String assetName);
    /**
     * 本地文件加载图片
     *
     * @param target ImageView控件
     * @param file 文件
     * @param options 参数设置
     */
    void loadFile(ImageView target, File file, Options options);

    /**
     * 本地文件加载图片
     *
     * @param target ImageView控件
     * @param file 文件
     */
    void loadFile(ImageView target, File file);

    /**
     * 清理内存缓存
     *
     * @param context 上下文
     */
    void clearMemoryCache(Context context);

    /**
     * 清理磁盘缓存
     *
     * @param context 上下文
     */
    void clearDiskCache(Context context);

}

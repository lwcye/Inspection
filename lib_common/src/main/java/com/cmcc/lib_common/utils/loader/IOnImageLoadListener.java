package com.cmcc.lib_common.utils.loader;

import android.graphics.Bitmap;

/**
 * <p>图片加载的监听器</p><br>
 *
 * @author - lwc
 * @date - 2017/9/11
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface IOnImageLoadListener {
    /**
     * 请求成功
     *
     * @param bitmap 返回的位图
     */
    void onSuccess(Object model, Bitmap bitmap);

    /**
     * 请求失败
     *
     * @param throwable 失败的throwable
     */
    void onFail(Throwable throwable);
}

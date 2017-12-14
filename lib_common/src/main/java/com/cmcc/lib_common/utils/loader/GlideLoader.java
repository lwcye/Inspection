package com.cmcc.lib_common.utils.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * <p>使用Glide框架加载图片</p><br>
 *
 * @author lwc
 * @date 2017/3/3 10:21
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class GlideLoader implements ILoader {
    private Options mOptions;

    @Override
    public void init(Context context, Options options) {
        mOptions = options;
    }

    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }

    @Override
    public void loadNet(ImageView target, String url) {
        load(getRequestManager(target.getContext()).load(url), target, mOptions);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadResource(ImageView target, int resId) {
        load(getRequestManager(target.getContext()).load(resId), target, mOptions);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, mOptions);
    }
    
    @Override
    public void loadAssetsAsGif(ImageView target, String assetName) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName).asGif(), target, mOptions);
    }
    
    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file) {
        load(getRequestManager(target.getContext()).load(file), target, mOptions);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 获得请求管理器
     *
     * @param context 上下文
     * @return 请求管理器
     */
    private RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    /**
     * 通过参数设置加载图片
     *
     * @param request Glide请求管理器
     * @param target ImageView的
     * @param options 通过构造类进行参数设置
     */
    private void load(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        if (options.thumbnail != Options.RES_NONE) {
            request.thumbnail(options.thumbnail);
        }
        if (options.type != Options.RES_NONE) {
            switch (options.type) {
                case Options.TYPE_CIRCLE:
                    request.bitmapTransform(new CropCircleTransformation(target.getContext()));
                    break;
                default:
                    break;
            }
        }
        request.crossFade().into(target);
    }
    /**
     * 通过参数设置加载图片
     *
     * @param request Glide请求管理器
     * @param target ImageView的
     * @param options 通过构造类进行参数设置
     */
    private void load(GifTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();
        
        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        if (options.thumbnail != Options.RES_NONE) {
            request.thumbnail(options.thumbnail);
        }
        request.crossFade().into(target);
    }
}

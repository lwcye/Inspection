package com.cmcc.inspection.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView的通用View保存器(RecyclerView Universal View Holder)
 *
 * @author mos
 * @date 2017.02.27
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class RUViewHolder extends RecyclerView.ViewHolder {
    /** View 数组 */
    private SparseArray<View> mViewArray;
    /** 填充的view */
    private View mItemView;
    /** 适配器 */
    private RUAdapter<?> mAdapter;
    
    /**
     * 构造函数
     *
     * @param adapter 适配器
     * @param itemView 填充的view
     */
    public RUViewHolder(RUAdapter<?> adapter, View itemView) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
        mViewArray = new SparseArray<View>();
    }
    
    /**
     * 获取Holder
     *
     * @param adapter 适配器
     * @param context 上下文
     * @param parent 父容器
     * @param layoutId 布局id
     * @return View保存器
     */
    public static RUViewHolder getHolder(RUAdapter<?> adapter, Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        
        return new RUViewHolder(adapter, itemView);
    }
    
    /**
     * 通过资源id获得View
     *
     * @param resId 资源id
     * @return view对象
     * @note 若此View未缓存，则自动缓存
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V getViewById(int resId) {
        View v = mViewArray.get(resId);
        
        if (v == null) {
            v = mItemView.findViewById(resId);
            mViewArray.put(resId, v);
            
            // 第一次view被缓存时的回调
            mAdapter.onViewCached(this, v, resId);
        }
        
        return (V) v;
    }
    
    /**
     * 获取当前位置
     *
     * @return 当前位置
     */
    public int getHolderPosition() {
        
        return getLayoutPosition();
    }
    
    /**
     * 设置文本
     *
     * @param resId 资源id
     * @param text 文本
     * @return this
     */
    public RUViewHolder setText(int resId, String text) {
        if (!TextUtils.isEmpty(text)) {
            ((TextView) getViewById(resId)).setText(text);
        }
        return this;
    }
    
    public RUViewHolder setHint(int resId, String text) {
        if (!TextUtils.isEmpty(text)) {
            ((TextView) getViewById(resId)).setHint(text);
        }
        return this;
    }
    
    public RUViewHolder setEnabled(int resId, boolean enabled) {
        getViewById(resId).setEnabled(enabled);
        return this;
    }
    
    /**
     * 设置可见性
     *
     * @param resId 资源id
     * @param visibility 可见性
     * @return this
     */
    public RUViewHolder setVisibility(int resId, int visibility) {
        getViewById(resId).setVisibility(visibility);
        return this;
    }
    
    /**
     * 设置字体颜色
     *
     * @param resId 资源id
     * @param color 颜色
     * @return this
     */
    public RUViewHolder setTextColor(int resId, @ColorInt int color) {
        ((TextView) getViewById(resId)).setTextColor(color);
        return this;
    }
    
    /**
     * 设置item的背景颜色
     *
     * @param color 颜色
     * @return this
     */
    public RUViewHolder setBackgroundColor(@ColorInt int color) {
        itemView.setBackgroundColor(color);
        return this;
    }
    
    /**
     * 设置item的背景颜色
     *
     * @param color 颜色
     * @return this
     */
    public RUViewHolder setBackgroundColor(int resId, @ColorInt int color) {
        getViewById(resId).setBackgroundColor(color);
        return this;
    }
    
    /**
     * 设置item的背景颜色
     *
     * @param drawableId 背景资源id
     * @return this
     */
    public RUViewHolder setBackgroundResource(@DrawableRes int drawableId) {
        itemView.setBackgroundResource(drawableId);
        return this;
    }
    
    /**
     * 设置item的背景颜色
     *
     * @param drawableId 背景资源id
     * @return this
     */
    public RUViewHolder setBackgroundResource(int resId, @DrawableRes int drawableId) {
        getViewById(resId).setBackgroundResource(drawableId);
        return this;
    }
    
    /**
     * 设置图片
     *
     * @param resId 资源id
     * @param bitmap 位图
     * @return this
     */
    public RUViewHolder setImageView(int resId, Bitmap bitmap) {
        ((ImageView) getViewById(resId)).setImageBitmap(bitmap);
        return this;
    }
    
    /**
     * 设置图片
     *
     * @param viewResId 资源id
     * @param resId 图片id
     * @return this
     */
    public RUViewHolder setImageView(int viewResId, int resId) {
        ((ImageView) getViewById(viewResId)).setImageResource(resId);
        return this;
    }
    
    /**
     * 设置监听
     *
     * @param resId 资源id
     * @param onClickListener 监听
     * @return this
     */
    public RUViewHolder setOnClickListener(int resId, View.OnClickListener onClickListener) {
        getViewById(resId).setOnClickListener(onClickListener);
        return this;
    }
    
    public RUViewHolder addTextChangedListener(int resId, TextWatcher textWatcher) {
        ((EditText) getViewById(resId)).addTextChangedListener(textWatcher);
        return this;
    }
}

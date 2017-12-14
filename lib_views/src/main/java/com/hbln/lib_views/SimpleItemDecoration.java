package com.hbln.lib_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 为RecyclerView添加间隔
 *
 * @author lwc
 * @date 2017/3/1 14:56
 * @note - RecyclerView.addItemDecoration
 * 默认添加listDivider的颜色和大小
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    /** 横向列表的间隔 */
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    /** 竖向列表的间隔 */
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    /** 默认加载Drawable属性 */
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    /** ItemDecoration的drawable */
    private Drawable mDivider;
    /** 方向 */
    private int mOrientation;

    /**
     * 构造类
     *
     * @param context 上下文,当前Activity
     * @param orientation 设置列表的方向：SimpleItemDecoration.HORIZONTAL_LIST or SimpleItemDecoration.VERTICAL_LIST
     */
    public SimpleItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * 设置列表的方向
     *
     * @param orientation SimpleItemDecoration.HORIZONTAL_LIST or SimpleItemDecoration.VERTICAL_LIST
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    /**
     * 设置背景drawable属性
     *
     * @param context 上下文,最好传Application
     * @param dividerResId drawable的resid
     */
    public void setDrawable(Context context, @DrawableRes int dividerResId) {
        mDivider = ContextCompat.getDrawable(context, dividerResId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 通过画布draw方向在Vertical的间隔
     *
     * @param c 画布
     * @param parent RecyclerView
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 通过画布draw方向在Horizontal的间隔
     *
     * @param c 画布
     * @param parent RecyclerView
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
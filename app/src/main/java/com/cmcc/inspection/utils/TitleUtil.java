package com.cmcc.inspection.utils;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.lib_common.utils.ViewUtils;
import com.jaeger.library.StatusBarUtil;


/**
 * <p>标题帮助类</p><br>
 *
 * @author lwc
 * @date 2017/4/2 8:53
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TitleUtil {
    private LinearLayout mToolbar;
    private TextView mCenter;
    private TextView mLeft;
    private TextView mRight;
    
    public static TitleUtil attach(AppCompatActivity activity) {
        TitleUtil titleUtil = new TitleUtil();
        titleUtil.mToolbar = (LinearLayout) activity.findViewById(R.id.ll_title);
        
        if (!TextUtils.isEmpty(activity.getTitle()) || activity.getTitle().equals(activity.getString(R.string.app_name))) {
            titleUtil.mCenter = (TextView) activity.findViewById(R.id.center);
            titleUtil.mLeft = (TextView) activity.findViewById(R.id.left);
            titleUtil.mRight = (TextView) activity.findViewById(R.id.right);
            if (titleUtil.mCenter != null) {
                titleUtil.mCenter.setText(activity.getTitle());
            }
        }
        return titleUtil;
    }
    
    public static TitleUtil attach(View view) {
        TitleUtil titleUtil = new TitleUtil();
        titleUtil.mToolbar = (LinearLayout) view.findViewById(R.id.ll_title);
        titleUtil.mCenter = (TextView) view.findViewById(R.id.center);
        titleUtil.mLeft = (TextView) view.findViewById(R.id.left);
        titleUtil.mRight = (TextView) view.findViewById(R.id.right);
        titleUtil.mToolbar.bringToFront();
        return titleUtil;
    }
    
    /**
     * 设置状态栏颜色
     *
     * @param color 颜色
     * @param alpha 透明度
     */
    public TitleUtil setColor(int color, @IntRange(from = 0, to = 255) int alpha) {
        StatusBarUtil.setColor((Activity) mToolbar.getContext(), color, alpha);
        mToolbar.setBackgroundColor(color);
        mToolbar.getBackground().setAlpha(alpha);
        return this;
    }
    
    public TitleUtil setTitle(String title) {
        if (null != mCenter) {
            mCenter.setText(title);
        }
        return this;
    }
    
    public TitleUtil setLeft(String title) {
        if (null != mLeft) {
            mLeft.setText(title);
        }
        return this;
    }
    
    public TitleUtil setLeftDrawable(int left, int top, int right, int bottom) {
        if (null != mLeft) {
            ViewUtils.setTextDrawable(mLeft, left, top, right, bottom, mLeft.getContext());
        }
        return this;
    }
    
    public TitleUtil setLeftColor(@ColorInt int color) {
        if (null != mLeft) {
            mLeft.setTextColor(color);
        }
        return this;
    }
    
    public TitleUtil setLeftClickListener(View.OnClickListener leftClickListener) {
        if (null != mLeft) {
            mLeft.setOnClickListener(leftClickListener);
        }
        return this;
    }
    
    public TitleUtil setRight(String title) {
        if (null != mRight) {
            mRight.setText(title);
        }
        return this;
    }
    
    public TitleUtil setRightColor(@ColorInt int color) {
        if (null != mRight) {
            mRight.setTextColor(color);
        }
        return this;
    }
    
    public TitleUtil setRightDrawable(int left, int top, int right, int bottom) {
        if (null != mRight) {
            ViewUtils.setTextDrawable(mRight, left, top, right, bottom, mRight.getContext());
        }
        return this;
    }
    
    public TitleUtil setRightClickListener(View.OnClickListener rightClickListener) {
        if (null != mRight) {
            mRight.setOnClickListener(rightClickListener);
        }
        return this;
    }
}

package com.cmcc.lib_common.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * <p>视图相关的Utils</p><br>
 *
 * @author lwc
 * @date 2017/3/14 18:18
 * @note -
 * findViewByXY :在View上的x,y值获得子View
 * getTouchTarget :通过xy在View上获得点击的目标view
 * isTouchPointInView :判断xy是否在view的大小内
 * setTextDrawable :给TextView设置Drawable,如果不设置，传0
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ViewUtils {
    /**
     * 构造类
     */
    private ViewUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 在View上的x,y值获得子View
     *
     * @param view 根View
     * @param x x值
     * @param y y值
     * @return child view
     */
    public static View findViewByXY(View view, int x, int y) {
        View targetView = null;
        if (view instanceof ViewGroup) {
            // 父容器,遍历子控件
            ViewGroup v = (ViewGroup) view;
            for (int i = 0; i < v.getChildCount(); i++) {
                targetView = findViewByXY(v.getChildAt(i), x, y);
                if (targetView != null) {
                    break;
                }
            }
        } else {
            targetView = getTouchTarget(view, x, y);
        }
        return targetView;

    }

    /**
     * 通过xy在View上获得点击的目标view
     *
     * @param view xy的View
     * @param x x值
     * @param y y值
     * @return view
     */
    public static View getTouchTarget(View view, int x, int y) {
        View targetView = null;
        // 判断view是否可以聚焦
        List<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }

    /**
     * 判断xy是否在view的大小内
     *
     * @param view 控件
     * @param x x值
     * @param y y值
     * @return 在View内返回true, 否则返回false
     */
    public static boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return view.isClickable() && y >= top && y <= bottom && x >= left
                && x <= right;
    }

    /**
     * 给TextView设置Drawable,如果不设置，传0
     *
     * @param view TextView
     * @param drawbleLeftRes leftDrawable ResId
     * @param drawbleTopRes topDrawable ResId
     * @param drawbleRightRes rightDrawable ResId
     * @param drawbleBottomRes bottomDrawable ResId
     * @param context Context
     */
    public static void setTextDrawable(TextView view, @DrawableRes int drawbleLeftRes, @DrawableRes int drawbleTopRes, @DrawableRes int drawbleRightRes,
                                       @DrawableRes int drawbleBottomRes, Context context) {
        Drawable leftDrawable = null;
        Drawable topDrawable = null;
        Drawable rightDrawable = null;
        Drawable bottomDrawable = null;

        if (drawbleLeftRes != -1 && drawbleLeftRes != 0) {
            leftDrawable = ContextCompat.getDrawable(context.getApplicationContext(), drawbleLeftRes);
            // 这一步必须要做,否则不会显示.
            if (leftDrawable != null) {
                leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            }
        }

        if (drawbleTopRes != -1 && drawbleTopRes != 0) {
            topDrawable = ContextCompat.getDrawable(context.getApplicationContext(), drawbleTopRes);
            if (topDrawable != null) {
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            }
        }

        if (drawbleRightRes != -1 && drawbleRightRes != 0) {
            rightDrawable = ContextCompat.getDrawable(context.getApplicationContext(), drawbleRightRes);
            if (rightDrawable != null) {
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            }
        }

        if (drawbleBottomRes != -1 && drawbleBottomRes != 0) {
            bottomDrawable = ContextCompat.getDrawable(context.getApplicationContext(), drawbleBottomRes);
            if (bottomDrawable != null) {
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
            }
        }

        view.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

    /**
     * ImageView 添加 selector 颜色
     *
     * @param imageView imageView
     * @param drawableRes 背景图片
     * @param colorNormalResId 正常颜色
     * @param colorSelectedResId 点击的颜色
     */
    public static void setSelector(ImageView imageView, @DrawableRes int drawableRes, @ColorRes int colorNormalResId, @ColorRes int colorSelectedResId) {
        Drawable drawable = ContextCompat.getDrawable(Utils.getContext(), drawableRes);
        int[] colors = new int[]{ContextCompat.getColor(Utils.getContext(), colorNormalResId), ContextCompat.getColor(Utils.getContext(), colorSelectedResId)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);//注意顺序
        stateListDrawable.addState(states[1], drawable);
        Drawable.ConstantState state = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();
        DrawableCompat.setTintList(drawable, colorList);
        imageView.setImageDrawable(drawable);
    }

    /**
     * 通过代码写出 shape_normal 和 shape_press，
     * 再通过代码为一个{View} 设置 selector
     *
     * @param view 需要设置press_state 的控件
     * @param solidNormal normal状态下的solid
     * @param solidPress press状态下的solid
     * @param radius 拐角
     * @param strokeColor 边缘颜色
     * @param strokeWidth 边缘宽度
     */
    public static void setViewSelector(@NonNull View view, @ColorInt int solidNormal, @ColorInt int solidPress, int radius, @ColorInt int strokeColor, int strokeWidth) {
        // normal
        GradientDrawable gd_n = new GradientDrawable();
        gd_n.setColor(solidNormal);
        gd_n.setCornerRadius(radius);
        gd_n.setStroke(strokeWidth, strokeColor);

        // selected
        GradientDrawable gd_p = new GradientDrawable();
        gd_p.setColor(solidPress);
        gd_p.setCornerRadius(radius);
        gd_p.setStroke(strokeWidth, strokeColor);

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, gd_p);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, gd_n);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}

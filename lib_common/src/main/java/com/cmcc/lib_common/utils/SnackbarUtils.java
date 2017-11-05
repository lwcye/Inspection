package com.cmcc.lib_common.utils;

import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * <p> Snackbar相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 15:59
 * @note -
 * showShortSnackbar     : 显示短时snackbar
 * showLongSnackbar      : 显示长时snackbar
 * showIndefiniteSnackbar: 显示自定义时长snackbar
 * addView               : 为SnackBar添加布局
 * dismissSnackbar       : 取消snackbar显示
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class SnackbarUtils {
    /** 弱引用Snackbar */
    private static WeakReference<Snackbar> sSnackbarWeakReference;

    /**
     * 构造类
     */
    private SnackbarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 显示短时snackbar
     *
     * @param parent 父视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     */
    public static void showShortSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示短时snackbar
     *
     * @param parent 父视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     * @param actionText 事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener 监听器
     */
    public static void showShortSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor,
                                         CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent 视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     */
    public static void showLongSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent 视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     * @param actionText 事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener 监听器
     */
    public static void showLongSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor,
                                        CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent 父视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_INDEFINITE, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent 父视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param textColor 文本颜色
     * @param bgColor 背景色
     * @param actionText 事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener 监听器
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, @ColorInt int textColor, @ColorInt int bgColor,
                                              CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_INDEFINITE, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 设置snackbar文字和背景颜色
     *
     * @param parent 父视图(CoordinatorLayout或者DecorView)
     * @param text 文本
     * @param duration 显示时长
     * @param textColor 文本颜色
     * @param bgColor 背景色
     * @param actionText 事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener 监听器
     */
    private static void showSnackbar(View parent, CharSequence text,
                                     int duration,
                                     @ColorInt int textColor, @ColorInt int bgColor,
                                     CharSequence actionText, int actionTextColor,
                                     View.OnClickListener listener) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(textColor);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sSnackbarWeakReference = new WeakReference<>(Snackbar.make(parent, spannableString, duration));
        Snackbar snackbar = sSnackbarWeakReference.get();
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);
        if (actionText != null && actionText.length() > 0 && listener != null) {
            snackbar.setActionTextColor(actionTextColor);
            snackbar.setAction(actionText, listener);
        }
        snackbar.show();
    }

    /**
     * 为snackbar添加布局
     * <p>在show...Snackbar之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index 位置(the position at which to add the child or -1 to add last)
     */
    public static void addView(int layoutId, int index) {
        Snackbar snackbar = sSnackbarWeakReference.get();
        if (snackbar != null) {
            View view = snackbar.getView();
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            layout.addView(child, index, params);
        }
    }

    /**
     * 取消snackbar显示
     */
    public static void dismissSnackbar() {
        if (sSnackbarWeakReference != null && sSnackbarWeakReference.get() != null) {
            sSnackbarWeakReference.get().dismiss();
            sSnackbarWeakReference = null;
        }
    }
}

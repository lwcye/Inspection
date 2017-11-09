package com.cmcc.lib_utils.utils;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * <p> 吐司相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 16:00
 * @note -
 * init              : 吐司初始化
 * showShortToastSafe: 安全地显示短时吐司
 * showLongToastSafe : 安全地显示长时吐司
 * showShortToast    : 显示短时吐司
 * showLongToast     : 显示长时吐司
 * cancelToast       : 取消吐司显示
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ToastUtils {
    /** 实体 */
    private static Toast sToast;
    /** 主线程handle */
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    /** 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容 */
    private static boolean isJumpWhenMore;
    /** Toast默认位置 */
    private static final int GRAVITY_DETAIL = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    /**
     * 构造类
     */
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     * <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     * <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(final CharSequence text, final int gravity) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT, gravity);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args 参数
     */
    public static void showShortToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args 参数
     */
    public static void showShortToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToastSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args 参数
     */
    public static void showLongToastSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args 参数
     */
    public static void showLongToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args 参数
     */
    public static void showShortToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args 参数
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args 参数
     */
    public static void showLongToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args 参数
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId 资源Id
     * @param duration 显示时长
     */
    private static void showToast(@StringRes int resId, int duration) {
        showToast(Utils.getContext().getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId 资源Id
     * @param duration 显示时长
     * @param args 参数
     */
    private static void showToast(@StringRes int resId, int duration, Object... args) {
        showToast(String.format(Utils.getContext().getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format 格式
     * @param duration 显示时长
     * @param args 参数
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration, int gravity) {
        Resources resources = Utils.getContext().getResources();
        int resourceId = resources.getIdentifier("toast_y_offset", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        showToast(text, duration, gravity, 0, height);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration) {
        Resources resources = Utils.getContext().getResources();
        int resourceId = resources.getIdentifier("toast_y_offset", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        showToast(text, duration, GRAVITY_DETAIL, 0, height);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration, int gravity, int xOffSet, int yOffSet) {
        if (isJumpWhenMore) {
            cancelToast();
        }
        if (sToast == null) {
            sToast = Toast.makeText(Utils.getContext(), text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
            sToast.setGravity(gravity, xOffSet, yOffSet);
        }
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
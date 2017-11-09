package com.cmcc.lib_utils.utils;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import static android.graphics.BlurMaskFilter.Blur;

/**
 * <p> SpannableString相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 15:59
 * @note -
 * getBuilder        : 获取建造者
 * setFlag           : 设置标识
 * setForegroundColor: 设置前景色
 * setBackgroundColor: 设置背景色
 * setQuoteColor     : 设置引用线的颜色
 * setLeadingMargin  : 设置缩进
 * setBullet         : 设置列表标记
 * setProportion     : 设置字体比例
 * setXProportion    : 设置字体横向比例
 * setStrikethrough  : 设置删除线
 * setUnderline      : 设置下划线
 * setSuperscript    : 设置上标
 * setSubscript      : 设置下标
 * setBold           : 设置粗体
 * setItalic         : 设置斜体
 * setBoldItalic     : 设置粗斜体
 * setFontFamily     : 设置字体
 * setAlign          : 设置对齐
 * setBitmap         : 设置图片
 * setDrawable       : 设置图片
 * setUri            : 设置图片
 * setResourceId     : 设置图片
 * setClickSpan      : 设置点击事件
 * setUrl            : 设置超链接
 * setBlur           : 设置模糊
 * append            : 追加样式字符串
 * create            : 创建样式字符串
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class SpannableStringUtils {
    /**
     * 构造类
     */
    private SpannableStringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取建造者
     *
     * @param text 样式字符串文本
     * @return {@link Builder}
     */
    public static Builder getBuilder(@NonNull CharSequence text) {
        return new Builder(text);
    }

    /**
     * 建造者模式
     */
    public static class Builder {
        /** 默认值 */
        private int defaultValue = 0x12000000;
        /** 显示文本 */
        private CharSequence text;

        /** 标识 */
        private int flag;
        /** 前景色 */
        @ColorInt
        private int foregroundColor;
        /** 背景颜色 */
        @ColorInt
        private int backgroundColor;
        /** 引用线的颜色 */
        @ColorInt
        private int quoteColor;

        /** 缩进 */
        private boolean isLeadingMargin;
        /** 首行缩进 */
        private int first;
        /** 剩余行缩进 */
        private int rest;

        /** 列表标记 */
        private boolean isBullet;
        /** 列表标记和文字间距离 */
        private int gapWidth;
        /** 列表标记的颜色 */
        private int bulletColor;

        /** 字体比例 */
        private float proportion;
        /** 字体横向比例 */
        private float xProportion;
        /** 是否删除线 */
        private boolean isStrikethrough;
        /** 是否下划线 */
        private boolean isUnderline;
        /** 是否上标 */
        private boolean isSuperscript;
        /** 是否下表 */
        private boolean isSubscript;
        /** 是否粗体 */
        private boolean isBold;
        /** 是否斜体 */
        private boolean isItalic;
        /** 是否粗斜体 */
        private boolean isBoldItalic;
        /** 字体 */
        private String fontFamily;
        /** 设置对齐 */
        private Alignment align;

        /** 是否位图 */
        private boolean imageIsBitmap;
        /** 位图 */
        private Bitmap bitmap;
        /** 是否图片资源 */
        private boolean imageIsDrawable;
        /** 图片资源 */
        private Drawable drawable;
        /** 是否图片url */
        private boolean imageIsUri;
        /** 图片utl */
        private Uri uri;
        /** 是否图片资源id */
        private boolean imageIsResourceId;
        /** 图片资源id */
        @DrawableRes
        private int resourceId;

        /** 点击范围 */
        private ClickableSpan clickSpan;
        /** 超链接 */
        private String url;

        /** 是否模糊 */
        private boolean isBlur;
        /** 半径 */
        private float radius;
        /** 模糊样式 */
        private Blur style;
        /** 样式字符串 */
        private SpannableStringBuilder mBuilder;

        /**
         * 建造类
         *
         * @param text 文本
         */
        private Builder(@NonNull CharSequence text) {
            this.text = text;
            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
            foregroundColor = defaultValue;
            backgroundColor = defaultValue;
            quoteColor = defaultValue;
            proportion = -1;
            xProportion = -1;
            mBuilder = new SpannableStringBuilder();
        }

        /**
         * 设置标识
         *
         * @param flag <ul>
         * <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
         * <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
         * <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
         * <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
         * </ul>
         * @return {@link Builder}
         */
        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * 设置前景色
         *
         * @param color 前景色
         * @return {@link Builder}
         */
        public Builder setForegroundColor(@ColorInt int color) {
            this.foregroundColor = color;
            return this;
        }

        /**
         * 设置背景色
         *
         * @param color 背景色
         * @return {@link Builder}
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * 设置引用线的颜色
         *
         * @param color 引用线的颜色
         * @return {@link Builder}
         */
        public Builder setQuoteColor(@ColorInt int color) {
            this.quoteColor = color;
            return this;
        }

        /**
         * 设置缩进
         *
         * @param first 首行缩进
         * @param rest 剩余行缩进
         * @return {@link Builder}
         */
        public Builder setLeadingMargin(int first, int rest) {
            this.first = first;
            this.rest = rest;
            isLeadingMargin = true;
            return this;
        }

        /**
         * 设置列表标记
         *
         * @param gapWidth 列表标记和文字间距离
         * @param color 列表标记的颜色
         * @return {@link Builder}
         */
        public Builder setBullet(int gapWidth, int color) {
            this.gapWidth = gapWidth;
            bulletColor = color;
            isBullet = true;
            return this;
        }

        /**
         * 设置字体比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setProportion(float proportion) {
            this.proportion = proportion;
            return this;
        }

        /**
         * 设置字体横向比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setXProportion(float proportion) {
            this.xProportion = proportion;
            return this;
        }

        /**
         * 设置删除线
         *
         * @return {@link Builder}
         */
        public Builder setStrikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        /**
         * 设置下划线
         *
         * @return {@link Builder}
         */
        public Builder setUnderline() {
            this.isUnderline = true;
            return this;
        }

        /**
         * 设置上标
         *
         * @return {@link Builder}
         */
        public Builder setSuperscript() {
            this.isSuperscript = true;
            return this;
        }

        /**
         * 设置下标
         *
         * @return {@link Builder}
         */
        public Builder setSubscript() {
            this.isSubscript = true;
            return this;
        }

        /**
         * 设置粗体
         *
         * @return {@link Builder}
         */
        public Builder setBold() {
            isBold = true;
            return this;
        }

        /**
         * 设置斜体
         *
         * @return {@link Builder}
         */
        public Builder setItalic() {
            isItalic = true;
            return this;
        }

        /**
         * 设置粗斜体
         *
         * @return {@link Builder}
         */
        public Builder setBoldItalic() {
            isBoldItalic = true;
            return this;
        }

        /**
         * 设置字体
         *
         * @param fontFamily 字体
         * <ul>
         * <li>monospace</li>
         * <li>serif</li>
         * <li>sans-serif</li>
         * </ul>
         * @return {@link Builder}
         */
        public Builder setFontFamily(@Nullable String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        /**
         * 设置对齐
         *
         * @param align 对其方式
         * <ul>
         * <li>{@link Alignment#ALIGN_NORMAL}正常</li>
         * <li>{@link Alignment#ALIGN_OPPOSITE}相反</li>
         * <li>{@link Alignment#ALIGN_CENTER}居中</li>
         * </ul>
         * @return {@link Builder}
         */
        public Builder setAlign(@Nullable Alignment align) {
            this.align = align;
            return this;
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @return {@link Builder}
         */
        public Builder setBitmap(@NonNull Bitmap bitmap) {
            this.bitmap = bitmap;
            imageIsBitmap = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @return {@link Builder}
         */
        public Builder setDrawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            imageIsDrawable = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param uri 图片uri
         * @return {@link Builder}
         */
        public Builder setUri(@NonNull Uri uri) {
            this.uri = uri;
            imageIsUri = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @return {@link Builder}
         */
        public Builder setResourceId(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            imageIsResourceId = true;
            return this;
        }

        /**
         * 设置点击事件
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param clickSpan 点击事件
         * @return {@link Builder}
         */
        public Builder setClickSpan(@NonNull ClickableSpan clickSpan) {
            this.clickSpan = clickSpan;
            return this;
        }

        /**
         * 设置超链接
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param url 超链接
         * @return {@link Builder}
         */
        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置模糊
         * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
         * <p>推荐还是把所有字体都模糊这样使用</p>
         *
         * @param radius 模糊半径（需大于0）
         * @param style 模糊样式<ul>
         * <li>{@link Blur#NORMAL}</li>
         * <li>{@link Blur#SOLID}</li>
         * <li>{@link Blur#OUTER}</li>
         * <li>{@link Blur#INNER}</li>
         * </ul>
         * @return {@link Builder}
         */
        public Builder setBlur(float radius, Blur style) {
            this.radius = radius;
            this.style = style;
            this.isBlur = true;
            return this;
        }

        /**
         * 追加样式字符串
         *
         * @param text 样式字符串文本
         * @return {@link Builder}
         */
        public Builder append(@NonNull CharSequence text) {
            setSpan();
            this.text = text;
            return this;
        }

        /**
         * 创建样式字符串
         *
         * @return 样式字符串
         */
        public SpannableStringBuilder create() {
            setSpan();
            return mBuilder;
        }

        /**
         * 设置样式
         */
        private void setSpan() {
            int start = mBuilder.length();
            mBuilder.append(this.text);
            int end = mBuilder.length();
            if (foregroundColor != defaultValue) {
                mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
                foregroundColor = defaultValue;
            }
            if (backgroundColor != defaultValue) {
                mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
                backgroundColor = defaultValue;
            }
            if (isLeadingMargin) {
                mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
                isLeadingMargin = false;
            }
            if (quoteColor != defaultValue) {
                mBuilder.setSpan(new QuoteSpan(quoteColor), start, end, 0);
                quoteColor = defaultValue;
            }
            if (isBullet) {
                mBuilder.setSpan(new BulletSpan(gapWidth, bulletColor), start, end, 0);
                isBullet = false;
            }
            if (proportion != -1) {
                mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
                proportion = -1;
            }
            if (xProportion != -1) {
                mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
                xProportion = -1;
            }
            if (isStrikethrough) {
                mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
                isStrikethrough = false;
            }
            if (isUnderline) {
                mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
                isUnderline = false;
            }
            if (isSuperscript) {
                mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
                isSuperscript = false;
            }
            if (isSubscript) {
                mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
                isSubscript = false;
            }
            if (isBold) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
                isBold = false;
            }
            if (isItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
                isItalic = false;
            }
            if (isBoldItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
                isBoldItalic = false;
            }
            if (fontFamily != null) {
                mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
                fontFamily = null;
            }
            if (align != null) {
                mBuilder.setSpan(new AlignmentSpan.Standard(align), start, end, flag);
                align = null;
            }
            if (imageIsBitmap || imageIsDrawable || imageIsUri || imageIsResourceId) {
                if (imageIsBitmap) {
                    mBuilder.setSpan(new ImageSpan(Utils.getContext(), bitmap), start, end, flag);
                    bitmap = null;
                    imageIsBitmap = false;
                } else if (imageIsDrawable) {
                    mBuilder.setSpan(new ImageSpan(drawable), start, end, flag);
                    drawable = null;
                    imageIsDrawable = false;
                } else if (imageIsUri) {
                    mBuilder.setSpan(new ImageSpan(Utils.getContext(), uri), start, end, flag);
                    uri = null;
                    imageIsUri = false;
                } else {
                    mBuilder.setSpan(new ImageSpan(Utils.getContext(), resourceId), start, end, flag);
                    resourceId = 0;
                    imageIsResourceId = false;
                }
            }
            if (clickSpan != null) {
                mBuilder.setSpan(clickSpan, start, end, flag);
                clickSpan = null;
            }
            if (url != null) {
                mBuilder.setSpan(new URLSpan(url), start, end, flag);
                url = null;
            }
            if (isBlur) {
                mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(radius, style)), start, end, flag);
                isBlur = false;
            }
            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        }
    }
}
package com.hbln.lib_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.cmcc.lib_utils.utils.ConvertUtils;


/**
 * 圆形的ImageView
 *
 * @author mos
 * @date 2017.02.24
 * @note 1.支持圆形，圆角矩形，正方形
 * 2. 若为圆形，圆角矩形，则ScaleType固定为centerCrop。
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class CircularImageView extends android.support.v7.widget.AppCompatImageView {
    /** 默认形状 */
    private static final int SHAPE_NORMAL = 0;
    /** 圆形 */
    private static final int SHAPE_CIRCLE = 1;
    /** 圆角矩形 */
    private static final int SHAPE_ROUND_RECT = 2;

    /** 默认边框颜色 */
    private static final int DEFAULT_BORDER_COLOR = Color.WHITE;
    /** 默认圆角半径 */
    private static final int DEFAULT_ROUND_RADIUS = 10;
    /** 默认绘制模式SrcIn */
    private static final Xfermode MODE_SRC_IN = new PorterDuffXfermode(Mode.SRC_IN);
    /** 默认绘制模式SrcOver */
    private static final Xfermode MODE_SRC_OVER = new PorterDuffXfermode(Mode.SRC_OVER);

    /** 形状 */
    private int mShape;
    /** 边框宽度 */
    private int mBorderWidth;
    /** 边框颜色 */
    private int mBorderColor;
    /** 圆角半径 */
    private int mRoundRadius;
    /** 控件矩形 */
    private Rect mImageRect = new Rect();
    /** 源矩形 */
    private Rect mSrcRect = new Rect();
    /** 目标矩形 */
    private Rect mDstRect = new Rect();
    /** 圆角矩形 */
    private RectF mRoundRect = new RectF();
    /** Paint对象 */
    private Paint mPaint = new Paint();

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public CircularImageView(Context context) {
        super(context);
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs 属性
     */
    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs 属性
     * @param defStyle 样式
     */
    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
    }

    /**
     * 初始化属性
     *
     * @param context 上下文
     * @param attrs 属性集合
     */
    private void initAttrs(final Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView);

        // 读取属性
        mShape = array.getInt(R.styleable.CircularImageView_civShape, SHAPE_NORMAL);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.CircularImageView_civBorder, 0);
        mBorderColor = array.getColor(R.styleable.CircularImageView_civBorderColor, DEFAULT_BORDER_COLOR);
        mRoundRadius = array.getDimensionPixelSize(R.styleable.CircularImageView_civRoundRadius, dp2px(context, DEFAULT_ROUND_RADIUS));

        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
         * 说明：
         * 圆形，圆角矩形使用的绘制边框方式不同，因为圆形stroke是向内边和外边扩散，而圆角
         * 仅向内边扩散。因此相同的borderWidth，圆形和圆角矩形的边框宽度看起来不一样。
         */
        if (mShape == SHAPE_CIRCLE) {
            // 截取圆形
            Drawable drawable = getDrawable();

            if (drawable != null) {
                Bitmap bitmap = ConvertUtils.drawable2Bitmap(drawable);
                mImageRect.left = mBorderWidth;
                mImageRect.top = mBorderWidth;
                mImageRect.right = getWidth() - mBorderWidth;
                mImageRect.bottom = getHeight() - mBorderWidth;

                // 设置边缘平滑、位图平滑
                mPaint.setAntiAlias(true);
                mPaint.setFilterBitmap(true);

                // 截取圆形位图
                Bitmap circularBitmap = getCircularBitmap(bitmap);
                // 获取圆形矩形
                getCircularBitmapRect(circularBitmap, mSrcRect);

                if (circularBitmap != null) {
                    // 由于截取圆形，因此缩放后的矩形长宽相同
                    int scaledWidth;

                    // 取目标矩形的最小边长
                    boolean widthBased = mImageRect.width() < mImageRect.height();

                    if (widthBased) {
                        // 宽为最小边长
                        scaledWidth = mImageRect.width();
                        mDstRect.left = mImageRect.left;
                        mDstRect.top = mImageRect.top + (mImageRect.height() - mImageRect.width()) / 2;
                    } else {
                        // 高为最小边长
                        scaledWidth = mImageRect.height();
                        mDstRect.left = mImageRect.left + (mImageRect.width() - mImageRect.height()) / 2;
                        mDstRect.top = mImageRect.top;
                    }

                    mDstRect.right = mDstRect.left + scaledWidth;
                    mDstRect.bottom = mDstRect.top + scaledWidth;

                    // 绘制边框
                    if (mBorderWidth != 0) {
                        int viewWidth = getWidth();
                        int viewHeight = getHeight();
                        int radius = viewWidth < viewHeight ? viewWidth / 2 : viewHeight / 2;

                        // 设置边框颜色
                        mPaint.setColor(mBorderColor);
                        canvas.drawCircle(viewWidth / 2, viewHeight / 2, radius, mPaint);

                        // 设置绘制模式
                        mPaint.setXfermode(MODE_SRC_OVER);
                    }

                    canvas.drawBitmap(circularBitmap, mSrcRect, mDstRect, mPaint);
                }
            }

        } else if (mShape == SHAPE_ROUND_RECT) {
            // 截取圆形
            Drawable drawable = getDrawable();

            if (drawable != null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                int ivWidth = getWidth();
                int ivHeight = getHeight();

                // 设置边缘平滑、位图平滑
                mPaint.setAntiAlias(true);
                mPaint.setFilterBitmap(true);

                // 获取圆角矩形的位图
                Bitmap newBmp = getRoundRectBitmap(bitmapDrawable.getBitmap(), ivWidth, ivHeight);

                mDstRect.left = 0;
                mDstRect.top = 0;
                mDstRect.right = ivWidth;
                mDstRect.bottom = ivHeight;

                canvas.drawBitmap(newBmp, null, mDstRect, mPaint);
            }

        } else {
            // 完整绘制
            super.onDraw(canvas);
        }

        mPaint.reset();
    }

    /**
     * 获取圆形位图
     *
     * @param src 位图
     * @return 圆形位图
     */
    private Bitmap getCircularBitmap(Bitmap src) {
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Bitmap output = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        float radius = srcWidth < srcHeight ? srcWidth / 2 : srcHeight / 2;

        // 绘制一个边界图形
        mPaint.setColor(Color.RED);
        canvas.drawCircle(srcWidth / 2, srcHeight / 2, radius, mPaint);

        // 将位图绘制到目标区域
        mPaint.setXfermode(MODE_SRC_IN);
        mDstRect.left = 0;
        mDstRect.top = 0;
        mDstRect.right = srcWidth;
        mDstRect.bottom = srcHeight;
        canvas.drawBitmap(src, null, mDstRect, mPaint);

        mPaint.setXfermode(null);

        return output;
    }

    /**
     * 获取圆角矩形位图
     *
     * @param src 位图
     * @param dstW 目标宽度
     * @param dstH 目标高度
     * @return 圆角矩形位图
     */
    private Bitmap getRoundRectBitmap(Bitmap src, int dstW, int dstH) {
        mImageRect.left = 0;
        mImageRect.top = 0;
        mImageRect.right = dstW;
        mImageRect.bottom = dstH;

        Bitmap output = Bitmap.createBitmap(dstW, dstH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 绘制裁剪圆角边框
        mRoundRect.set(mImageRect);
        mPaint.setColor(Color.RED);
        canvas.drawRoundRect(mRoundRect, mRoundRadius, mRoundRadius, mPaint);

        // 将位图绘制到目标区域 
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();

        // 按比例计算SrcRect，先以宽为标准
        int rqsWidth = srcWidth;
        int rqsHeight = rqsWidth * dstH / dstW;

        if (rqsHeight > srcHeight) {
            // 根据上次计算，需求的高大于源位图，则按高为标准
            rqsHeight = srcHeight;
            rqsWidth = rqsHeight * dstW / dstH;
        }

        // 宽度相同
        if (rqsWidth == srcWidth) {
            mSrcRect.left = 0;
            mSrcRect.right = rqsWidth;
            mSrcRect.top = (int) (((float) srcHeight - rqsHeight) / 2 + 0.5f);
            mSrcRect.bottom = mSrcRect.top + rqsHeight;
        } else {
            mSrcRect.top = 0;
            mSrcRect.bottom = rqsHeight;
            mSrcRect.left = (int) (((float) srcWidth - rqsWidth) / 2 + 0.5f);
            mSrcRect.right = mSrcRect.left + rqsWidth;
        }

        mPaint.setXfermode(MODE_SRC_IN);
        mDstRect.left = 0;
        mDstRect.top = 0;
        mDstRect.right = dstW;
        mDstRect.bottom = dstH;
        canvas.drawBitmap(src, mSrcRect, mDstRect, mPaint);

        // 绘制边框
        if (mBorderWidth != 0) {
            mPaint.setColor(mBorderColor);
            mPaint.setStyle(Style.STROKE);
            mPaint.setStrokeWidth(mBorderWidth);
            canvas.drawRoundRect(mRoundRect, mRoundRadius, mRoundRadius, mPaint);
        }

        mPaint.setXfermode(null);

        return output;
    }

    /**
     * 获取位图的圆形矩形
     *
     * @param bitmap 位图
     * @param rect 矩形对象(输出)
     */
    private void getCircularBitmapRect(Bitmap bitmap, Rect rect) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean widthBased = width < height;

        if (widthBased) {
            rect.left = 0;
            rect.top = (height - width) / 2;
            rect.right = rect.left + width;
            rect.bottom = rect.top + width;
        } else {
            rect.left = (width - height) / 2;
            rect.top = 0;
            rect.right = rect.left + height;
            rect.bottom = rect.top + height;
        }
    }

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dp * scale + 0.5f);
    }
}

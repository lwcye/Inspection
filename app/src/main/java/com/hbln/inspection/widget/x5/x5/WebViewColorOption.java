package com.hbln.inspection.widget.x5.x5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * WebView的颜色参数选项
 *
 * @author mos
 * @date 2017.04.20
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WebViewColorOption implements Parcelable {
    public static final Creator<WebViewColorOption> CREATOR = new Creator<WebViewColorOption>() {
        @Override
        public WebViewColorOption createFromParcel(Parcel source) {
            return new WebViewColorOption(source);
        }

        @Override
        public WebViewColorOption[] newArray(int size) {
            return new WebViewColorOption[size];
        }
    };
    /** 标题栏背景颜色 */
    public int titleBackgroundColor;
    /** 标题栏文本颜色 */
    public int titleTextColor;
    /** 是否使用浅色状态栏 */
    public boolean useLightStatusBar;

    /**
     * 构造函数
     */
    public WebViewColorOption() {
    }

    protected WebViewColorOption(Parcel in) {
        this.titleBackgroundColor = in.readInt();
        this.titleTextColor = in.readInt();
        this.useLightStatusBar = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.titleBackgroundColor);
        dest.writeInt(this.titleTextColor);
        dest.writeByte(this.useLightStatusBar ? (byte) 1 : (byte) 0);
    }
}

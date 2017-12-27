package com.hbln.inspection.widget.x5.x5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * WebView的参数选项
 *
 * @author mos
 * @date 2017.04.13
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WebViewOption implements Parcelable {
    public static final Creator<WebViewOption> CREATOR = new Creator<WebViewOption>() {
        @Override
        public WebViewOption createFromParcel(Parcel source) {
            return new WebViewOption(source);
        }

        @Override
        public WebViewOption[] newArray(int size) {
            return new WebViewOption[size];
        }
    };
    /** 地址 */
    public String url;
    /** 标题 */
    public String title;
    /** 是否显示标题栏 */
    public boolean showTitleBar;
    /** 是否覆盖标题 */
    public boolean overrideTitle;
    /** 是否延迟加载图片 */
    public boolean blockNetworkImage;
    /** 是否显示转圈样式的loading(否则为进度样式) */
    public boolean indeterminate;
    /** cookie(格式，以分号分割：uSign=xxx; WT_FPC=xxx) */
    public String cookie;
    /** header(格式，Json：{"SESSION_APP":"xxxx", "Other":"xxxx"}) */
    public String header;
    /** 是否允许加载缓存 */
    public boolean loadCache;

    /**
     * 构造函数
     */
    public WebViewOption() {
        showTitleBar = true;
        overrideTitle = true;
        indeterminate = true;
        blockNetworkImage = true;
        loadCache = true;
    }

    protected WebViewOption(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        this.showTitleBar = in.readByte() != 0;
        this.overrideTitle = in.readByte() != 0;
        this.blockNetworkImage = in.readByte() != 0;
        this.indeterminate = in.readByte() != 0;
        this.cookie = in.readString();
        this.header = in.readString();
        this.loadCache = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeByte(this.showTitleBar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.overrideTitle ? (byte) 1 : (byte) 0);
        dest.writeByte(this.blockNetworkImage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indeterminate ? (byte) 1 : (byte) 0);
        dest.writeString(this.cookie);
        dest.writeString(this.header);
        dest.writeByte(this.loadCache ? (byte) 1 : (byte) 0);
    }
}

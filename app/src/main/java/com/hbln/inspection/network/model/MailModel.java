package com.hbln.inspection.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/14
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MailModel extends ResultModel {
    public List<MailInfo> info;
    
    public static class MailInfo implements Parcelable {
        
        public String name;
        public String id;
        public String sort;
        public String pid;
        public String beizhu;
        public String pic;
        public String lingdao;
        public String nums;
        public List<XingxiBean> xingxi;
        
        public static class XingxiBean implements Parcelable {
            public int id;
            public String name;
            public String mobile;
            public String pic;
            public String zhiwu;
    
            @Override
            public int describeContents() {
                return 0;
            }
    
            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.mobile);
                dest.writeString(this.pic);
                dest.writeString(this.zhiwu);
            }
    
            public XingxiBean() {
            }
    
            protected XingxiBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.mobile = in.readString();
                this.pic = in.readString();
                this.zhiwu = in.readString();
            }
    
            public static final Creator<XingxiBean> CREATOR = new Creator<XingxiBean>() {
                @Override
                public XingxiBean createFromParcel(Parcel source) {
                    return new XingxiBean(source);
                }
        
                @Override
                public XingxiBean[] newArray(int size) {
                    return new XingxiBean[size];
                }
            };
        }
    
        @Override
        public int describeContents() {
            return 0;
        }
    
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.id);
            dest.writeString(this.sort);
            dest.writeString(this.pid);
            dest.writeString(this.beizhu);
            dest.writeString(this.pic);
            dest.writeString(this.lingdao);
            dest.writeString(this.nums);
            dest.writeList(this.xingxi);
        }
    
        public MailInfo() {
        }
    
        protected MailInfo(Parcel in) {
            this.name = in.readString();
            this.id = in.readString();
            this.sort = in.readString();
            this.pid = in.readString();
            this.beizhu = in.readString();
            this.pic = in.readString();
            this.lingdao = in.readString();
            this.nums = in.readString();
            this.xingxi = new ArrayList<XingxiBean>();
            in.readList(this.xingxi, XingxiBean.class.getClassLoader());
        }
    
        public static final Creator<MailInfo> CREATOR = new Creator<MailInfo>() {
            @Override
            public MailInfo createFromParcel(Parcel source) {
                return new MailInfo(source);
            }
        
            @Override
            public MailInfo[] newArray(int size) {
                return new MailInfo[size];
            }
        };
    }
    
}

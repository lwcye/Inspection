package com.cmcc.lib_network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/18
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TrackModel extends ResultModel{
    
    public List<InfoBean> info;
    
    public static class InfoBean implements Parcelable {
        /**
         * id : 1
         * uid : 20
         * name : 钱程
         * longitude : 106.519307
         * latitude : 29.620905
         * address : 中国重庆市渝北区星光三路2号
         * create_time : null
         * beizhu : 测试
         * times : 2017-12-18 20:57
         */
        
        public String id;
        public String uid;
        public String name;
        public String longitude;
        public String latitude;
        public String address;
        public String create_time;
        public String beizhu;
        public String times;
    
        @Override
        public int describeContents() {
            return 0;
        }
    
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.name);
            dest.writeString(this.longitude);
            dest.writeString(this.latitude);
            dest.writeString(this.address);
            dest.writeString(this.create_time);
            dest.writeString(this.beizhu);
            dest.writeString(this.times);
        }
    
        public InfoBean() {
        }
    
        protected InfoBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.name = in.readString();
            this.longitude = in.readString();
            this.latitude = in.readString();
            this.address = in.readString();
            this.create_time = in.readString();
            this.beizhu = in.readString();
            this.times = in.readString();
        }
    
        public static final Parcelable.Creator<InfoBean> CREATOR = new Parcelable.Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel source) {
                return new InfoBean(source);
            }
        
            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
            }
        };
    }
}

package com.cmcc.lib_network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 1:03
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class JiafangModel extends ResultModel {

    public List<JiafangInfoBean> info;

    public static class JiafangInfoBean implements Parcelable {
        /**
         * id : 2
         * title : 贾汪区纪检监察巡察家访问卷父母
         * dannums : 7
         * duonums : 0
         * wendannums : 1
         * create_time : 1513247848
         * pic : null
         * typeid : 97
         * times : 2017-12-14 18:37
         */

        public String id;
        public String title;
        public String dannums;
        public String duonums;
        public String wendannums;
        public String create_time;
        public String pic;
        public String typeid;
        public String times;
        public String nums;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.dannums);
            dest.writeString(this.duonums);
            dest.writeString(this.wendannums);
            dest.writeString(this.create_time);
            dest.writeString(this.pic);
            dest.writeString(this.typeid);
            dest.writeString(this.times);
            dest.writeString(this.nums);
        }

        public JiafangInfoBean() {
        }

        protected JiafangInfoBean(Parcel in) {
            this.id = in.readString();
            this.title = in.readString();
            this.dannums = in.readString();
            this.duonums = in.readString();
            this.wendannums = in.readString();
            this.create_time = in.readString();
            this.pic = in.readString();
            this.typeid = in.readString();
            this.times = in.readString();
            this.nums = in.readString();
        }

        public static final Creator<JiafangInfoBean> CREATOR = new Creator<JiafangInfoBean>() {
            @Override
            public JiafangInfoBean createFromParcel(Parcel source) {
                return new JiafangInfoBean(source);
            }

            @Override
            public JiafangInfoBean[] newArray(int size) {
                return new JiafangInfoBean[size];
            }
        };
    }
}

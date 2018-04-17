package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/18 23:33
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class FortressHomeModel extends ResultModel{
    public String resultCount;
    public String pageCount;
    public List<InfoBean> info;

    public static class InfoBean {
        /**
         * id : 20
         * typeid : 94
         * title : 第五党小组缴纳党费
         * create_time : 1511971200
         * content :
         * fujian : null
         * uid : 16
         * zbid : 45
         * nums : 1
         * times : 2017-11-30
         * picpath :
         * zhibuname : 第五党小组
         */

        public String id;
        public String typeid;
        public String title;
        public String create_time;
        public String update_time;
        public String content;
        public String fujian;
        public String author;
        public String uid;
        public String zbid;
        public String nums;
        public String times;
        public String picpath;
        //三会一课的图片
        public String pic;
        public String zhibuname;
    }
}

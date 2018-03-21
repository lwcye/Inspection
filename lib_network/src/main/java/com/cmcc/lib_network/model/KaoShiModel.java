package com.cmcc.lib_network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/19 20:47
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class KaoShiModel extends ResultModel {

    public List<InfoBean> info;

    public static class InfoBean {
        /**
         * id : 45
         * title : 測試試卷
         * dannums : 20
         * duonums : 20
         * panduannums : 10
         * create_time : 0
         * pic :S
         * typeid : 104
         * danxuanfenshu : null
         * duoxuanfenshu : null
         * panduanfenshu : null
         * times : 1970-01-01 08:00
         * nums : 0
         */

        public String id;
        public String title;
        public String dannums;
        public String duonums;
        public String panduannums;
        public String create_time;
        public String pic;
        public String typeid;
        public String times;
        public String kmid;
        public String danxuanfenshu;
        public String duoxuanfenshu;
        public String panduanfenshu;
        public long startime;
        public long endtime;
        public String kaishitime;
        public String endtimes;
        public String nums;
    }
}

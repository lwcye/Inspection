package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 0:45
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WorkModel extends ResultModel {
    
    public String pageCount;
    public String resultCount;
    public List<WorkInfoBean> info;
    
    public static class WorkInfoBean {
        /**
         * id : 8
         * title : 爱上
         * content : <p>萨菲打算</p>
         * dwid : 1
         * create_time : 0
         * update_time : 0
         * uid : 16
         * nums : 0
         * times : 1970-01-01
         * danwei : 科室
         * picpath : []
         */
        
        public String id;
        public String title;
        public String content;
        public String dwid;
        public String create_time;
        public String update_time;
        public String uid;
        public String nums;
        public String times;
        public String danwei;
        public Object picpath;
    }
}

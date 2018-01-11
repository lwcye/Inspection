package com.cmcc.lib_network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 20:53
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class SchoolModel extends ResultModel{
    public int resultCount;
    public int pageCount;
    public List<SchoolInfo> info;
    
    public static class SchoolInfo {
        public String id;
        public String title;
        public String content;
        public String pic;
        public String uid;
        public String video;
        public String create_time;
        public String update_time;
        public String nums;
        public String goodnums;
        public String badnums;
        public String status;
        public String author;
        public String typeid;
        public String times;
        public String picpath;
    }
}

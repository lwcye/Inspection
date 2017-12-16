package com.cmcc.lib_network.model;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/16 0:13
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WebViewModel extends ResultModel {
    public WebViewInfo info;
    
    public static class WebViewInfo {
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
        public String danweione;
        public String danweitwo;
        public String name;
        public Object fujian;
        public String zhiwu;
    }
}

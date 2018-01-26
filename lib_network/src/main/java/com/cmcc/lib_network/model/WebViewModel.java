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
    /** 反腐讲习堂 */
    public static final int TYPE_SCHOOL = 0;
    /** 工作动态 */
    public static final int TYPE_WORK = 1;
    /** 制度规则 */
    public static final int TYPE_REGULAR = 2;
    /** 堡垒 -- 党员监督 */
    public static final int TYPE_FORTRESS_JIANDU = 3;
    /** 堡垒 -- 首页 */
    public static final int TYPE_FORTRESS_HOME = 4;
    /** 堡垒 -- 群众工作 */
    public static final int TYPE_FORTRESS_QUN_ZHONG = 5;
    /** 堡垒 -- 三会一课 */
    public static final int TYPE_FORTRESS_SAN_HUI = 6;
    /** 品牌外宣 */
    public static final int TYPE_BRAND_WAIXUAN = 7;
    /** 模范 -- 个人 */
    public static final int TYPE_MODEL_PRIVE = 8;
    /** 模范 -- 集体 */
    public static final int TYPE_MODEL_GROUP = 9;
    
    public String resultCount;
    public String pageCount;
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

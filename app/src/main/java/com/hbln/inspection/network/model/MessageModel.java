package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2018/1/26 21:04
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MessageModel extends ResultModel {
    
    
    public List<InfoBean> info;
    
    public static class InfoBean {
        /**
         * id : 475
         * title : 爱上
         * content : 阿斯顿擦拭
         * create_time : 2018-01-26
         * dwid : 9
         * uid : 16
         */
        
        public int id;
        public String title;
        public String content;
        public String create_time;
        public int dwid;
        public int uid;
    }
}

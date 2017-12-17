package com.cmcc.lib_network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/17 21:33
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BrandModel extends ResultModel{
    
    public List<Info> info;
    
    public static class Info {
        /**
         * id : 2
         * title : 1、监督执纪问责平台（办公室、案管室）
         * create_time : 1513353600
         * uid : 16
         * pic : null
         * model : 1,4,5,3
         * sort : 1
         */
        
        public String id;
        public String title;
        public String create_time;
        public String uid;
        public String pic;
        public String model;
        public String sort;
    }
}

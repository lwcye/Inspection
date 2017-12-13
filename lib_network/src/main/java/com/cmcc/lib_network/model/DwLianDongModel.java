package com.cmcc.lib_network.model;

import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/13 22:50
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class DwLianDongModel extends ResultModel{
    
    public List<InfoBean> info;
    
    public static class InfoBean {
        
        public String name;
        public String id;
        public int sort;
        public String pid;
        public String beizhu;
        public String pic;
        public String lingdao;
    }
}

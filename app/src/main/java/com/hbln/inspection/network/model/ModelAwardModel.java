package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>模范 -- 获奖情况</p><br>
 *
 * @author - lwc
 * @date - 2018/1/30
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ModelAwardModel {
    public List<InfoBean> info;

    public static class InfoBean {
        public int id;
        public String typeid;
        public String create_time;
        public String jiangxiang;
        public String danwei;
        public String sxid;
        public String time;
    }
}

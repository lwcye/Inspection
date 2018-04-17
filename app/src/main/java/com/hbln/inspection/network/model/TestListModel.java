package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>我的试题</p><br>
 *
 * @author - lwc
 * @date - 2018/4/2
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TestListModel extends ResultModel {
    public List<InfoBean> info;

    public static class InfoBean {
        public String id;
        public String uid;
        public String name;
        public String sjid;
        public String stids;
        public String daids;
        public String create_time;
        public String fenshu;
        public String sjtitle;
    }
}

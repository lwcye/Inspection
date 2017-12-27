package com.cmcc.lib_network.model;

import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/27
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WorkTypeModel extends ResultModel {
    public List<InfoBean> info;
    private List<InfoBean> sinfo;

    public void initModel() {
        for (int i = 0; i < info.size(); i++) {
            InfoBean infoBean = info.get(i);
            for (InfoBean bean : sinfo) {
                if (bean.dwid.equals(infoBean.dwid)) {
                    infoBean.diff = bean.paiming - infoBean.paiming;
                    break;
                }
            }
        }
    }

    public static class InfoBean {
        /**
         * {
         * "id": 72,
         * "create_time": 1511971200,
         * "update_time": 1511971200,
         * "typeid": 68,
         * "dwid": 12,
         * "uid": 16,
         * "nums": 3,
         * "year": 2017,
         * "month": 11,
         * "danwei": "纪检监察四室",
         * "paiming": 7
         * }
         */
        public String id;
        public String create_time;
        public String update_time;
        public String typeid;
        public String dwid;
        public String uid;
        public int nums;
        public String year;
        public String month;
        public String danwei;
        public int paiming;
        public int diff;
    }
}

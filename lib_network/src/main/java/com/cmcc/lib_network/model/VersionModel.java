package com.cmcc.lib_network.model;

/**
 * <p>版本信息</p><br>
 *
 * @author - lwc
 * @date - 2017/12/27
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class VersionModel extends ResultModel {
    public InfoBean info;

    public static class InfoBean {
        /**
         * "id": 6,
         * "version": "1111",
         * "create_time": 1514341356,
         * "beizhu": "566556654",
         * "rompath": "http://58.218.171.12:1003/ganbujiandu/uploads/wenjian/20171227/4252d3bd49690394cbdb8f2f1e77f036.apk"
         */
        public String id;
        public String version;
        public String create_time;
        public String beizhu;
        public String rompath;
    }
}

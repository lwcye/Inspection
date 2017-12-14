package com.cmcc.lib_network.model;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/14
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class UserInfoModel extends ResultModel {
    public UserInfo info;

    public static class UserInfo {
        public String username;
        public String id;
        public String password;
        public String email;
        public String reg_time;
        public String reg_ip;
        public String last_login_time;
        public String last_login_ip;
        public String updata_time;
        public String status;
        public String nickname;
        public String mobile;
        public String dwid;
        public String sfid;
        public String danwei;
    }
}

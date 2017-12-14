package com.cmcc.lib_network.model;

import java.util.List;

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
public class MailModel extends ResultModel {
    public List<MailInfo> info;

    public static class MailInfo {
        public String name;
        public String id;
        public String sort;
        public String pid;
        public String beizhu;
        public String pic;
        public String lingdao;
        public String nums;
        public List<Object> xingxi;
    }
}

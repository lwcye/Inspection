package com.cmcc.inspection.widget.x5.result;


import com.cmcc.inspection.widget.x5.bean.ActionResult;

/**
 * 电话信息
 *
 * @author mos
 * @date 2017.04.20
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class PhoneInfoResult extends ActionResult {
    /** 数据 */
    public PhoneInfo data;

    /**
     * 构造函数
     *
     * @param code 代码
     * @param msg 消息
     */
    public PhoneInfoResult(int code, String msg) {
        super(code, msg);
    }

    /**
     * 电话信息
     */
    public static class PhoneInfo {
        /** 手机IMEI */
        public String imei;
        /** 手机IMSI */
        public String imsi;
    }
}

package com.hbln.inspection.widget.x5.result;


import com.hbln.inspection.widget.x5.bean.ActionResult;

/**
 * App信息
 *
 * @author mos
 * @date 2017.04.19
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class AppInfoResult extends ActionResult {
    /** 数据 */
    public AppInfo data;

    /**
     * 构造函数
     *
     * @param code 代码
     * @param msg 消息
     */
    public AppInfoResult(int code, String msg) {
        super(code, msg);
    }

    /**
     * App信息
     */
    public static class AppInfo {
        /** 版本号 */
        public int versionCode;
        /** 版本名 */
        public String versionName;
    }
}

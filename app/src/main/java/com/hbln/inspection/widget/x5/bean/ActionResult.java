package com.hbln.inspection.widget.x5.bean;

/**
 * 内部组件的调用结果
 *
 * @author mos
 * @date 2017.04.13
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ActionResult extends BaseActionResult {
    /** 代码 */
    public int code;
    /** 消息 */
    public String msg;

    /**
     * 构造函数
     *
     * @param code 代码
     * @param msg 消息
     */
    public ActionResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

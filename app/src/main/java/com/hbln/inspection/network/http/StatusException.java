package com.hbln.inspection.network.http;

/**
 * <p>订单超时的错误</p><br>
 *
 * @author - Administrator
 * @date - 2017/3/30
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class StatusException extends RuntimeException {
    public int mStatusCode;
    public String mResult;
    
    /** 构造类 */
    public StatusException(int statusCode, String result) {
        super(result);
        this.mStatusCode = statusCode;
        this.mResult = result;
    }
}

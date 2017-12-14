package com.cmcc.lib_network.http;


import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.R;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.ResUtils;
import com.cmcc.lib_utils.utils.ToastUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rx.functions.Action1;

/**
 * <p>网络请求错误处理</p><br>
 *
 * @author lwc
 * @date 2017/3/20 16:07
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class HttpError implements Action1<Throwable> {
    public final static int ERROR_CODE_TIMEOUT = 90;
    public final static int ERROR_CODE_ELSE = 91;
    /** 基本视图 */
    private BaseView mBaseView;
    
    /**
     * 无参构造类
     */
    public HttpError() {
    }
    
    /**
     * 错误情况
     *
     * @param errorCode 错误代码
     * @param message 错误信息
     */
    public void error(int errorCode, String message) {
        ToastUtils.showShortToastSafe(message);
    }
    
    /**
     * 构造类，传入BaseView以便取消Loading
     *
     * @param baseView BaseView
     */
    public HttpError(BaseView baseView) {
        mBaseView = baseView;
    }
    
    @Override
    public void call(Throwable throwable) {
        if (null != mBaseView) {
            mBaseView.hideLoading();
        }
        if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException ||
            throwable instanceof SocketException || throwable instanceof UnknownHostException) {
            // 网络超时
            error(ERROR_CODE_TIMEOUT, ResUtils.getString(R.string.error_network_data));
            return;
        }
        if (throwable instanceof StatusException) {
            // 网络状态错误
            error(((StatusException) throwable).mStatusCode, ((StatusException) throwable).mResult);
            return;
        }
        // 其他错误
        error(ERROR_CODE_ELSE, ResUtils.getString(R.string.error_network_data));
        LogUtils.e(throwable);
    }
}

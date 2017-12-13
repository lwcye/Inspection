package com.cmcc.lib_network.http;


import com.cmcc.lib_common.mvp.BaseView;

import rx.functions.Action0;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/4/29 0:11
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class HttpComplete implements Action0 {
    private BaseView mBaseView;
    
    public HttpComplete(BaseView baseView) {
        mBaseView = baseView;
    }
    
    public HttpComplete() {
    }
    
    @Override
    public void call() {
        if (null != mBaseView) {
            mBaseView.hideLoading();
        }
    }
}

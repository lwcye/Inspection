package com.hbln.inspection.network.http;


import com.hbln.inspection.mvp.BaseView;

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

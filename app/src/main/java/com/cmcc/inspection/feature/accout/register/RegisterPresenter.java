package com.cmcc.inspection.feature.accout.register;

import android.text.TextUtils;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpException;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.DwLianDongModel;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {
    
    @Override
    public void loadDwData(String pid, final Action1<DwLianDongModel> action) {
        getView().showLoading("");
        if (TextUtils.isEmpty(pid)) {
            pid = "0";
        }
        HttpRequest.getUserService().dwliandong(pid)
            .compose(getView().getBaseActivity().<DwLianDongModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<DwLianDongModel>() {
                @Override
                public void result(DwLianDongModel dwLianDongModel) {
                    if (action != null) {
                        action.call(dwLianDongModel);
                    }
                }
            }, new HttpException(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void requestRegist() {
        
    }
    
    @Override
    public void verificationSfid() {
        
    }
}

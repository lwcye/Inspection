package com.hbln.inspection.feature.model;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ModelModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ModelPresenter extends BasePresenterImpl<ModelContract.View> implements ModelContract.Presenter {
    
    @Override
    public void loadGeRenData() {
        getView().showLoading("");
        HttpRequest.getModelService().gereninfo()
            .compose(NetWorkInterceptor.<ModelModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<ModelModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<ModelModel>() {
                @Override
                public void result(ModelModel modelModel) {
                    getView().setGeRenData(modelModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void loadJiTiData() {
        getView().showLoading("");
        HttpRequest.getModelService().danweiinfo()
            .compose(NetWorkInterceptor.<ModelModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<ModelModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<ModelModel>() {
                @Override
                public void result(ModelModel modelModel) {
                    getView().setJiTiData(modelModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

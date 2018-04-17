package com.hbln.inspection.feature.workarena.workdynamic;

import com.hbln.inspection.constans.URLs;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.WorkModel;
import com.trello.rxlifecycle.android.ActivityEvent;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkDynamicPresenter extends BasePresenterImpl<WorkDynamicContract.View> implements WorkDynamicContract.Presenter {
    
    @Override
    public void getData() {
        getView().showLoading("");
        HttpRequest.getWorkService().jobdongtai("1", URLs.PAGE_SIZE)
            .compose(NetWorkInterceptor.<WorkModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<WorkModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<WorkModel>() {
                @Override
                public void result(WorkModel workModel) {
                    getView().setData(workModel.info);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void searchData(String title) {
        getView().showLoading("");
        HttpRequest.getWorkService().jobdongtaisoso(title, "1", URLs.PAGE_SIZE)
            .compose(NetWorkInterceptor.<WorkModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<WorkModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<WorkModel>() {
                @Override
                public void result(WorkModel workModel) {
                    getView().setData(workModel.info);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

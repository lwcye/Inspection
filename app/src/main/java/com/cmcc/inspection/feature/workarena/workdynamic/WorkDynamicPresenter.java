package com.cmcc.inspection.feature.workarena.workdynamic;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.WorkModel;
import com.trello.rxlifecycle.android.ActivityEvent;

import static com.cmcc.lib_network.constans.URLs.PAGE_SIZE;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkDynamicPresenter extends BasePresenterImpl<WorkDynamicContract.View> implements WorkDynamicContract.Presenter {
    
    @Override
    public void getData() {
        getView().showLoading("");
        HttpRequest.getWorkService().jobdongtai("1", PAGE_SIZE)
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
        HttpRequest.getWorkService().jobdongtaisoso(title, "1", PAGE_SIZE)
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

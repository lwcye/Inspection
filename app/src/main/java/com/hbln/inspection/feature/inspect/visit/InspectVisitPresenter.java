package com.hbln.inspection.feature.inspect.visit;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.JiafangModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectVisitPresenter extends BasePresenterImpl<InspectVisitContract.View> implements InspectVisitContract.Presenter {

    @Override
    public void loadData() {
        getView().showLoading("");
        HttpRequest.getJiaFangServicee().index()
                .compose(NetWorkInterceptor.<JiafangModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<JiafangModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<JiafangModel>() {
                    @Override
                    public void result(JiafangModel jiafangModel) {
                        getView().setData(jiafangModel);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

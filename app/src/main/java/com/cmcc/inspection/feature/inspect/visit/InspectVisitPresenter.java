package com.cmcc.inspection.feature.inspect.visit;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.JiafangModel;
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

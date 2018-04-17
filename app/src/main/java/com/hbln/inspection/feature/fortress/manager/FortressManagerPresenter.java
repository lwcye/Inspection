package com.hbln.inspection.feature.fortress.manager;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.ManagerModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FortressManagerPresenter extends BasePresenterImpl<FortressManagerContract.View> implements FortressManagerContract.Presenter{
    @Override
    public void loadManagerData() {
        getView().showLoading("");
        HttpRequest.getFortressService().dangyuanguangli()
            .compose(NetWorkInterceptor.<ManagerModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<ManagerModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<ManagerModel>() {
                @Override
                public void result(ManagerModel objectModel) {
                    getView().setManagerData(objectModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

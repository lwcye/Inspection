package com.cmcc.inspection.feature.fortress.manager;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ManagerModel;
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

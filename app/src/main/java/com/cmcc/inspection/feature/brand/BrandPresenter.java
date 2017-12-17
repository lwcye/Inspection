package com.cmcc.inspection.feature.brand;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.BrandModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BrandPresenter extends BasePresenterImpl<BrandContract.View> implements BrandContract.Presenter {
    
    @Override
    public void loadData() {
        getView().showLoading("");
        HttpRequest.getBrandService().index()
            .compose(NetWorkInterceptor.<BrandModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<BrandModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<BrandModel>() {
                @Override
                public void result(BrandModel brandModel) {
                    getView().setData(brandModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

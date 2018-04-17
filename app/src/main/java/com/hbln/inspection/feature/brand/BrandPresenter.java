package com.hbln.inspection.feature.brand;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.BrandModel;
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

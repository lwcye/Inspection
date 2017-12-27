package com.hbln.inspection.feature.brand.branddetail;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.BrandDetailModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BrandDetailPresenter extends BasePresenterImpl<BrandDetailContract.View> implements BrandDetailContract.Presenter{
    
    @Override
    public void loadDetail(String ppid) {
        getView().showLoading("");
        HttpRequest.getBrandService().view(ppid)
            .compose(NetWorkInterceptor.<BrandDetailModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<BrandDetailModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<BrandDetailModel>() {
                @Override
                public void result(BrandDetailModel brandModel) {
                    getView().setDetail(brandModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

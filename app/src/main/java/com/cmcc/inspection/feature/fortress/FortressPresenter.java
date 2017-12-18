package com.cmcc.inspection.feature.fortress;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.cmcc.lib_network.model.JianDuModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressPresenter extends BasePresenterImpl<FortressContract.View> implements FortressContract.Presenter {
    
    
    @Override
    public void loadJiaoYuData() {
        getView().showLoading("");
        HttpRequest.getFortressService().zhuzhishlist("党员教育")
            .compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<FortressHomeModel>() {
                @Override
                public void result(FortressHomeModel objectModel) {
                    getView().setJiaoYuData(objectModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void loadJianDuData() {
        getView().showLoading("");
        HttpRequest.getFortressService().dangyuanjiandu("1", URLs.PAGE_SIZE)
            .compose(NetWorkInterceptor.<JianDuModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<JianDuModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<JianDuModel>() {
                @Override
                public void result(JianDuModel objectModel) {
                    getView().setJianDuData(objectModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

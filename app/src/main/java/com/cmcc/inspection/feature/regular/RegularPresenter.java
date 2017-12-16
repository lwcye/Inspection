package com.cmcc.inspection.feature.regular;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.RegularModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegularPresenter extends BasePresenterImpl<RegularContract.View> implements RegularContract.Presenter {
    
    @Override
    public void loadData(final int type, String title) {
        String sType = "中央";
        if (type == 1) {
            sType = "省";
        }
        if (type == 2) {
            sType = "市";
        }
        if (type == 3) {
            sType = "区";
        }
        
        getView().showLoading("");
        HttpRequest.getRegularService().listinfo(sType, title)
            .compose(NetWorkInterceptor.<RegularModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<RegularModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<RegularModel>() {
                @Override
                public void result(RegularModel regularModel) {
                    getView().setData(regularModel, type);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

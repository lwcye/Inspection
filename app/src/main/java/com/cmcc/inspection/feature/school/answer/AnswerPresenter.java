package com.cmcc.inspection.feature.school.answer;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import static android.R.attr.name;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerPresenter extends BasePresenterImpl<AnswerContract.View> implements AnswerContract.Presenter {
    
    @Override
    public void loadData(String sjid) {
        getView().showLoading("");
        HttpRequest.getKaoShiService().shiti(sjid)
            .compose(NetWorkInterceptor.<JfShiTiModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<JfShiTiModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<JfShiTiModel>() {
                @Override
                public void result(JfShiTiModel JfShiTiModel) {
                    getView().setData(JfShiTiModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void submit(String sjid, String stids, String daids) {
        LogUtils.e("sjid", sjid, "name", name, "stids", stids, "daids", daids);
        getView().showLoading("");
        HttpRequest.getKaoShiService().wenjuan(sjid, stids, daids)
            .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<ObjectModel>() {
                @Override
                public void result(ObjectModel objectModel) {
                    getView().submitSuccess(objectModel.info.toString());
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

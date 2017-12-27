package com.hbln.inspection.feature.school.answer;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;

import static android.R.attr.name;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerPresenter extends BasePresenterImpl<AnswerContract.View> implements AnswerContract.Presenter {
    
    @Override
    public void loadData(String sjid, int type) {
        Observable<JfShiTiModel> shiti;
        if (type == AnswerActivity.TYPE_VISIT) {
            shiti = HttpRequest.getJiaFangServicee().shiti(sjid);
        } else {
            shiti = HttpRequest.getKaoShiService().shiti(sjid);
        }
        getView().showLoading("");
        shiti.compose(NetWorkInterceptor.<JfShiTiModel>retrySessionCreator())
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
    
    @Override
    public void submit(String sjid, String name, String guanxi, String mobile, String stids, String daids) {
        LogUtils.e("sjid", sjid, "name", name, "guanxi", guanxi, "mobile", mobile, "stids", stids, "daids", daids);
        getView().showLoading("");
        HttpRequest.getJiaFangServicee().wenjuan(sjid, name, guanxi, mobile, stids, daids)
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

package com.hbln.inspection.feature.school.answer;

import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.AnswerModel;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;

import static android.R.attr.name;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerPresenter extends BasePresenterImpl<AnswerContract.View> implements AnswerContract.Presenter {

    @Override
    public void loadData(final String sjid, int type) {
        Observable<JfShiTiModel> shiti;
        if (type == AnswerActivity.TYPE_VISIT) {
            shiti = HttpRequest.getJiaFangServicee().shiti(sjid);
        } else if (type == AnswerActivity.TYPE_TEST) {
            shiti = HttpRequest.getKaoShiService().chengjiview(sjid);
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
                .compose(NetWorkInterceptor.<AnswerModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<AnswerModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<AnswerModel>() {
                    @Override
                    public void result(AnswerModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getView().submitSuccess(objectModel.chengji);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }

    @Override
    public void submit(String sjid, String name, String guanxi, String mobile, String stids, String daids) {
        LogUtils.e("sjid", sjid, "name", name, "guanxi", guanxi, "mobile", mobile, "stids", stids, "daids", daids);
        getView().showLoading("");
        HttpRequest.getJiaFangServicee().wenjuan(sjid, name, guanxi, mobile, stids, daids)
                .compose(NetWorkInterceptor.<AnswerModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<AnswerModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<AnswerModel>() {
                    @Override
                    public void result(AnswerModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getView().submitSuccess(objectModel.chengji);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

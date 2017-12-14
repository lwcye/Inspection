package com.cmcc.inspection.feature.inspect.visitanswer;

import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.JiafangModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VisitAnswerPresenter extends BasePresenterImpl<VisitAnswerContract.View> implements VisitAnswerContract.Presenter {
    @Override
    public void loadData(JiafangModel.JiafangInfoBean bean) {
        getView().showLoading("");
        HttpRequest.getJiaFangServicee().shiti(bean.id, URLs.UID)
            .compose(getView().getBaseActivity().<JfShiTiModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<JfShiTiModel>() {
                @Override
                public void result(JfShiTiModel JfShiTiModel) {
                    getView().setData(JfShiTiModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

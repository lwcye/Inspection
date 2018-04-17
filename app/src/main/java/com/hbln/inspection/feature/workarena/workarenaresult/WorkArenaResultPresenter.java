package com.hbln.inspection.feature.workarena.workarenaresult;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.WorkArenaModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkArenaResultPresenter extends BasePresenterImpl<WorkArenaResultContract.View> implements WorkArenaResultContract.Presenter {
    
    @Override
    public void loadData() {
        getView().showLoading("");
        HttpRequest.getWorkService().userpaiming()
            .compose(NetWorkInterceptor.<WorkArenaModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<WorkArenaModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<WorkArenaModel>() {
                @Override
                public void result(WorkArenaModel workArenaModel) {
                    getView().setData(workArenaModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

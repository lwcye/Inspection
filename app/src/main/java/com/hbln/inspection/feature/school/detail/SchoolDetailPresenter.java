package com.hbln.inspection.feature.school.detail;

import android.text.TextUtils;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.SchoolDetailModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolDetailPresenter extends BasePresenterImpl<SchoolDetailContract.View> implements SchoolDetailContract.Presenter {
    
    @Override
    public void loadData(String id) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showShortToastSafe("数据读取错误");
            getView().getBaseActivity().finish();
            return;
        }
        getView().showLoading("");
        HttpRequest.getShcoolService().jiangtangview(id)
            .compose(NetWorkInterceptor.<SchoolDetailModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<SchoolDetailModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<SchoolDetailModel>() {
                @Override
                public void result(SchoolDetailModel schoolInfo) {
                    getView().setData(schoolInfo.info);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

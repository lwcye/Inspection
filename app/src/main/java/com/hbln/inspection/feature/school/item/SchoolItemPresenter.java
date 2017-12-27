package com.hbln.inspection.feature.school.item;

import android.text.TextUtils;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.SchoolModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolItemPresenter extends BasePresenterImpl<SchoolItemContract.View> implements SchoolItemContract.Presenter {
    
    @Override
    public void loadData(int index) {
        String typeId = "81";
        if (index == 1) {
            typeId = "82";
        }
        if (index == 2) {
            typeId = "83";
        }
        getView().showLoading("");
        HttpRequest.getShcoolService().jiangtang("1", URLs.PAGE_SIZE, typeId)
            .compose(NetWorkInterceptor.<SchoolModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<SchoolModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<SchoolModel>() {
                @Override
                public void result(SchoolModel schoolModel) {
                    getView().setData(schoolModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void searchData(int index, String title) {
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showShortToastSafe("请输入要搜索的内容");
            return;
        }
        String typeId = "81";
        if (index == 1) {
            typeId = "82";
        }
        if (index == 2) {
            typeId = "83";
        }
        getView().showLoading("");
        HttpRequest.getShcoolService().jiangtangsoso(title, "1", URLs.PAGE_SIZE, typeId)
            .compose(NetWorkInterceptor.<SchoolModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<SchoolModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<SchoolModel>() {
                @Override
                public void result(SchoolModel schoolModel) {
                    getView().setData(schoolModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

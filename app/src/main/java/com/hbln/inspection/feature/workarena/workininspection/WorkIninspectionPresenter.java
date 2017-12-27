package com.hbln.inspection.feature.workarena.workininspection;

import android.text.TextUtils;

import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.WorkTypeModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkIninspectionPresenter extends BasePresenterImpl<WorkIninspectionContract.View> implements WorkIninspectionContract.Presenter {
    
    @Override
    public void loadData(final int type, String date) {
        //纪律审查   信息数量 外宣数量 巡查报告 移交线索
        //科室   巡察机构 派驻纪检组  乡镇
        String sType;
        if (type == 0) {
            sType = "纪律审查";
        } else if (type == 1) {
            sType = "信息数量";
        } else {
            sType = "外宣数量";
        }
        if (TextUtils.isEmpty(date)) {
            date = TimeUtils.getNowTimeString("yyyy-MM");
        }
        getView().showLoading("");
        HttpRequest.getWorkService().getinfo(date, sType, WorkIninspectionActivity.TITLE)
            .compose(NetWorkInterceptor.<WorkTypeModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<WorkTypeModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<WorkTypeModel>() {
                @Override
                public void result(WorkTypeModel workTypeModel) {
                    getView().setData(workTypeModel, type);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

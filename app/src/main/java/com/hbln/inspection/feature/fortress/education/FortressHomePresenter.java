package com.hbln.inspection.feature.fortress.education;

import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.hbln.inspection.R;
import com.hbln.inspection.model.CellBean;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressHomePresenter extends BasePresenterImpl<FortressHomeContract.View> implements FortressHomeContract.Presenter {
    @Override
    public void loadWenTiData() {
        getView().showLoading("");
        HttpRequest.getFortressService().zhuzhishlist("文体活动")
                .compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<FortressHomeModel>() {
                    @Override
                    public void result(FortressHomeModel objectModel) {
                        getView().setWenTiData(objectModel);
                    }
                }, new HttpError(getView()) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(getView()));
    }

    @Override
    public void loadDangZhiBuDaHuiData() {
        getView().showLoading("");
        HttpRequest.getFortressService().sanhuiyikelist("支部党员大会")
                .compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<FortressHomeModel>() {
                    @Override
                    public void result(FortressHomeModel objectModel) {
                        getView().setDangZhiBuDaHuiData(objectModel);
                    }
                }, new HttpError(getView()) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(getView()));
    }

    @Override
    public void loadBtnData() {
        List<CellBean> list = new ArrayList<>();
        CellBean bean;
        bean = new CellBean();
        bean.left = "文体活动";
        bean.leftResId = R.drawable.icon_fortress_btn_1;
        list.add(bean);
        bean = new CellBean();
        bean.left = "缴纳党费";
        bean.leftResId = R.drawable.icon_fortress_btn_2;
        list.add(bean);
        bean = new CellBean();
        bean.left = "群众工作";
        bean.leftResId = R.drawable.icon_fortress_btn_3;
        list.add(bean);
        bean = new CellBean();
        bean.left = "三会一课";
        bean.leftResId = R.drawable.icon_fortress_btn_0;
        list.add(bean);
//        bean = new CellBean();
//        bean.left = "支部换届";
//        bean.leftResId = R.drawable.icon_fortress_btn_4;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "组织管理";
//        bean.leftResId = R.drawable.icon_fortress_btn_5;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "阵地建设";
//        bean.leftResId = R.drawable.icon_fortress_btn_6;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "全部";
//        bean.leftResId = R.drawable.icon_fortress_btn_7;
//        list.add(bean);
        getView().showBtn(list);
    }
}

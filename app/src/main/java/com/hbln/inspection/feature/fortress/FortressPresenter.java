package com.hbln.inspection.feature.fortress;

import com.hbln.inspection.constans.URLs;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.FortressHomeModel;
import com.hbln.inspection.network.model.JianDuModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * <p>支部堡垒</p><br>
 *
 * @author - lwc
 * @date - 2017/12/19 10:07
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class FortressPresenter extends BasePresenterImpl<FortressContract.View> implements FortressContract.Presenter {

    @Override
    public void loadJiaoYuData() {
        getView().showLoading("");
        HttpRequest.getFortressService().zhuzhishlist("党员教育")
                .compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<FortressHomeModel>() {
                    @Override
                    public void result(FortressHomeModel objectModel) {
                        getView().setJiaoYuData(objectModel);
                    }
                }, new HttpError(getView()) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(getView()));
    }

    @Override
    public void loadJianDuData() {
        getView().showLoading("");
        HttpRequest.getFortressService().dangyuanjiandu("1", URLs.PAGE_SIZE)
                .compose(NetWorkInterceptor.<JianDuModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<JianDuModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<JianDuModel>() {
                    @Override
                    public void result(JianDuModel objectModel) {
                        getView().setJianDuData(objectModel);
                    }
                }, new HttpError(getView()) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(getView()));
    }
}

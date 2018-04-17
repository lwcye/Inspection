package com.hbln.inspection.feature.accout.register;

import android.text.TextUtils;

import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.DwLianDongModel;
import com.hbln.inspection.network.model.LoginModel;
import com.hbln.inspection.network.model.ObjectModel;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    public void loadDwData(String pid, final Action1<DwLianDongModel> action) {
        getView().showLoading("");
        if (TextUtils.isEmpty(pid)) {
            pid = "0";
        }
        HttpRequest.getUserService().dwliandong(pid)
                .compose(NetWorkInterceptor.<DwLianDongModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<DwLianDongModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<DwLianDongModel>() {
                    @Override
                    public void result(DwLianDongModel dwLianDongModel) {
                        if (action != null) {
                            action.call(dwLianDongModel);
                        }
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }

    @Override
    public void requestRegist(String username, String password, String repassword, String email, String nickname, String sfid, String dwid, String token) {
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShortToastSafe("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToastSafe("请输入密码");
            return;
        }
        if (!password.equals(repassword)) {
            ToastUtils.showShortToastSafe("两次输入的密码不一致");
            return;
        }
        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.showShortToastSafe("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(sfid)) {
            ToastUtils.showShortToastSafe("请输入身份证号");
            return;
        }
        if (TextUtils.isEmpty(dwid)) {
            ToastUtils.showShortToastSafe("请选择单位");
            return;
        }
        getView().showLoading("");
        HttpRequest.getUserService().register(username, password, repassword, email, nickname, sfid, dwid, token)
                .compose(getView().getBaseActivity().<LoginModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<LoginModel>() {
                    @Override
                    public void result(LoginModel errorModel) {
                        getView().resultRegist(errorModel);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }

    @Override
    public void verificationSfid(String name, String sfid, final Action0 action0) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToastSafe("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(sfid)) {
            ToastUtils.showShortToastSafe("请输入身份证号");
            return;
        }
        getView().showLoading("");
        HttpRequest.getUserService().useryanzheng(name, sfid)
                .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<ObjectModel>() {
                    @Override
                    public void result(ObjectModel errorModel) {
                        if (action0 != null) {
                            action0.call();
                        } else {
                            ToastUtils.showShortToastSafe(errorModel.info.toString());
                        }
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

package com.hbln.inspection.feature.accout.login;

import android.text.TextUtils;

import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.tencent.stat.StatService;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void initUserInfo() {
        LoginModel.initUserInfo();
        if (!TextUtils.isEmpty(URLs.UID) && !TextUtils.isEmpty(URLs.ACCESS_TOKEN)) {
            getView().resultLogin(new LoginModel());
        } else {
            // 用户登录事件，统计用户点击登录按钮的次数
            StatService.trackCustomKVEvent(getView().getBaseActivity(), "login", null);
        }
    }

    @Override
    public void requestLogin(final String username, final String password) {
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShortToastSafe("请输入用户名，默认电话号码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShortToastSafe("请输入密码");
            return;
        }
        getView().showLoading("登陆中");
        HttpRequest.getUserService().login(username, password, URLs.HTTP_TOKEN)
                .compose(getView().getBaseActivity().<LoginModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<LoginModel>() {
                    @Override
                    public void result(LoginModel loginModel) {
                        loginModel.saveUserInfo(username, password);
                        getView().resultLogin(loginModel);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

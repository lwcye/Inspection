package com.cmcc.inspection.feature.main.mainuser;

import com.cmcc.inspection.feature.accout.login.LoginActivity;
import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.UserInfoModel;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserPresenter extends BasePresenterImpl<MainUserContract.View> implements MainUserContract.Presenter {

    @Override
    public void loadUserInfo() {
        HttpRequest.getUserService().userinfo()
                .compose(NetWorkInterceptor.<UserInfoModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<UserInfoModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<UserInfoModel>() {
                    @Override
                    public void result(UserInfoModel userInfoModel) {
                        getView().resultUserInfo(userInfoModel);
                    }
                }, new HttpError(getView()), new HttpComplete(getView()));
    }

    @Override
    public void logout() {
        LoginModel.logout();
        LoginActivity.start(getView().getBaseActivity());
    }
}

package com.hbln.inspection.feature.main.mainuser;

import com.hbln.inspection.feature.accout.login.LoginActivity;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.UserInfoModel;

import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserPresenter extends BasePresenterImpl<MainUserContract.View> implements MainUserContract.Presenter {

    @Override
    public void loadUserInfo() {
        LoginModel.getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {
                getView().resultUserInfo(userInfo);
            }
        });
    }

    @Override
    public void logout() {
        LoginModel.logout();
        LoginActivity.start(getView().getBaseActivity());
    }
}

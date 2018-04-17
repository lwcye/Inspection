package com.hbln.inspection.feature.accout.login;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.LoginModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void resultLogin(LoginModel loginModel);

    }

    interface Presenter extends BasePresenter<View> {
        void initUserInfo();

        void requestLogin(String username, String password);

        void loadPatch();
    }
}

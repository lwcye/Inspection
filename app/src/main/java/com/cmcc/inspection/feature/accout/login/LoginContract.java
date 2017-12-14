package com.cmcc.inspection.feature.accout.login;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.LoginModel;

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
    }
}

package com.cmcc.inspection.feature.main.mainuser;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.UserInfoModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserContract {
    interface View extends BaseView {
        void resultUserInfo(UserInfoModel.UserInfo userInfoModel);
    }

    interface Presenter extends BasePresenter<View> {
        void loadUserInfo();

        void logout();
    }
}

package com.hbln.inspection.feature.main.mainuser;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.ObjectModel;
import com.hbln.inspection.network.model.UserInfoModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserContract {
    interface View extends BaseView {
        void resultUserInfo(UserInfoModel.UserInfo userInfoModel);
        void resultRead(ObjectModel userInfoModel);
    }

    interface Presenter extends BasePresenter<View> {
        void loadUserInfo();
        void loadRead();

        void logout();
    }
}

package com.hbln.inspection.feature.accout.register;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.DwLianDongModel;
import com.cmcc.lib_network.model.LoginModel;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseView {
        void resultRegist(LoginModel objectModel);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadDwData(String pid, Action1<DwLianDongModel> action);
        
        void requestRegist(String username, String password, String repassword, String email, String nickname, String sfid, String dwid, String token);
        
        void verificationSfid(String name, String sfid, Action0 action0);
    }
}

package com.cmcc.inspection.feature.accout.register;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.DwLianDongModel;

import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseView {
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadDwData(String pid, Action1<DwLianDongModel> action);
        
        void requestRegist();
        
        void verificationSfid();
    }
}

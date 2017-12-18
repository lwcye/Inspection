package com.cmcc.inspection.feature.fortress.manager;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.ManagerModel;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FortressManagerContract {
    interface View extends BaseView {
        void setManagerData(ManagerModel managerData);
    }

    interface  Presenter extends BasePresenter<View> {
        void loadManagerData();
    
    }
}

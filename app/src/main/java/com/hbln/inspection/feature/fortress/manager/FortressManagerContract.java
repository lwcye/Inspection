package com.hbln.inspection.feature.fortress.manager;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.ManagerModel;

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

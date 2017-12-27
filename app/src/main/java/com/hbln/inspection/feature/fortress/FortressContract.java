package com.hbln.inspection.feature.fortress;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.cmcc.lib_network.model.JianDuModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressContract {
    interface View extends BaseView {


        void setJianDuData(JianDuModel jianDuData);

        void setJiaoYuData(FortressHomeModel homeModel);
    }

    interface Presenter extends BasePresenter<View> {

        void loadJiaoYuData();

        void loadJianDuData();

    }
}

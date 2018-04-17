package com.hbln.inspection.feature.fortress;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.FortressHomeModel;
import com.hbln.inspection.network.model.JianDuModel;

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

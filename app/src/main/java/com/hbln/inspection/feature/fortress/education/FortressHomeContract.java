package com.hbln.inspection.feature.fortress.education;


import com.hbln.inspection.model.CellBean;
import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.FortressHomeModel;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressHomeContract {
    interface View extends BaseView {
        void setWenTiData(FortressHomeModel homeModel);

        void setBannerData(FortressHomeModel homeModel);

        void showBtn(List<CellBean> btnBean);

    }

    interface Presenter extends BasePresenter<View> {
        void loadWenTiData();

        void loadBannerData();
        
        void loadBtnData();
    }
}

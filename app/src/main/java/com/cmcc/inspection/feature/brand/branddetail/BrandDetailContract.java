package com.cmcc.inspection.feature.brand.branddetail;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.BrandDetailModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BrandDetailContract {
    interface View extends BaseView {
        void setDetail(BrandDetailModel detailModel);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadDetail(String ppid);
    }
}

package com.hbln.inspection.feature.brand.branddetail;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.BrandDetailModel;

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

package com.hbln.inspection.feature.brand;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.BrandModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BrandContract {
    interface View extends BaseView {
        void setData(BrandModel data);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}

package com.cmcc.inspection.feature.brand;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.BrandModel;

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

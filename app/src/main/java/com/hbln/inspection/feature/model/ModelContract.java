package com.hbln.inspection.feature.model;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.ModelModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ModelContract {
    interface View extends BaseView {
        void setGeRenData(ModelModel model);
        
        void setJiTiData(ModelModel model);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadGeRenData();
        
        void loadJiTiData();
    }
}

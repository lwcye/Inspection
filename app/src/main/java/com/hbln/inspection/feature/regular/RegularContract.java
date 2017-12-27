package com.hbln.inspection.feature.regular;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.RegularModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegularContract {
    interface View extends BaseView {
        void setData(RegularModel regularModel, int type);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData(int type, String title);
    }
}

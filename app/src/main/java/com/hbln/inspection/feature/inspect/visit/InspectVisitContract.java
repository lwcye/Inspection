package com.hbln.inspection.feature.inspect.visit;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.JiafangModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectVisitContract {
    interface View extends BaseView {
        void setData(JiafangModel data);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}

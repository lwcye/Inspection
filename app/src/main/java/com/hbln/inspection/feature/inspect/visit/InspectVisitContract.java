package com.hbln.inspection.feature.inspect.visit;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.JiafangModel;

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

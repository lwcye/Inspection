package com.cmcc.inspection.feature.school.detail;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.SchoolModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolDetailContract {
    interface View extends BaseView {
        void setData(SchoolModel.SchoolInfo data);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData(String id);
    }
}

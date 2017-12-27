package com.hbln.inspection.feature.workarena.workdynamic;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.WorkModel;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkDynamicContract {
    interface View extends BaseView {
        void setData(List<WorkModel.WorkInfoBean> list);
    }
    
    interface Presenter extends BasePresenter<View> {
        void getData();
        
        void searchData(String title);
    }
}

package com.hbln.inspection.feature.workarena.workdynamic;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.WorkModel;

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

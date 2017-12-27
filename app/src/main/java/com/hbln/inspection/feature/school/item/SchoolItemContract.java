package com.hbln.inspection.feature.school.item;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.SchoolModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolItemContract {
    interface View extends BaseView {
        void setData(SchoolModel schoolModel);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData(int index);
        
        void searchData(int index, String title);
    }
}

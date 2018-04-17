package com.hbln.inspection.feature.school.item;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.SchoolModel;

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

package com.hbln.inspection.feature.workarena.workininspection;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.WorkTypeModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkIninspectionContract {
    interface View extends BaseView {
        void setData(WorkTypeModel data, int type);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(int type, String date);
    }
}

package com.hbln.inspection.feature.workarena.workininspection;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.WorkTypeModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkIninspectionContract {
    interface View extends BaseView {
        void setData(WorkTypeModel data, int type);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(int type);
    }
}

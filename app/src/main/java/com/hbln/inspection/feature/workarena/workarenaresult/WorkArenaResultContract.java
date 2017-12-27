package com.hbln.inspection.feature.workarena.workarenaresult;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.WorkArenaModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkArenaResultContract {
    interface View extends BaseView {
        void setData(WorkArenaModel arenaModel);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}

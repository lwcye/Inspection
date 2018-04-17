package com.hbln.inspection.feature.workarena.workarenaresult;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.WorkArenaModel;

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

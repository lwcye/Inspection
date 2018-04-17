package com.hbln.inspection.feature.main;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.VersionModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void setVersion(VersionModel version);
    }

    interface Presenter extends BasePresenter<View> {
        void getVersion();

        void upDataVersion(String url);
    }
}

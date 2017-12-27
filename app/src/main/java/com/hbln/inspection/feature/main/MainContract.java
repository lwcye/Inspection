package com.hbln.inspection.feature.main;

import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.VersionModel;

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

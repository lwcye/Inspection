package com.cmcc.inspection.feature.accout.accountlist;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListContract {
    interface View extends BaseView {
        void setMailData(int index);
    }

    interface Presenter extends BasePresenter<View> {
        void getMailData(int index);
    }
}

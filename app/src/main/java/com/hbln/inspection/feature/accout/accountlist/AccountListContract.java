package com.hbln.inspection.feature.accout.accountlist;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.MailModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListContract {
    interface View extends BaseView {
        void setMailData(int index, MailModel mailModel);
    }

    interface Presenter extends BasePresenter<View> {
        void getMailData(int index);
    }
}

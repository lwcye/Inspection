package com.hbln.inspection.feature.accout.accountlist;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.MailModel;

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

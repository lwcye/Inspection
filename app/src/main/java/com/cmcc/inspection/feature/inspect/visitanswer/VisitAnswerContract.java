package com.cmcc.inspection.feature.inspect.visitanswer;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.JiafangModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VisitAnswerContract {
    interface View extends BaseView {
        void setData(JfShiTiModel jfShiTiModel);

        void submitSuccess(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData(JiafangModel.JiafangInfoBean bean);

        void submit(String sjid,
                    String name,
                    String guanxi,
                    String mobile,
                    String stids,
                    String daids);
    }
}

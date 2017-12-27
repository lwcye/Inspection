package com.hbln.inspection.feature.school.answer;

import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.JfShiTiModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerContract {
    interface View extends BaseView {
        void setData(JfShiTiModel jfShiTiModel);
        
        void submitSuccess(String message);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadData(String sjid, int type);
        
        void submit(String sjid,
                    String stids,
                    String daids);
        
        void submit(String sjid, String name, String guanxi, String mobile, String stids, String daids);
    }
    
}

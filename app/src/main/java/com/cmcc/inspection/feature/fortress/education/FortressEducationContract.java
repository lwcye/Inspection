package com.cmcc.inspection.feature.fortress.education;


import com.cmcc.inspection.model.CellBean;
import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FortressEducationContract {
    interface View extends BaseView {
        void showBtn(List<CellBean> btnBean);
    
        void showNews(List<CellBean> newsBean);
    }

    interface  Presenter extends BasePresenter<View> {
        void loadBtnData();
    
        void loadNewsData();
    }
}

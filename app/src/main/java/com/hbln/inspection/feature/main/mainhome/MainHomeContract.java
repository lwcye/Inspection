package com.hbln.inspection.feature.main.mainhome;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainHomeContract {
    interface View extends BaseView {
        
    }
    
    interface Presenter extends BasePresenter<View> {
        /**
         * 签到上传定位
         * @param beiZhu
         */
        void postTrackData(final String beiZhu);
    }
}

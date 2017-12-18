package com.cmcc.inspection.feature.inspect.track;


import com.cmcc.lib_common.mvp.BasePresenter;
import com.cmcc.lib_common.mvp.BaseView;
import com.cmcc.lib_network.model.TrackModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackContract {
    interface View extends BaseView {
        void setTrackData(TrackModel trackData);
    }
    
    interface Presenter extends BasePresenter<View> {
        void loadTrackData();
        
        void postTrackData(String beiZhu);
        
        void postTrackTag(String id, String beiZhu);
    }
}

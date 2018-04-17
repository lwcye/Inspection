package com.hbln.inspection.feature.inspect.track;


import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.TrackModel;

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

package com.hbln.inspection.feature.inspect.trackdetail;


import com.baidu.mapapi.map.BaiduMap;
import com.hbln.inspection.mvp.BasePresenter;
import com.hbln.inspection.mvp.BaseView;
import com.hbln.inspection.network.model.TrackModel;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackDetailContract {
    interface View extends BaseView {
        void setTrack(TrackModel track);
        void showTrackDetail(String title, double latitude, double longitude);
    
    }
    
    interface Presenter extends BasePresenter<View> {
        void initBaiduMap(BaiduMap baiduMap);
        
        void loadTrackData(String startTime, String endTime, String uids);
    }
}

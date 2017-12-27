package com.hbln.inspection.feature.inspect.track;

import com.baidu.location.BDLocation;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.utils.BaiduMapUtils;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.TrackModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackPresenter extends BasePresenterImpl<InspectTrackContract.View> implements InspectTrackContract.Presenter {
    
    @Override
    public void loadTrackData() {
        getView().showLoading("");
        HttpRequest.getTrackService().index("1", URLs.PAGE_SIZE)
            .compose(NetWorkInterceptor.<TrackModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<TrackModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<TrackModel>() {
                @Override
                public void result(TrackModel objectModel) {
                    getView().setTrackData(objectModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    @Override
    public void postTrackData(final String beiZhu) {
        getView().showLoading("");
        BaiduMapUtils.getInstance().beginLocation(null, false, new BaiduMapUtils.OnLocationListener() {
            @Override
            public void locationListener(BDLocation location) {
                HttpRequest.getTrackService().add(TimeUtils.getNowTimeMills() / 1000 + "",
                    location.getLongitude() + "",
                    location.getLatitude() + "",
                    location.getAddrStr(),
                    beiZhu)
                    .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
                    .compose(getView().getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                    .subscribe(new HttpResult<ObjectModel>() {
                        @Override
                        public void result(ObjectModel objectModel) {
                            ToastUtils.showShortToastSafe(objectModel.info.toString());
                        }
                    }, new HttpError(getView()) {
                        @Override
                        public void call(Throwable throwable) {
                        }
                    }, new HttpComplete(getView()));
            }
        });
    }
    
    @Override
    public void postTrackTag(String id, String beiZhu) {
        getView().showLoading("");
        HttpRequest.getTrackService().beizhu(id, beiZhu)
            .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<ObjectModel>() {
                @Override
                public void result(ObjectModel objectModel) {
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
}

package com.cmcc.inspection.feature.inspect.track;

import com.baidu.location.BDLocation;
import com.cmcc.inspection.mvp.BasePresenterImpl;
import com.cmcc.inspection.utils.BaiduMapUtils;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_utils.utils.TimeUtils;
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
                .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<ObjectModel>() {
                    @Override
                    public void result(ObjectModel objectModel) {
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
                            }
                        }, new HttpError(getView()), new HttpComplete(getView()));
            }
        });

    }
}

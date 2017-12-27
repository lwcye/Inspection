package com.hbln.inspection.feature.main.mainhome;

import com.baidu.location.BDLocation;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.utils.BaiduMapUtils;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainHomePresenter extends BasePresenterImpl<MainHomeContract.View> implements MainHomeContract.Presenter{
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
}

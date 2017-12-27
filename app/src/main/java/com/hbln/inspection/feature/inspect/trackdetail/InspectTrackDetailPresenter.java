package com.hbln.inspection.feature.inspect.trackdetail;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.utils.BaiduMapUtils;
import com.hbln.inspection.widget.DialogUtils;
import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.TrackModel;
import com.cmcc.lib_utils.utils.IntentUtils;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.NetworkUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.functions.Action1;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackDetailPresenter extends BasePresenterImpl<InspectTrackDetailContract.View> implements InspectTrackDetailContract.Presenter, BaiduMapUtils.OnLocationListener, BaiduMap.OnMarkerClickListener {
    BaiduMap mBaiduMap;
    
    @Override
    public void initBaiduMap(BaiduMap baiduMap) {
        // 判断是否有网络,有网络就请求数据
        checkNetWorkAvailable();
        mBaiduMap = baiduMap;
        baiduMap.setOnMarkerClickListener(this);
        checkPermission();
        BaiduMapUtils.getInstance().initBaiduMap(baiduMap, 0);
    }
    
    @Override
    public void loadTrackData() {
        getView().showLoading("");
        HttpRequest.getTrackService().index("1", URLs.PAGE_SIZE)
            .compose(NetWorkInterceptor.<TrackModel>retrySessionCreator())
            .compose(getView().getBaseActivity().<TrackModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<TrackModel>() {
                @Override
                public void result(TrackModel objectModel) {
                    getView().setTrack(objectModel);
                }
            }, new HttpError(getView()), new HttpComplete(getView()));
    }
    
    /**
     * 判断手机网络是否连接
     */
    private void checkNetWorkAvailable() {
        RxPermissions rxPermissions = new RxPermissions(((BaseActivity) getView()));
        rxPermissions.request(Manifest.permission.ACCESS_NETWORK_STATE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    if (!NetworkUtils.isConnected()) {
                        DialogUtils.getInstance().showDefTwoBtn(((BaseActivity) getView()), "无法链接到网络,是否打开网络", null, "取消", "打开", new DialogUtils.onDialogClickListener() {
                            @Override
                            public void dialogClickListener(DialogInterface dialog) {
                                dialog.cancel();
                            }
                        }, new DialogUtils.onDialogClickListener() {
                            @Override
                            public void dialogClickListener(DialogInterface dialog) {
                                dialog.cancel();
                                IntentUtils.jumpSetting();
                            }
                        });
                    } else {
                        //在判断有网络有，判断是否有GPS导航
                        checkGpsSetting();
                    }
                }
            }
        });
    }
    
    /**
     * 判断GPS开关是否开启
     */
    private void checkGpsSetting() {
        LocationManager locManager = (LocationManager) ((BaseActivity) getView()).getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
            DialogUtils.getInstance().showDefTwoBtn((BaseActivity) getView(), "您GPS定位开关未开启,开启后定位将更加精确", null, "取消", "开启", new DialogUtils.onDialogClickListener() {
                @Override
                public void dialogClickListener(DialogInterface dialog) {
                    dialog.cancel();
                }
            }, new DialogUtils.onDialogClickListener() {
                @Override
                public void dialogClickListener(DialogInterface dialog) {
                    dialog.cancel();
                    // 转到手机设置界面，用户设置GPS
                    IntentUtils.jumpGpsSetting();
                }
            });
        }
    }
    /**
     * 请求百度地图权限，详情可查看http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/androidmnotice
     */
    private void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(((BaseActivity) getView()));
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    BaiduMapUtils.getInstance().beginLocation(mBaiduMap, false, InspectTrackDetailPresenter.this);
                } else {
                    ActivityCompat.shouldShowRequestPermissionRationale(((BaseActivity) getView()), Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            }
        });
        
        if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe();
        }
        if (!rxPermissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe();
        }
    }
    
    @Override
    public void locationListener(BDLocation location) {
        LogUtils.e(location);
    }
    
    @Override
    public boolean onMarkerClick(Marker marker) {
        getView().showTrackDetail(marker.getTitle(), marker.getPosition().latitude, marker.getPosition().longitude);
        return false;
    }
}

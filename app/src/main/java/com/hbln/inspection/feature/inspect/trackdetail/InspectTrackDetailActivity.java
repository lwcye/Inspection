package com.hbln.inspection.feature.inspect.trackdetail;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.cmcc.lib_network.model.TrackModel;
import com.cmcc.lib_utils.utils.ConvertUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.utils.BaiduMapUtils;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackDetailActivity extends MVPBaseActivity<InspectTrackDetailContract.View, InspectTrackDetailPresenter> implements InspectTrackDetailContract.View, View.OnClickListener {
    public static final String INTENT_BEAN = "bean";
    /** 轨迹追踪 */
    private TextView mBtbInspectTrackDetail;
    private TrackModel.InfoBean mBean;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    
    @Override
    protected InspectTrackDetailPresenter createPresenter() {
        return new InspectTrackDetailPresenter();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    
    public static void start(Context context, TrackModel.InfoBean infoBean) {
        Intent starter = new Intent(context, InspectTrackDetailActivity.class);
        starter.putExtra(INTENT_BEAN, infoBean);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inpect_track_detail);
        mBean = getIntent().getParcelableExtra(INTENT_BEAN);
        initView();
        mPresenter.initBaiduMap(mBaiduMap);
        if (mBean == null) {
            mPresenter.loadTrackData();
        } else {
            showLoc(mBean);
        }
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true)
            .setTitle("监督管理");
        mBtbInspectTrackDetail = (TextView) findViewById(R.id.btb_inspect_track_detail);
        mBtbInspectTrackDetail.setOnClickListener(this);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_track_detail);
        mBaiduMap = mMapView.getMap();
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btb_inspect_track_detail:
                mBaiduMap.clear();
                mPresenter.loadTrackData();
                break;
        }
    }
    
    public void showLoc(TrackModel.InfoBean infoBean) {
        Marker overlay = (Marker) BaiduMapUtils.getInstance().addOverlay(mBaiduMap, Double.valueOf(infoBean.latitude), Double.valueOf(infoBean.longitude), R.drawable.icon_track_map);
        overlay.setTitle(infoBean.times + "\n" + "备注：" + infoBean.beizhu);
    }
    
    
    @Override
    public void setTrack(TrackModel track) {
        if (track.info != null && track.info.size() > 0) {
            for (TrackModel.InfoBean infoBean : track.info) {
                showLoc(infoBean);
            }
            if (track.info.size() > 1) {
                List<LatLng> points = new ArrayList<>();
                for (TrackModel.InfoBean infoBean : track.info) {
                    points.add(new LatLng(Double.valueOf(infoBean.latitude), Double.valueOf(infoBean.longitude)));
                }
                OverlayOptions ooPolyline = new PolylineOptions()
                    .width(ConvertUtils.dp2px(5))
                    .visible(true)
                    .color(getCompatColor(R.color.colorAccentRed))
                    .points(points);
                mBaiduMap.addOverlay(ooPolyline);
            }
        }
    }
    
    @Override
    public void showTrackDetail(String title, double latitude, double longitude) {
        TextView mOverLayAgent = new TextView(this);
        mOverLayAgent.setBackgroundResource(R.drawable.img_bg_map_overlayer_title);
        mOverLayAgent.setText(title);
        mOverLayAgent.setTextColor(Color.WHITE);
        mOverLayAgent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.t14));
        mOverLayAgent.setGravity(Gravity.CENTER);
        LatLng ll = new LatLng(latitude, longitude);
        //图标的高度是103px
        InfoWindow infoWindow = new InfoWindow(mOverLayAgent, ll, -163);
        mBaiduMap.showInfoWindow(infoWindow);
    }
}

package com.cmcc.inspection.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.cmcc.inspection.R;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.cmcc.lib_utils.utils.Utils;


/**
 * 百度地图帮助类
 *
 * @author lwc
 * @date 2017/2/23 15:13
 * @note - 单例模式
 * 1. beginLocation -- 开始定位
 * 2. initBaiduMap -- 初始化百度地图，主要设置缩放值
 * 3. moveMapToLocation -- 移动地图到经纬度处
 * 4. addOverlay -- 为地图添加覆盖物
 * 5. showAddressWithLocation -- 通过经纬度获取地址信息
 * 6. getLocationWithAddress -- 通过地址信息获取经纬度
 * <p>
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BaiduMapUtils {
    /** 默认地图缩放大小，值越大越地图详细 */
    public static final int MAP_ZOOM_DEFAULT = 17;
    /** 默认地图缩放间隔 */
    public static final int MAP_ZOOM_DIV = 3;
    /** 默认地图缩放间隔 */
    private static final int RETRY_COUNT = 10;
    /** 单例 */
    private static BaiduMapUtils sOurInstance = new BaiduMapUtils();
    /** 默认城市信息 */
    public static final String CITY_DEFAULT = "重庆";
    /** 定位管理器 */
    private LocationClient mLocationClient;
    /** 地图编码搜索器 */
    private GeoCoder mGeoCoder;
    /** poi搜索器 */
    private PoiSearch mPoiSearch;
    /** 初始化建议搜索模块 **/
    private SuggestionSearch mSuggestionSearch;
    /** 百度地图定位详细信息 **/
    private BDLocation mBDLocation;
    /** 定位次数 */
    private int mLocationNum = 0;

    /**
     * 无参构造类
     */
    private BaiduMapUtils() {
    }

    /**
     * 单例模式
     *
     * @return BaiduMapUtils
     */
    public static BaiduMapUtils getInstance() {
        return sOurInstance;
    }

    /**
     * 开启定位，并在BaiduMapUtils保存Location信息
     *
     * @param baiduMap 百度地图
     * @param locationListener 监听获取location信息
     * @param isUseCache 是否使用缓存
     */
    public void beginLocation(final BaiduMap baiduMap, boolean isUseCache, @NonNull final OnLocationListener locationListener) {
        if (mBDLocation != null && isUseCache && mBDLocation.hasAddr()) {
            locationListener.locationListener(mBDLocation);
            return;
        }
        // 定位初始化
        mLocationNum = 0;
        mLocationClient = new LocationClient(Utils.getContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.d(bdLocation);
                if (bdLocation == null) {
                    // 若定位失败，重试10次
                    if (mLocationNum < RETRY_COUNT) {
                        mLocationNum++;
                        ToastUtils.showShortToastSafe(R.string.o2o_error_location_retrying);
                        return;
                    } else {
                        ToastUtils.showShortToastSafe(R.string.o2o_error_location);
                        mLocationClient.stop();

                        return;
                    }
                } else {
                    //定位错误码 参考百度地图API http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/ermsg
                    if (!bdLocation.hasAddr()) {
                        int locType = bdLocation.getLocType();
                        if (locType == BDLocation.TypeCriteriaException) {
                            ToastUtils.showShortToastSafe(R.string.o2o_error_location_canot_fint);
                        } else if (locType == BDLocation.TypeNetWorkException) {
                            ToastUtils.showShortToastSafe(R.string.o2o_error_location_network);
                        } else if (locType == BDLocation.TypeServerError) {
                            ToastUtils.showShortToastSafe(R.string.o2o_error_location_permission);
                        } else {
                            ToastUtils.showShortToastSafe(R.string.o2o_error_location);
                        }
                        mLocationClient.stop();

                        return;
                    }
                }
                mLocationClient.stop();

                mBDLocation = bdLocation;
                if (null != baiduMap) {
                    //增加自己的定位信息
                    addCurrentLocationOverlay(baiduMap);
                }
                locationListener.locationListener(mBDLocation);
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {
            }
        });
        initLocation(mLocationClient);
        mLocationClient.start();
    }

    /**
     * 初始化定位的设置参数
     *
     * @param mLocationClient 定位管理器
     */
    private void initLocation(LocationClient mLocationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 3500;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    /**
     * 初始化百度地图的参数
     *
     * @param baiduMap 百度地图实体
     * @param zoom 默认地图缩放大小，值越大越地图详细
     */
    public void initBaiduMap(final BaiduMap baiduMap, int zoom) {
        //1. 定义缩放比例
        if (zoom <= 0) {
            zoom = MAP_ZOOM_DEFAULT;
        }
        //注意，调整地图缩放度应该在地图加载完成以后进行
        final int finalZoom = zoom;
        baiduOption(baiduMap, finalZoom);
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                baiduOption(baiduMap, finalZoom);
            }
        });
    }

    /**
     * 百度初始化参数设置
     *
     * @param baiduMap 百度地图
     * @param finalZoom 缩放比例
     */
    private void baiduOption(BaiduMap baiduMap, int finalZoom) {
        // 设置最大及最小缩放比例
        baiduMap.setMaxAndMinZoomLevel(finalZoom + MAP_ZOOM_DIV, finalZoom - MAP_ZOOM_DIV);
        // 设置缩放比例
        MapStatus.Builder builder = new MapStatus.Builder().zoom(finalZoom);
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
        baiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 添加自身地位的覆盖物
     *
     * @param baiduMap 百度地图
     */
    public void addCurrentLocationOverlay(@NonNull final BaiduMap baiduMap) {
        beginLocation(baiduMap, true, new OnLocationListener() {
            @Override
            public void locationListener(BDLocation location) {
                baiduMap.setMyLocationEnabled(true);
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mBDLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mBDLocation.getDirection()).latitude(mBDLocation.getLatitude())
                        .longitude(mBDLocation.getLongitude()).build();
                baiduMap.setMyLocationData(locData);
            }
        });
    }

    /**
     * 将百度地图移动到指定经纬度处
     *
     * @param baiduMap 百度地图实体
     * @param latitude 纬度
     * @param longitude 经度
     */
    public void moveMapToLocation(BaiduMap baiduMap, double latitude, double longitude) {
        //设定中心点坐标
        LatLng center = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder().target(center).zoom(MAP_ZOOM_DEFAULT);
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
        //改变地图状态
        baiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 移动到当前位置
     *
     * @param baiduMap 百度地图
     */
    public void moveMapToCurrentLocation(final BaiduMap baiduMap) {
        beginLocation(baiduMap, true, new OnLocationListener() {
            @Override
            public void locationListener(BDLocation location) {
                //设定中心点坐标
                LatLng center = new LatLng(mBDLocation.getLatitude(), mBDLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder().zoom(MAP_ZOOM_DEFAULT).target(center);
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
                //改变地图状态
                baiduMap.animateMapStatus(mMapStatusUpdate);
            }
        });
    }

    /**
     * 通过经纬度信息来获取地址信息
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @param addressWithLocationListener 地址信息获取监听器
     */
    public void getAddressWithLocation(final double latitude, final double longitude, final OnAddressWithLocationListener addressWithLocationListener) {
        if (null == mGeoCoder) {
            mGeoCoder = GeoCoder.newInstance();
        }
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    getAddressWithLocation(latitude, longitude, addressWithLocationListener);
                    return;
                }
                //获取反向地理编码结果
                addressWithLocationListener.getAddressWithLocationListener(reverseGeoCodeResult);
            }
        });
        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longitude)));
    }

    /**
     * 通过地址信息获取经纬度
     *
     * @param address 地址信息
     * @param locationWithAddressListener 经纬度获取监听器
     */
    public void getLocationWithAddress(final String address, final OnLocationWithAddressListener locationWithAddressListener) {
        if (null == mGeoCoder) {
            mGeoCoder = GeoCoder.newInstance();
        }
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    getLocationWithAddress(address, locationWithAddressListener);
                    return;
                }
                //获取反向地理编码结果
                locationWithAddressListener.locationWithAddressListener(geoCodeResult);
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
        mGeoCoder.geocode(new GeoCodeOption().city(CITY_DEFAULT).address(address));
    }

    /**
     * 在百度地图上指定的经纬度添加覆盖物
     *
     * @param baiduMap 百度地图
     * @param latitude 纬度
     * @param longitude 经度
     * @param overlayResId 覆盖物ResId
     */
    public Overlay addOverlay(BaiduMap baiduMap, double latitude, double longitude, @DrawableRes int overlayResId) {
        //构建Marker图标
        BitmapDescriptor bitmap;
        bitmap = BitmapDescriptorFactory.fromResource(overlayResId);
        //构建MarkerOption，用于在地图上添加Marker
        LatLng point = new LatLng(latitude, longitude);
        MarkerOptions option = new MarkerOptions()
                .position(point)
                .draggable(false)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        return baiduMap.addOverlay(option);
    }

    /**
     * 根据2个坐标点进行缩放地图
     *
     * @param baiduMap 百度地图
     * @param centerLatitude 中心纬度
     * @param centerLongitude 中心经度
     * @param otherLatitude 另一个点纬度
     * @param otherLongitude 另一个点经度
     */
    public void zoomWithLatLngBounds(final BaiduMap baiduMap, final double centerLatitude, final double centerLongitude, final double otherLatitude, final double otherLongitude) {
        //改变地图状态
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(centerLatitude, centerLongitude));
        builder.include(new LatLng(otherLatitude, otherLongitude));
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLngBounds(builder.build());
        baiduMap.animateMapStatus(update);

    }

    /**
     * 搜索关键字
     *
     * @param keyword 关键字
     * @param onGetSuggestionResultListener 获取搜索信息监听
     */
    public void searchWithKeyword(String keyword, OnGetSuggestionResultListener onGetSuggestionResultListener) {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(onGetSuggestionResultListener);
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(keyword).city(CITY_DEFAULT).citylimit(true));
    }

    /**
     * 通过兴趣点uid搜索详细信息
     *
     * @param uid 兴趣点uid
     * @param onGetDetailResult 信息回调
     */
    public void searchAddressWithSuggestionInfo(String uid, final OnGetDetailResult onGetDetailResult) {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //详情检索失败
                } else {
                    //检索成功
                    onGetDetailResult.getDetailResult(poiDetailResult);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(uid));
    }

    /**
     * 释放内存相关的操作
     */
    public void onDestroy(@Nullable BaiduMap baiduMap) {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
        }
        if (mGeoCoder != null) {
            mGeoCoder.destroy();
        }
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(true);
            baiduMap.clear();
        }
    }

    /**
     * 定位监听器
     */
    public interface OnLocationListener {
        /**
         * 定位的监听
         *
         * @param location 百度的定位
         */
        void locationListener(BDLocation location);
    }

    /**
     * 通过经纬度监听地址位置
     */
    public interface OnAddressWithLocationListener {
        /**
         * 通过经纬度监听地址位置
         *
         * @param reverseGeoCodeResult 地址位置
         */
        void getAddressWithLocationListener(ReverseGeoCodeResult reverseGeoCodeResult);
    }

    /**
     * 通过地址信息监听经纬度
     */
    interface OnLocationWithAddressListener {
        /**
         * 通过地址信息监听经纬度
         *
         * @param geoCodeResult 经纬度
         */
        void locationWithAddressListener(GeoCodeResult geoCodeResult);
    }

    /**
     * 获得地址详细信息
     */
    public interface OnGetDetailResult {
        /**
         * 获得详细地址
         *
         * @param poiDetailResult 地址详细信息
         */
        void getDetailResult(PoiDetailResult poiDetailResult);
    }
}

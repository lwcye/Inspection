package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.ObjectModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/18
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface TrackService {
    /**
     * 讲堂
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/guiji/index")
    Observable<ObjectModel> index(
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    /**
     * 讲堂
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/guiji/add")
    Observable<ObjectModel> add(
            @Field("times") String times,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("address") String address,
            @Field("beizhu") String beizhu
    );

    /**
     * 讲堂
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/guiji/beizhu")
    Observable<ObjectModel> beizhu(
            @Field("id") String id,
            @Field("beizhu") String beizhu
    );
}

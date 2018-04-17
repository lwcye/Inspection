package com.hbln.inspection.network.service;


import com.hbln.inspection.network.model.ModelAwardModel;
import com.hbln.inspection.network.model.ModelModel;
import com.hbln.inspection.network.model.WebViewModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/16 19:17
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface ModelService {
    @POST("public/api/biaozhang/gereninfo")
    Observable<ModelModel> gereninfo(
    );

    @FormUrlEncoded
    @POST("public/api/biaozhang/gerenview")
    Observable<WebViewModel> gerenview(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/api/biaozhang/danweiview")
    Observable<WebViewModel> danweiview(
            @Field("id") String id
    );

    @POST("public/api/biaozhang/danweiinfo")
    Observable<ModelModel> danweiinfo(
    );

    @FormUrlEncoded
    @POST("public/api/biaozhang/biaozhanggerenshiji")
    Observable<ModelAwardModel> biaozhanggerenshiji(
            @Field("sxid") String sxid
    );

    @FormUrlEncoded
    @POST("public/api/biaozhang/biaozhangdanweishiji")
    Observable<ModelAwardModel> biaozhangdanweishiji(
            @Field("sxid") String sxid
    );
}

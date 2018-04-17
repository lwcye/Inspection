package com.hbln.inspection.network.service;


import com.hbln.inspection.network.model.WebViewModel;
import com.hbln.inspection.network.model.WorkArenaModel;
import com.hbln.inspection.network.model.WorkModel;
import com.hbln.inspection.network.model.WorkTypeModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 0:37
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface WorkService {
    @FormUrlEncoded
    @POST("public/api/main/jobdongtaisoso")
    Observable<WorkModel> jobdongtaisoso(
            @Field("title") String title,
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    @FormUrlEncoded
    @POST("public/api/main/jobdongtai")
    Observable<WorkModel> jobdongtai(
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize

    );

    @FormUrlEncoded
    @POST("public/api/main/jobdongtaiview")
    Observable<WebViewModel> jobdongtaiview(
            @Field("id") String id
    );

    @POST("public/api/yewu/userpaiming")
    Observable<WorkArenaModel> userpaiming(
    );

    @FormUrlEncoded
    @POST("public/api/yewu/getinfo")
    Observable<WorkTypeModel> getinfo(
            @Field("shijian") String shijian,
            @Field("type") String type,
            @Field("keshi") String keshi
    );
}

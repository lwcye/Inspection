package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.SchoolModel;
import com.cmcc.lib_network.model.WebViewModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 20:55
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface ShcoolService {
    /**
     * 讲堂
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/main/jiangtang")
    Observable<SchoolModel> jiangtang(
        @Field("pageNo") String pageNo,
        @Field("pageSize") String pageSize,
        @Field("typeid") String typeid
    );
    
    /**
     * 讲堂搜索
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/main/jiangtangsoso")
    Observable<SchoolModel> jiangtangsoso(
        @Field("title") String title,
        @Field("pageNo") String pageNo,
        @Field("pageSize") String pageSize,
        @Field("typeid") String typeid
    );
    
    /**
     * 讲堂搜索
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/main/jiangtangview")
    Observable<WebViewModel> jiangtangview(
        @Field("id") String id
    );
}

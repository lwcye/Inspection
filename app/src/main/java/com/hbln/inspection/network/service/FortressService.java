package com.hbln.inspection.network.service;


import com.hbln.inspection.network.model.FortressHomeModel;
import com.hbln.inspection.network.model.JianDuModel;
import com.hbln.inspection.network.model.ManagerModel;
import com.hbln.inspection.network.model.WebViewModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/18 23:20
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface FortressService {
    /**
     * 家访试卷
     *
     * @return
     */
    @POST("public/api/zhibu/dangyuanguangli")
    Observable<ManagerModel> dangyuanguangli(
    );
    
    /**
     * 家访试卷
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/zhibu/dangyuanjiandu")
    Observable<JianDuModel> dangyuanjiandu(
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/dangyuanjianduview")
    Observable<WebViewModel> dangyuanjianduview(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/zhuzhishlist")
    Observable<FortressHomeModel> zhuzhishlist(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/zhuzhiview")
    Observable<WebViewModel> zhuzhiview(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/sanhuiyikelist")
    Observable<FortressHomeModel> sanhuiyikelist(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/sanhuiyikeview")
    Observable<WebViewModel> sanhuiyikeview(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/qzgongzuolist")
    Observable<FortressHomeModel> qzgongzuolist(
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    @FormUrlEncoded
    @POST("public/api/zhibu/qzgzview")
    Observable<WebViewModel> qzgzview(
            @Field("id") String id
    );
}

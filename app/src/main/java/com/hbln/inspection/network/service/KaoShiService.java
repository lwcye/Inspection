package com.hbln.inspection.network.service;


import com.hbln.inspection.network.model.AnswerModel;
import com.hbln.inspection.network.model.JfShiTiModel;
import com.hbln.inspection.network.model.KaoShiModel;
import com.hbln.inspection.network.model.ObjectModel;
import com.hbln.inspection.network.model.TestListModel;
import com.hbln.inspection.network.model.TiHuiModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/19 20:46
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface KaoShiService {
    @FormUrlEncoded
    @POST("public/api/kaoshi/index")
    Observable<KaoShiModel> index(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("public/api/kaoshi/shiti")
    Observable<JfShiTiModel> shiti(
            @Field("sjid") String sjid
    );

    @FormUrlEncoded
    @POST("public/api/kaoshi/wenjuan")
    Observable<AnswerModel> wenjuan(
            @Field("sjid") String sjid,
            @Field("stids") String stids,
            @Field("daids") String daids
    );

    @POST("public/api/kaoshi/tihuijiaoliu")
    Observable<KaoShiModel> tihuijiaoliu(
    );


    @FormUrlEncoded
    @POST("public/api/kaoshi/thjlloglist")
    Observable<TiHuiModel> thjlloglist(
            @Field("tid") String tid
    );


    @FormUrlEncoded
    @POST("public/api/kaoshi/thjllogadd")
    Observable<ObjectModel> thjllogadd(
            @Field("tid") String tid,
            @Field("content") String content
    );

    /**
     * 查看已经做过的试题
     *
     * @return
     */
    @POST("public/api/kaoshi/chengjiinfo")
    Observable<TestListModel> chengjiinfo();

    @FormUrlEncoded
    /**
     * 查看做过的试题，详情
     */
    @POST("public/api/kaoshi/chengjiview")
    Observable<JfShiTiModel> chengjiview(
            @Field("id") String id
    );
}

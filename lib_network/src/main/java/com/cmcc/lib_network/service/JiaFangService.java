package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.JiafangModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 1:00
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface JiaFangService {
    /**
     * 家访列表
     *
     * @return
     */
    @POST("public/api/jiafang/index")
    Observable<JiafangModel> index(
    );
    
    /**
     * 家访试卷
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/jiafang/shiti")
    Observable<JfShiTiModel> shiti(
        @Field("sjid") String sjid,
        @Field("uid") String uid
    );
    
    /**
     * 家访试卷提交
     *
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/jiafang/wenjuan")
    Observable<JiafangModel> wenjuan(
        @Field("sjid") String sjid,
        @Field("name") String name,
        @Field("uid") String uid,
        @Field("guanxi") String guanxi,
        @Field("mobile") String mobile,
        @Field("stids") String stids,
        @Field("daids") String daids
    );
}

package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.KaoShiModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.TiHuiModel;

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
    Observable<ObjectModel> wenjuan(
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
}

package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.RegularModel;
import com.cmcc.lib_network.model.WebViewModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/16 17:06
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface RegularService {
    @FormUrlEncoded
    @POST("public/api/zhiduguiding/listinfo")
    Observable<RegularModel> listinfo(
        @Field("type") String type,
        @Field("title") String title
    );
    
    @FormUrlEncoded
    @POST("public/api/zhiduguiding/zdview")
    Observable<WebViewModel> zdview(
        @Field("id") String id
    );
}

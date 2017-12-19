package com.cmcc.lib_network.service;

import com.cmcc.lib_network.model.BrandDetailModel;
import com.cmcc.lib_network.model.BrandModel;
import com.cmcc.lib_network.model.WebViewModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/17 6:34
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface PingPaiService {
    @POST("public/api/pingpai/index")
    Observable<BrandModel> index(
    );
    
    @FormUrlEncoded
    @POST("public/api/pingpai/view")
    Observable<BrandDetailModel> view(
        @Field("ppid") String ppid
    );
    
    @FormUrlEncoded
    @POST("public/api/pingpai/waixuanview")
    Observable<WebViewModel> waixuanview(
        @Field("id") String id
    );
}

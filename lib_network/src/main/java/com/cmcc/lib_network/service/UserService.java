package com.cmcc.lib_network.service;


import com.cmcc.lib_network.model.DwLianDongModel;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.MailModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.UserInfoModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>用户的接口</p><br>
 *
 * @author lwc
 * @date 2017/4/7 20:16
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface UserService {
    @FormUrlEncoded
    @POST("public/api/logintoken/register")
    Observable<LoginModel> register(
        @Field("username") String username,
        @Field("password") String password,
        @Field("repassword") String repassword,
        @Field("email") String email,
        @Field("nickname") String nickname,
        @Field("sfid") String sfid,
        @Field("dwid") String dwid,
        @Field("token") String token
    );
    
    @FormUrlEncoded
    @POST("public/api/logintoken/login")
    Observable<LoginModel> login(
        @Field("username") String username,
        @Field("password") String password,
        @Field("token") String token
    );
    
    @FormUrlEncoded
    @POST("public/api/logintoken/useryanzheng")
    Observable<ObjectModel> useryanzheng(
        @Field("name") String name,
        @Field("sfid") String sfid
    );
    
    @FormUrlEncoded
    @POST("public/api/logintoken/dwliandong")
    Observable<DwLianDongModel> dwliandong(
        @Field("pid") String pid
    );
    
    @POST("public/api/main/userinfo")
    Observable<UserInfoModel> userinfo(
    );
    
    @FormUrlEncoded
    @POST("public/api/main/tongxun")
    Observable<MailModel> tongxun(
        @Field("name") String name
    );
    
    @FormUrlEncoded
    @POST("public/api/logintoken/getversion")
    Observable<ObjectModel> getversion(
        @Field("version") String version
    );
}

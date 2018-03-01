package com.cmcc.lib_network.service;


import com.cmcc.lib_network.model.CommentModel;
import com.cmcc.lib_network.model.DwLianDongModel;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.MailModel;
import com.cmcc.lib_network.model.MessageModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.UserInfoModel;
import com.cmcc.lib_network.model.VersionModel;
import com.cmcc.lib_network.model.ZanModel;

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

    /**
     * @param sfid 身份证号码
     * @param username 用户账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/logintoken/changepassword")
    Observable<ObjectModel> changepassword(
            @Field("sfid") String sfid,
            @Field("username") String username,
            @Field("password") String password
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
    Observable<VersionModel> getversion(
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("public/api/main/pinglunadd")
    Observable<ObjectModel> pinglunadd(
            @Field("sfid") String sfid,
            @Field("content") String content,
            @Field("catid") String catid,
            @Field("sxid") String sxid
    );

    @FormUrlEncoded
    @POST("public/api/main/pinglunshanchu")
    Observable<ObjectModel> pinglunshanchu(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/api/main/pingluninfo")
    Observable<CommentModel> pingluninfo(
            @Field("catid") String catid,
            @Field("sxid") String sxid
    );

    /**
     * 消息列表
     *
     * @return
     */
    @POST("public/api/main/tongzhilistinfo")
    Observable<MessageModel> tongzhilistinfo(
    );

    /**
     * 点赞列表
     *
     * @param typeid
     * @param sxid
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/main/pinglunzanshuliang")
    Observable<ZanModel> pinglunzanshuliang(
            @Field("typeid") String typeid,
            @Field("sxid") String sxid
    );

    /**
     * 点赞
     *
     * @param typeid
     * @param sxid
     * @return
     */
    @FormUrlEncoded
    @POST("public/api/main/dianzhanadd")
    Observable<ObjectModel> dianzhanadd(
            @Field("sfid") String sfid,
            @Field("yeszan") String yeszan,
            @Field("typeid") String typeid,
            @Field("sxid") String sxid,
            @Field("nozan") String nozan
    );
}

package com.hbln.inspection.network.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hbln.inspection.base.BaseApp;
import com.hbln.inspection.constans.URLs;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/14
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class LoginModel extends ResultModel {
    public String uid;
    public String access_token;

    /**
     * 初始化数据
     */
    public static void initUserInfo() {
        URLs.ACCESS_TOKEN = BaseApp.getSpUtils().getString(URLs.ACCESS_TOKEN_KEY, "");
        URLs.UID = BaseApp.getSpUtils().getString(URLs.UID_KEY, "");
    }

    public static void logout() {
        URLs.ACCESS_TOKEN = "";
        URLs.UID = "";
        BaseApp.getSpUtils().put(URLs.ACCESS_TOKEN_KEY, "");
        BaseApp.getSpUtils().put(URLs.UID_KEY, "");
        BaseApp.getSpUtils().put(URLs.USERNAME_KEY, "");
        BaseApp.getSpUtils().put(URLs.PASSWORD_KEY, "");
        BaseApp.getSpUtils().put(URLs.USERINFO_KEY, "");
    }

    public static void getUserInfo(final Action1<UserInfoModel.UserInfo> userInfoAction1) {
        if (userInfoAction1 == null) {
            return;
        }
        String userinfo = BaseApp.getSpUtils().getString(URLs.USERINFO_KEY, "");
        if (!TextUtils.isEmpty(userinfo)) {
            UserInfoModel.UserInfo userInfo = new Gson().fromJson(userinfo, UserInfoModel.UserInfo.class);
            if (userInfo != null) {
                userInfoAction1.call(userInfo);
            }
        } else {
            if (TextUtils.isEmpty(URLs.ACCESS_TOKEN) && TextUtils.isEmpty(URLs.UID)) {
                userInfoAction1.call(null);
                return;
            }
            HttpRequest.getUserService().userinfo()
                    .compose(NetWorkInterceptor.<UserInfoModel>retrySessionCreator())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnNext(new HttpResult<UserInfoModel>() {
                        @Override
                        public void result(UserInfoModel userInfoModel) {
                            setUserInfo(userInfoModel.info);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpResult<UserInfoModel>() {
                        @Override
                        public void result(UserInfoModel userInfoModel) {
                            userInfoAction1.call(userInfoModel.info);
                        }
                    }, new HttpError() {
                        @Override
                        public void error(int errorCode, String message) {
                            userInfoAction1.call(null);
                        }
                    }, new HttpComplete());
        }
    }

    public static void setUserInfo(UserInfoModel.UserInfo userInfo) {
        BaseApp.getSpUtils().put(URLs.USERINFO_KEY, new Gson().toJson(userInfo));
    }

    /**
     * 保存数据
     */
    public void saveUserInfo() {
        if (!TextUtils.isEmpty(access_token)) {
            URLs.ACCESS_TOKEN = access_token;
            BaseApp.getSpUtils().put(URLs.ACCESS_TOKEN_KEY, access_token);
        }
        if (!TextUtils.isEmpty(uid)) {
            URLs.UID = uid;
            BaseApp.getSpUtils().put(URLs.UID_KEY, uid);
        }
        getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {

            }
        });
    }

    /**
     * 保存数据
     */
    public void saveUserInfo(String username, String password) {
        if (!TextUtils.isEmpty(username)) {
            BaseApp.getSpUtils().put(URLs.USERNAME_KEY, username);
        }
        if (!TextUtils.isEmpty(password)) {
            URLs.UID = uid;
            BaseApp.getSpUtils().put(URLs.PASSWORD_KEY, password);
        }
        saveUserInfo();
    }


}

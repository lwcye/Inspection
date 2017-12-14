package com.cmcc.lib_network.model;

import android.text.TextUtils;

import com.cmcc.lib_common.base.BaseApp;
import com.cmcc.lib_network.constans.URLs;

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

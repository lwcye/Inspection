package com.cmcc.inspection.base;

import com.cmcc.inspection.BuildConfig;
import com.cmcc.lib_common.base.BaseApp;
import com.cmcc.lib_network.model.UserInfoModel;
import com.cmcc.lib_utils.utils.LogUtils;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/11/9
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MyApplication extends BaseApp {
    public static UserInfoModel sUserInfoModel;

    public static UserInfoModel getUserInfoModel() {
        return sUserInfoModel;
    }

    public static void setUserInfoModel(UserInfoModel userInfoModel) {
        sUserInfoModel = userInfoModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
    }

    /**
     * Log的初始化
     */
    private void initLog() {
        LogUtils.init(BuildConfig.LOG_DEBUG, false, 'v', "lwc");

    }
}

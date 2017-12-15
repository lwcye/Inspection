package com.cmcc.lib_common.base;

import android.app.Application;

import com.cmcc.lib_common.constans.CommonSharePresf;
import com.cmcc.lib_utils.utils.SPUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.cmcc.lib_utils.utils.Utils;


/**
 * <p>Application</p><br>
 *
 * @author lwc
 * @date 2017/3/25 18:03
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BaseApp extends Application {
    private static BaseApp sBaseApp;
    /** 系统偏好设置 */
    private static SPUtils spUtils;

    /** 单列 */
    public static BaseApp getInstance() {
        return sBaseApp;
    }
    
    /**
     * 获得SharedPreference进行操作
     *
     * @return SPUtils
     */
    public static SPUtils getSpUtils() {
        if (null == spUtils) {
            spUtils = new SPUtils(CommonSharePresf.FILE_NAME);
        }
        return spUtils;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApp = this;
        init();
    }
    
    private void init() {
        //工具初始化
        Utils.init(this);
        ToastUtils.init(true);
    }
}

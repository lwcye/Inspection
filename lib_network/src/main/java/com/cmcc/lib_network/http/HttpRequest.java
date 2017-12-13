package com.cmcc.lib_network.http;


import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.service.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/4/7 20:01
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class HttpRequest {
    /** Session服务 */
    private static UserService sUserService;
    
    private static synchronized <T> T create(final Class<T> service) {
        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new NetWorkInterceptor());
        return RetrofitWrapper.createInstance(URLs.HTTP_URL, RetrofitWrapper.CONVERTER_GSON,
            interceptorList, false).create(service);
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static synchronized UserService getUserService() {
        if (null == sUserService) {
            sUserService = create(UserService.class);
        }
        return sUserService;
    }
    
    
}

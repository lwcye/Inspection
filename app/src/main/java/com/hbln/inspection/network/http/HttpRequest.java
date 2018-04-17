package com.hbln.inspection.network.http;


import com.hbln.inspection.base.BaseApp;
import com.hbln.inspection.constans.URLs;
import com.hbln.inspection.network.model.LoginModel;
import com.hbln.inspection.network.service.FortressService;
import com.hbln.inspection.network.service.JiaFangService;
import com.hbln.inspection.network.service.KaoShiService;
import com.hbln.inspection.network.service.ModelService;
import com.hbln.inspection.network.service.PingPaiService;
import com.hbln.inspection.network.service.RegularService;
import com.hbln.inspection.network.service.ShcoolService;
import com.hbln.inspection.network.service.TrackService;
import com.hbln.inspection.network.service.UserService;
import com.hbln.inspection.network.service.WorkService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import rx.Observable;
import rx.schedulers.Schedulers;

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
    private static WorkService sWorkService;
    private static JiaFangService sJiaFangService;
    private static ShcoolService sShcoolService;
    private static RegularService sRegularService;
    private static ModelService sModelService;
    private static PingPaiService sPingPaiService;
    private static TrackService sTrackService;
    private static FortressService sFortressService;
    private static KaoShiService sKaoShiService;
    
    
    private static <T> T create(final Class<T> service) {
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
    public static UserService getUserService() {
        if (null == sUserService) {
            sUserService = create(UserService.class);
        }
        return sUserService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static WorkService getWorkService() {
        if (null == sWorkService) {
            sWorkService = create(WorkService.class);
        }
        return sWorkService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static JiaFangService getJiaFangServicee() {
        if (null == sJiaFangService) {
            sJiaFangService = create(JiaFangService.class);
        }
        return sJiaFangService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static ShcoolService getShcoolService() {
        if (null == sShcoolService) {
            sShcoolService = create(ShcoolService.class);
        }
        return sShcoolService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static RegularService getRegularService() {
        if (null == sRegularService) {
            sRegularService = create(RegularService.class);
        }
        return sRegularService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static ModelService getModelService() {
        if (null == sModelService) {
            sModelService = create(ModelService.class);
        }
        return sModelService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static PingPaiService getBrandService() {
        if (null == sPingPaiService) {
            sPingPaiService = create(PingPaiService.class);
        }
        return sPingPaiService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static TrackService getTrackService() {
        if (null == sTrackService) {
            sTrackService = create(TrackService.class);
        }
        return sTrackService;
    }
    
    /**
     * 获取user服务
     *
     * @return 服务对象
     */
    public static FortressService getFortressService() {
        if (null == sFortressService) {
            sFortressService = create(FortressService.class);
        }
        return sFortressService;
    }
    
    public static KaoShiService getKaoShiService() {
        if (null == sKaoShiService) {
            sKaoShiService = create(KaoShiService.class);
        }
        return sKaoShiService;
    }
    
    /**
     * 登陆的接口调用
     *
     * @return
     */
    public static Observable<LoginModel> login() {
        return HttpRequest.getUserService().login(BaseApp.getSpUtils().getString(URLs.USERNAME_KEY),
            BaseApp.getSpUtils().getString(URLs.PASSWORD_KEY),
            URLs.HTTP_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext(new HttpResult<LoginModel>() {
                @Override
                public void result(LoginModel loginModel) {
                    loginModel.saveUserInfo();
                }
            })
            .observeOn(Schedulers.io());
    }
}

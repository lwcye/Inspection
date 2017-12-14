package com.cmcc.lib_network.http;

import android.text.TextUtils;

import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/4/7 20:04
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class NetWorkInterceptor implements Interceptor {
    /** 重新创建会话的次数 */
    private static final int RETRY_MAX_TIMES = 1;
    /** 字符编码 */
    private final String CHARSET_NAME = "utf-8";
    /** 传输类型 */
    private final String DEC_MEDIA_TYPE = "application/x-www-form-urlencoded";

    /**
     * 处理 cookie 过期时出现SessionTimeoutException
     * 1. 调用Session创建
     * 2. retry 重新调用接口
     *
     * @param <R> 类型
     * @return 转换后的Observable
     */
    public static <R> Observable.Transformer<R, R> retrySessionCreator() {

        return new Observable.Transformer<R, R>() {
            /** 会话创建重试次数 */
            private int mRetryCount = 0;

            @Override
            public Observable<R> call(Observable<R> observable) {
                return observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable
                                .observeOn(Schedulers.io())
                                .flatMap(new Func1<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Throwable throwable) {
                                        if (throwable instanceof TokenException) {
                                            if (mRetryCount++ < RETRY_MAX_TIMES) {
                                                return HttpRequest.login();
                                            }
                                        }
                                        return Observable.error(throwable);
                                    }
                                });
                    }
                });
            }
        };
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 处理请求
        Request newRequest = handleRequest(chain.request());
        // 发送数据
        Response response = chain.proceed(newRequest);

        return handleResponse(response);
    }

    private Response handleResponse(Response response) throws IOException {

        Request oldRequest = response.request();
        //打印请求
        StringBuilder stringBuilder = new StringBuilder("Request:");
        stringBuilder.append(oldRequest.url().toString()).append("?");
        RequestBody reqBody = oldRequest.body();
        if (reqBody instanceof FormBody) {
            // 将form body转换为json body
            FormBody formBody = (FormBody) reqBody;
            int size = formBody.size();
            for (int i = 0; i < size; ++i) {
                stringBuilder.append(formBody.name(i)).append("=").append(formBody.value(i)).append("&");
            }
        }

        ResponseBody body = response.body();
        //打印 response
        String result = body.string();
        stringBuilder.append("\n").append("Result:").append(result);
        LogUtils.e(stringBuilder.toString());

        Gson gson = new Gson();
        ObjectModel resultModel = gson.fromJson(result, ObjectModel.class);
        if (null == resultModel || !(resultModel.status == HttpResult.CODE_SUCCESS)) {
            if (resultModel != null) {
                if (resultModel.status == HttpResult.CODE_TOKEN) {
                    throw new TokenException();
                } else {
                    throw new StatusException(resultModel.status, resultModel.info.toString());
                }
            } else {
//                throw new StatusException(HttpResult.CODE_NULL, "没有查询到数据");
                throw new TokenException();
            }
        }

        //创建新的响应
        Response.Builder builder = response.newBuilder();
        builder.headers(response.headers())
                .body(ResponseBody.create(MediaType.parse(DEC_MEDIA_TYPE), result));
        return builder.build();
    }

    /**
     * 处理请求
     *
     * @param oldRequest 旧的请求
     * @return
     * @throws UnsupportedEncodingException
     */
    private Request handleRequest(Request oldRequest) throws UnsupportedEncodingException {
        // 创建请求
        HttpUrl.Builder httpBuild = oldRequest.url().newBuilder();
        if (!oldRequest.url().toString().contains("login")){
            //添加参数
            if (!TextUtils.isEmpty(URLs.UID)) {
                httpBuild.addQueryParameter("uid", URLs.UID);
            }
            if (!TextUtils.isEmpty(URLs.ACCESS_TOKEN)) {
                httpBuild.addQueryParameter("access_token", URLs.ACCESS_TOKEN);
            }
        }
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.url(httpBuild.build());
        return requestBuilder.build();
    }
}

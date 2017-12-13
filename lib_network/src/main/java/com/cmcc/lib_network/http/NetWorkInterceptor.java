package com.cmcc.lib_network.http;

import com.cmcc.lib_common.utils.LogUtils;
import com.cmcc.lib_network.model.ErrorModel;
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
    /** 字符编码 */
    private final String CHARSET_NAME = "utf-8";
    /** 传输类型 */
    private final String DEC_MEDIA_TYPE = "application/x-www-form-urlencoded";
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 处理请求
//        Request newRequest = handleRequest(chain.request());
        // 发送数据
        Response response = chain.proceed(chain.request());
        
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
        ErrorModel resultModel = gson.fromJson(result, ErrorModel.class);
        if (null == resultModel || !(resultModel.status == HttpResult.CODE_SUCCESS)) {
            if (resultModel != null) {
                throw new StatusException(resultModel.status, resultModel.info.toString());
            } else {
                throw new StatusException(HttpResult.CODE_NULL, "没有查询到数据");
            }
        }
        
        //创建新的响应
        Response.Builder builder = response.newBuilder();
        builder.headers(response.headers())
            .body(ResponseBody.create(MediaType.parse(DEC_MEDIA_TYPE), result));
        return builder.build();
    }
    
    private Request handleRequest(Request oldRequest) throws UnsupportedEncodingException {
        
        // 创建请求
        HttpUrl.Builder httpBuild = oldRequest.url().newBuilder();
        //添加参数
//        httpBuild.addQueryParameter("auth", "canal0406");
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.url(httpBuild.build());
        return requestBuilder.build();
    }
}

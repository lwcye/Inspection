package com.cmcc.lib_network.http;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit包装类
 *
 * @author mos
 * @date 2017.02.03
 * @note 1. 若启用ssl，默认会扫描assets/certs目录下的所有证书
 * 2. 若加载证书，即使是CA证书的网站，也必须放入证书文件
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class RetrofitWrapper {
    /** 数据转换器(GSON) */
    public static final String CONVERTER_GSON = "gson";
    /** 数据转换器(String) */
    public static final String CONVERTER_STRING = "string";

    /** 默认连接超时(毫秒) */
    private static final long DEF_CONNECT_TIMEOUT = 8 * 1000;
    /** 默认读取超时(毫秒) */
    private static final long DEF_READ_TIMEOUT = 8 * 1000;
    /** 证书文件目录 */
    private static final String CERT_DIR = "certs";

    /** 连接超时时间 */
    private static long mConnectTimeout = DEF_CONNECT_TIMEOUT;
    /** 读取超时时间 */
    private static long mReadTimeout = DEF_READ_TIMEOUT;
    /** SSL工厂 */
    private static SSLSocketFactory mSSLSocketFactory;
    /** TrustManager */
    private static X509TrustManager mTrustManager;


    /**
     * 私有化构造函数
     */
    private RetrofitWrapper() {
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        sslSetup(context);
    }

    /**
     * 配置SSL(添加https证书)
     *
     * @param context 上下文
     */
    private static void sslSetup(Context context) {
        try {
            // 列出目录中的所有证书
            String[] certFiles = context.getAssets().list(CERT_DIR);
            if (certFiles != null) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

                // 初始化KeyStore
                keyStore.load(null);

                // 加入证书
                for (String cert : certFiles) {
                    InputStream is = context.getAssets().open(CERT_DIR + "/" + cert);

                    keyStore.setCertificateEntry(cert, certificateFactory.generateCertificate(is));
                    is.close();
                }

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {

                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                mTrustManager = (X509TrustManager) trustManagers[0];
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{mTrustManager}, null);
                mSSLSocketFactory = sslContext.getSocketFactory();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读超时时间(毫秒)
     */
    public static void init(Context context, long connectTimeout, long readTimeout) {
        mConnectTimeout = connectTimeout;
        mReadTimeout = readTimeout;
        sslSetup(context);
    }

    /**
     * 创建默认的Builder
     *
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @param sslEnable 是否加载ssl证书
     * @return builder对象
     */
    private static OkHttpClient.Builder createOkHttpBuilder(long connectTimeout, long readTimeout, boolean sslEnable) {
        // 参数修正
        if (connectTimeout <= 0) {
            connectTimeout = mConnectTimeout;
        }
        if (readTimeout <= 0) {
            readTimeout = mReadTimeout;
        }

        // 打印日志
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // OkHttp初始化
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {

                        return true;
                    }
                });

        // 是否使用SSL支持
        if (mSSLSocketFactory != null && mTrustManager != null && sslEnable) {
            okBuilder.sslSocketFactory(mSSLSocketFactory, mTrustManager);
        }

        return okBuilder;
    }

    /**
     * 获取Retrofit实例
     *
     * @param baseUrl 服务器的URL
     * @param converter 数据转换类型(参见 RetrofitWrapper.CONVERTER_GSON 等)
     * @param interceptors 拦截器列表
     * @param connectTimeout 连接超时
     * @param readTimeout 读超时
     * @param sslEnable 是否加载SSL证书
     * @return Retrofit实例
     */
    public static Retrofit createInstance(String baseUrl, String converter, List<Interceptor> interceptors, long connectTimeout, long readTimeout, boolean sslEnable) {
        // 添加拦截器
        OkHttpClient.Builder builder = createOkHttpBuilder(connectTimeout, readTimeout, sslEnable);
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        // 支持RxJava
        OkHttpClient httpClient = builder.build();
        Retrofit.Builder rtBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient);

        if (converter != null) {
            if (converter.equals(CONVERTER_GSON)) {
                // GSON转换器
                rtBuilder.addConverterFactory(GsonConverterFactory.create(new Gson()));

            } else if (converter.equals(CONVERTER_STRING)) {
                // String转换器
                rtBuilder.addConverterFactory(new StringConverterFactory());

            }
        }

        return rtBuilder.build();
    }

    /**
     * 获取Retrofit实例
     *
     * @param baseUrl 服务器的URL
     * @param converter 数据转换类型(参见 RetrofitWrapper.CONVERTER_GSON 等)
     * @param interceptors 拦截器列表
     * @param sslEnable 是否加载SSL证书
     * @return Retrofit实例
     */
    public static Retrofit createInstance(String baseUrl, String converter, List<Interceptor> interceptors, boolean sslEnable) {

        return createInstance(baseUrl, converter, interceptors, mConnectTimeout, mReadTimeout, sslEnable);
    }

    /**
     * 获取新的实例
     *
     * @param baseUrl 服务器的URL
     * @return Retrofit实例
     * @note 1. 此函数的超时参数，与init函数配置的一致
     * 2. 默认为gson数据转换器
     */
    public static Retrofit createInstance(String baseUrl) {

        return createInstance(baseUrl, CONVERTER_GSON, null, false);
    }

    /**
     * 获取新的实例
     *
     * @param baseUrl 服务器的URL
     * @param sslEnable 是否加载SSL证书
     * @return Retrofit实例
     * @note 1. 此函数的超时参数，与init函数配置的一致
     * 2. 默认为gson数据转换器
     */
    public static Retrofit createInstance(String baseUrl, boolean sslEnable) {

        return createInstance(baseUrl, CONVERTER_GSON, null, sslEnable);
    }

    /**
     * 获取新的实例
     *
     * @param baseUrl 服务器的URL
     * @param converter 数据转换类型(参见 RetrofitWrapper.CONVERTER_GSON 等)
     * @return Retrofit实例
     * @note 1. 此函数的超时参数，与init函数配置的一致
     */
    public static Retrofit createInstance(String baseUrl, String converter) {

        return createInstance(baseUrl, converter, null, false);
    }

    /**
     * String转换器
     */
    private static class StringConverterFactory extends Converter.Factory {
        private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (String.class.equals(type)) {

                return new Converter<ResponseBody, String>() {
                    @Override
                    public String convert(ResponseBody value) throws IOException {
                        return value.string();
                    }
                };
            }
            return null;
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

            if (String.class.equals(type)) {
                return new Converter<String, RequestBody>() {
                    @Override
                    public RequestBody convert(String value) throws IOException {
                        return RequestBody.create(MEDIA_TYPE, value);
                    }
                };
            }
            return null;
        }
    }
}

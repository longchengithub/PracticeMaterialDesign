package com.example.chenlong.practicematerialdesign.http;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.chenlong.practicematerialdesign.http.NetService.BannerServices;
import com.example.chenlong.practicematerialdesign.http.NetService.HomePageService;
import com.example.chenlong.practicematerialdesign.utils.CommonUtil;
import com.example.chenlong.practicematerialdesign.view.BiliBili;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenlong on 2016/12/10.
 */

public class RetrofitHelper
{

    private static OkHttpClient mOkHttpClient;

    /**
     * 获取轮播图的URL
     *
     * @return
     */
    public static BannerServices getBannerService()
    {
        return createApi(BannerServices.class, ApiConstants.APP_BASE_URL);
    }

    public static HomePageService getHomePageService()
    {
        return createApi(HomePageService.class, ApiConstants.APP_BASE_URL);
    }

    static
    {
        initOkHttpClient();
    }

    /**
     * 初始化OkHttpClient
     */
    private static void initOkHttpClient()
    {
        if (mOkHttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkHttpClient == null)
                {

                 /*   //设置Http缓存
                    Cache cache = new Cache(new File(BilibiliApp.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);*/
                    mOkHttpClient = new OkHttpClient.Builder()
//                            .addNetworkInterceptor(new CacheInterceptor())
//                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();

                }
            }
        }
    }

    /**
     * Retrofit包装okHttpClient
     *
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit.create(clazz);
    }

    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }


    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor
    {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public Response intercept(Chain chain) throws IOException
        {

            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(BiliBili.getInstance()))
            {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else
            {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(BiliBili.getInstance()))
            {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else
            {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

}

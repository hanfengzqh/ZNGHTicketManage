package com.zng.ticket_manage.commonlibrary.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.zng.ticket_manage.commonlibrary.http.GlobalHttpHandler;
import com.zng.ticket_manage.commonlibrary.http.RequestInterceptor;
import com.zng.ticket_manage.commonlibrary.utils.DataHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zqh on 2018/1/11.
 */
@Module
public class ClientModule {
    private static final int TIME_OUT = 10;

    /**
     * 提供 Retrofit
     *
     * @param builder
     * @param client
     * @param httpUrl
     * @return
     */
    @Singleton
    @Provides
    Retrofit providerRetrofit(Application application, @Nullable RetrofitConfiguration configuration,
                              Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl, Gson gson) {

        builder.baseUrl(httpUrl)//配置域名
                .client(client);//设置okhttp
        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava适配器
                .addConverterFactory(GsonConverterFactory.create());//使用Gson转换器
        return builder.build();
    }


    /**
     * 提供OkHttpClient
     *
     * @param builder
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient providerClient(Application application, @Nullable OkhttpConfiguration configuration,
                                OkHttpClient.Builder builder, Interceptor intercept
            , @Nullable List<Interceptor> interceptors, @Nullable final GlobalHttpHandler handler) {

        //设置超时
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        //添加拦截信息
        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });

            if (interceptors != null) {//如果外部提供了interceptor的集合则遍历添加
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
        }

        if (configuration != null) {
            configuration.configOkhttp(application, builder);
        }

        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    Interceptor provideInterceptor(RequestInterceptor intercept) {
        return intercept;//打印请求信息的拦截器
    }

    /**
     * 提供 {@link RxCache}
     *
     * @param cacheDirectory RxCache缓存路径
     * @return
     */
    @Singleton
    @Provides
    RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration, @Named("RxCacheDirectory") File cacheDirectory) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) {
            return rxCache;
        }
        return builder.persistence(cacheDirectory, new GsonSpeaker());
    }

    /**
     * 需要单独给 {@link RxCache} 提供缓存路径
     *
     * @param cacheDir
     * @return
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return DataHelper.makeDirs(cacheDirectory);
    }

    /**
     * 提供处理 RxJava 错误的管理器
     *
     * @return
     */
    @Singleton
    @Provides
    RxErrorHandler proRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface RxCacheConfiguration {
        RxCache configRxCache(Context context, RxCache.Builder builder);
    }
}

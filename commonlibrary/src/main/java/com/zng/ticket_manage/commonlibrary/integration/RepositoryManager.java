package com.zng.ticket_manage.commonlibrary.integration;

import android.app.Application;
import android.content.Context;

import com.zng.ticket_manage.commonlibrary.integration.cache.Cache;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Created by zqh on 2018/1/12.
 */

@Singleton
public class RepositoryManager implements IRepositoryManager {

    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private Application mApplication;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;
    private Cache.Factory mCachefactory;

    @Inject
    public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache, Application application
            , Cache.Factory cachefactory) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
        this.mApplication = application;
        this.mCachefactory = cachefactory;
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        return null;
    }

    @Override
    public <T> T obtainCacheService(Class<T> cahce) {
        return null;
    }

    @Override
    public void clearAllCache() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}

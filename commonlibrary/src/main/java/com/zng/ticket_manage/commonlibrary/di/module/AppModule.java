package com.zng.ticket_manage.commonlibrary.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zng.ticket_manage.commonlibrary.integration.IRepositoryManager;
import com.zng.ticket_manage.commonlibrary.integration.cache.Cache;
import com.zng.ticket_manage.commonlibrary.integration.cache.CacheType;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zqh on 2018/1/11.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public Gson providerGson(Application application, GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(IRepositoryManager repositoryManager) {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Cache<String, Object> providerExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }
}

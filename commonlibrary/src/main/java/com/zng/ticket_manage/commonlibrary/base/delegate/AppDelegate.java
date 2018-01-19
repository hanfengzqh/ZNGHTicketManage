package com.zng.ticket_manage.commonlibrary.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.zng.ticket_manage.commonlibrary.base.App;
import com.zng.ticket_manage.commonlibrary.di.component.AppComponent;
import com.zng.ticket_manage.commonlibrary.di.component.DaggerAppComponent;
import com.zng.ticket_manage.commonlibrary.di.module.AppModule;
import com.zng.ticket_manage.commonlibrary.di.module.ClientModule;
import com.zng.ticket_manage.commonlibrary.di.module.GlobalConfigModule;
import com.zng.ticket_manage.commonlibrary.integration.ActivityLifecycle;
import com.zng.ticket_manage.commonlibrary.integration.lifecycle.ActivityLifecycleForRxLifecycle;
import com.zng.ticket_manage.commonlibrary.integration.ConfigModule;
import com.zng.ticket_manage.commonlibrary.integration.ManifestParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zqh on 2018/1/11.
 */

public class AppDelegate implements App, AppLifecycles {

    private List<ConfigModule> mModules;
    private Application mApplication;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    @Inject
    protected ActivityLifecycleForRxLifecycle mActivityLifecycleForRxLifecycle;

    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    private AppComponent mAppComponent;
    private ComponentCallbacks2 mComponentCallback;

    public AppDelegate(Context context) {
        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        this.mModules = new ManifestParser(context).parse();

        for (ConfigModule module : mModules) {
            //将个人实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(context, mAppLifecycles);
            //将个人实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(Context base) {
        //遍历 mAppLifecycles, 回调所有已注册的 AppLifecycles 的 attachBaseContext() 方法
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(mApplication))//用于提供application
                .clientModule(new ClientModule())//用于提供okhttp和retrofit的单例
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);
        //将 ConfigModule 的实现类的集合存放到缓存 Cache, 可以随时获取
        //大于或等于缓存所能允许的最大 size, 则会根据 LRU 算法清除之前的条目
        mAppComponent.extras().put(ConfigModule.class.getName(), mModules);
        this.mModules = null;

        //该注册是为了给每个 Activity 增加引用框架中统一的全局逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        //该注册是为了RxLifecycle能够在每一个 Activity或Fragment的生命周期中发送对应的Event事件
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);

        //遍历 mActivityLifecycles, 注册所有 Activity 的生命周期回调, 每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
        //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        mComponentCallback = new AppComponentCallbacks(mApplication, mAppComponent);

        mApplication.registerComponentCallbacks(mComponentCallback);

        //遍历 mAppLifecycles, 回调所有已注册的 AppLifecycles 的 onCreate() 方法
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }

    }


    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycleForRxLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
        }
        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycleForRxLifecycle = null;
        this.mActivityLifecycles = null;
        this.mComponentCallback = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }

    /**
     * 将AppComponent返回出去,供其它地方使用,
     * AppComponent接口中声明的方法返回的实例,
     * 在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;
        private AppComponent mAppComponent;

        public AppComponentCallbacks(Application application, AppComponent appComponent) {
            this.mApplication = application;
            this.mAppComponent = appComponent;
        }

        @Override
        public void onTrimMemory(int i) {

        }

        @Override
        public void onConfigurationChanged(Configuration configuration) {

        }

        @Override
        public void onLowMemory() {
            //内存不足时清理不必要的资源
        }
    }

    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules) {

        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }
}

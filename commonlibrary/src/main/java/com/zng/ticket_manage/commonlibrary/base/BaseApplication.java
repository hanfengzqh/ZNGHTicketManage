package com.zng.ticket_manage.commonlibrary.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zng.ticket_manage.commonlibrary.base.delegate.AppDelegate;
import com.zng.ticket_manage.commonlibrary.base.delegate.AppLifecycles;
import com.zng.ticket_manage.commonlibrary.di.component.AppComponent;

/**
 * Created by zqh on 2018/1/18.
 */

public class BaseApplication extends Application implements App{

    private AppLifecycles mAppDelegate;
    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null){
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null){
            this.mAppDelegate.onCreate(this);
        }
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }
    /**
     * 将 {@link AppComponent} 返回出去,供其它地方使用,{@link AppComponent} 中声明的方法所返回的实例
     * 在 {@link #getAppComponent()}拿到对象后都可以直接使用
     *
     * @return
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return ((App) mAppDelegate).getAppComponent();
    }
}

package com.zng.tinkerdemo.app;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zng.tinkerdemo.BuildConfig;

/**
 * Created by zqh on 2018/2/13.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(5)
                .tag("zqh")
                .build();
        if (BuildConfig.LOG_DEBUG) {
            //Debug，打印日志
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        } else {
            //release,关闭日志
            Logger.clearLogAdapters();
        }
    }
}

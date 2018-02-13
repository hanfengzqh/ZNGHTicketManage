package com.zng.ticket_manage.myapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zng.ticket_manage.andfix.AndFixPatchManager;

/**
 * Created by zqh on 2018/2/10.
 */

public class ImoocApplication extends Application {
    // dex突破65535的限制
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initAndFix();

    }

    //完成AndFix的初始化
    private void initAndFix() {
        AndFixPatchManager.getInstance().initPatch(this);
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

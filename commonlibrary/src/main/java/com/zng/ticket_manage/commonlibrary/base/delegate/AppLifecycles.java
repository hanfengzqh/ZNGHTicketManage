package com.zng.ticket_manage.commonlibrary.base.delegate;

import android.app.Application;
import android.content.Context;

/**
 * 用于代理 {@link Application} 的生命周期
 * Created by zqh on 2018/1/11.
 */

public interface AppLifecycles {
    void attachBaseContext(Context base);

    void onCreate(Application application);

    void onTerminate(Application application);
}

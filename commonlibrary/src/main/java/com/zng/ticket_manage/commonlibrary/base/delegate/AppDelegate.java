package com.zng.ticket_manage.commonlibrary.base.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zng.ticket_manage.commonlibrary.base.App;
import com.zng.ticket_manage.commonlibrary.di.component.AppComponent;

/**
 * Created by zqh on 2018/1/11.
 */

public class AppDelegate implements App,AppLifecycles{
    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {

    }

    @Override
    public void onTerminate(Application application) {

    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return null;
    }
}

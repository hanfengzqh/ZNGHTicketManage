package com.zng.ticket_manage.commonlibrary.base;

import android.support.annotation.NonNull;

import com.zng.ticket_manage.commonlibrary.di.component.AppComponent;

/**
 * Created by zqh on 2018/1/11.
 * 框架要求框架中的每个 {@link android.app.Application} 都需要实现此类,以满足规范
 */

public interface App {
    @NonNull
    AppComponent getAppComponent();
}

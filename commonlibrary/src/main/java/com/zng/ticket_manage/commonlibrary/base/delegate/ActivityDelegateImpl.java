package com.zng.ticket_manage.commonlibrary.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.zng.ticket_manage.commonlibrary.utils.ArmsUtils;

import org.simple.eventbus.EventBus;

/**
 * Created by zqh on 2018/1/17.
 */

public class ActivityDelegateImpl implements ActivityDelegate {

    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            org.simple.eventbus.EventBus.getDefault().register(mActivity);//注册到事件主线
        iActivity.setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(mActivity));//依赖注入
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (iActivity != null && iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }
}

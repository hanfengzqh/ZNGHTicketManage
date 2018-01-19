package com.zng.ticket_manage.commonlibrary.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.zng.ticket_manage.commonlibrary.integration.IRepositoryManager;

/**
 * Created by zqh on 2018/1/5.
 */

public class BaseModel implements IModel, LifecycleObserver {

    protected IRepositoryManager mRepositoryManager;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory(LifecycleOwner owner){
        owner.getLifecycle().removeObserver(this);
    }
}

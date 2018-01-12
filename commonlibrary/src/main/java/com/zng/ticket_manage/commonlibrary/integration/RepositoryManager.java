package com.zng.ticket_manage.commonlibrary.integration;

import android.content.Context;

/**
 * Created by zqh on 2018/1/12.
 */

public class RepositoryManager implements IRepositoryManager {
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        return null;
    }

    @Override
    public <T> T obtainCacheService(Class<T> cahce) {
        return null;
    }

    @Override
    public void clearAllCache() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}

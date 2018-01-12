package com.zng.ticket_manage.commonlibrary.integration;

import android.content.Context;

import com.zng.ticket_manage.commonlibrary.mvp.IModel;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link IModel} 必要的 Api 做数据处理
 * Created by zqh on 2018/1/11.
 */

public interface IRepositoryManager {

    /**
     * 根据传入的Class 获取对应的Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的Class 获取对应的 RxCache service
     *
     * @param cahce
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cahce);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    Context getContext();
}

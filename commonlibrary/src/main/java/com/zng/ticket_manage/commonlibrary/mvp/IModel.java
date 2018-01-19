package com.zng.ticket_manage.commonlibrary.mvp;

/**
 * 每个 Model 都需要实现此类,以满足规范
 * Created by zqh on 2018/1/5.
 */

public interface IModel {
    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}

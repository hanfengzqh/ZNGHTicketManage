package com.zng.ticket_manage.commonlibrary.mvp;

import android.app.Activity;

/**
 * Created by zqh on 2018/1/5.
 * 框架要求框架中的每个 Presenter 都需要实现此类,以满足规范
 *
 * @see BasePresenter
 */

public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();
    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();

}

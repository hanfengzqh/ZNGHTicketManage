package com.zng.ticket_manage.commonlibrary.mvp;

import android.content.Intent;

/**
 * Created by zqh on 2018/1/5.
 */

public interface IView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     *
     * @param message
     */
    void showMessage(String message);

    /**
     * 跳转
     *
     * @param intent
     */
    void launchActivity(Intent intent);

    /**
     * 杀死自己
     */
    void killMyself();


}

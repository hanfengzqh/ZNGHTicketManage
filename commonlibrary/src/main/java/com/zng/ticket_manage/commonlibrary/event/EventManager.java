package com.zng.ticket_manage.commonlibrary.event;

import de.greenrobot.event.EventBus;

/**
 * Created by zqh on 2017/11/29.
 */

public class EventManager {

    //注册事件
    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    //取消注册
    public static void unRegister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }

    //终止事件传播
    public static void cancleEvent(Object object) {
        EventBus.getDefault().cancelEventDelivery(object);
    }

    //清除粘性事件
    public static void removeAllEvent(Object object) {
        EventBus.getDefault().removeAllStickyEvents();
    }


}

package com.zng.ticket_manage.commonlibrary.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.zng.ticket_manage.commonlibrary.base.delegate.AppDelegate;
import com.zng.ticket_manage.commonlibrary.di.module.AppModule;
import com.zng.ticket_manage.commonlibrary.di.module.ClientModule;
import com.zng.ticket_manage.commonlibrary.di.module.GlobalConfigModule;
import com.zng.ticket_manage.commonlibrary.http.imageloader.ImageLoader;
import com.zng.ticket_manage.commonlibrary.integration.AppManager;
import com.zng.ticket_manage.commonlibrary.integration.IRepositoryManager;
import com.zng.ticket_manage.commonlibrary.integration.cache.Cache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * 可通过 { Dagger2Utils#obtainAppComponentFromContext(Context)} 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * Created by zqh on 2018/1/11.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {

    Application application();

    //用于管理所有 activity
    AppManager appManager();

    //用于管理网络请求层以及数据缓存层
    IRepositoryManager repositoryManager();

    //rxjava 错误处理管理类
    RxErrorHandler rxErrorHandler();

    //图片管理器,用于加载图片的管理类,默认使用 Glide ,使用策略模式,可在运行时替换框架
    ImageLoader imageLoader();

    OkHttpClient okHttpClient();

    //Gson
    Gson gson();

    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),
    // 应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    File cacheFile();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Cache<String, Object> extras();

    //用于创建框架所需缓存对象的工厂
    Cache.Factory cacheFactory();

    void inject(AppDelegate appDelegate);

}

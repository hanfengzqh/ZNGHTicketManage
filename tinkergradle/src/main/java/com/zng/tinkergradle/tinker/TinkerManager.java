package com.zng.tinkergradle.tinker;

import android.content.Context;

import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by zqh on 2018/2/13.
 *
 * @function 对Tinker的所有api做一次封装
 */

public class TinkerManager {

    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;
    private static CustomPatchListener patchListener;

    public static void installTinker(ApplicationLike applicationLike) {

        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }
        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());
        patchListener = new CustomPatchListener(getApplicationContext());
        AbstractPatch  abstractPatch  = new UpgradePatch();

//        TinkerInstaller.install(mAppLike);//完成tinker初始化

        TinkerInstaller.install(mAppLike,
                loadReporter,patchReporter,
                patchListener,CustomResultService.class,
                abstractPatch);//完成tinker初始化

        isInstalled = true;
    }

    //完成Patch文件的加载
    public static void loadPatch(String patch) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), patch);
        }
    }

    //通过ApplicationLike获取Context
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }

}

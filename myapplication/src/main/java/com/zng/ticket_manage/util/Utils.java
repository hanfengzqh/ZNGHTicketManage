package com.zng.ticket_manage.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.orhanobut.logger.Logger;

/**
 * Created by renzhiqiang on 17/4/22.
 */

public class Utils {

    /**
     * 获取应用程序versionname
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.d("versionName = "+versionName);
        return versionName;
    }


    public static void createBug(){

        String error = "error = ";
//        Logger.d(error+1/0);
        Logger.d("log正常打印.我还是去掉吧");
    }
}

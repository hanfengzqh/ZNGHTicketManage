package com.zng.ticket_manage.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.orhanobut.logger.Logger;
import com.zng.ticket_manage.util.Utils;

import java.io.IOException;

/**
 * Created by zqh on 2018/2/10.
 *
 * @Function 管理AndFix的所有Api
 */

public class AndFixPatchManager {

    private static AndFixPatchManager mInstance = null;
    private PatchManager patchManager = null;

    public static AndFixPatchManager getInstance() {
        if (mInstance == null) {
            synchronized (AndFixPatchManager.class) {
                if (mInstance == null) {
                    mInstance = new AndFixPatchManager();
                }
            }
        }
        return mInstance;
    }

    //初始化AndFix
    public void initPatch(Context context) {
        patchManager = new PatchManager(context);
        patchManager.init(Utils.getVersionName(context));
        patchManager.loadPatch();
    }

    //加载我们的patch文件
    public void addPatch(String path) {

        if (patchManager != null) {
            try {
                patchManager.addPatch(path);
                Logger.d("加载完成");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

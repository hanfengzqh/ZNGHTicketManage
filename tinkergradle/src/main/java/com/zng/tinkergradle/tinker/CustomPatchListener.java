package com.zng.tinkergradle.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.zng.tinkergradle.utils.Utils;

/**
 * Created by zqh on 2018/2/18.
 */

public class CustomPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String md5Value) {

        this.currentMD5 = md5Value;
    }

    public CustomPatchListener(Context context) {
        super(context);
    }

    @Override
    public int onPatchReceived(String path) {
        //patch文件ms5较验
        if (!Utils.isFileMD5Matched(path, currentMD5)) {

            return ShareConstants.ERROR_PATCH_DISABLE;
        }

        return super.onPatchReceived(path);
    }
}

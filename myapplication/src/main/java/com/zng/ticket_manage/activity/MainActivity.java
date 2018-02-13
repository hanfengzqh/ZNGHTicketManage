package com.zng.ticket_manage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.zng.ticket_manage.andfix.AndFixPatchManager;
import com.zng.ticket_manage.myapplication.R;
import com.zng.ticket_manage.util.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_END = ".apatch";
    private String mPatchDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchDir = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        //创建文件夹
        File file = new File(mPatchDir);
        if (file == null || !file.exists()) {
            file.mkdirs();
        }
    }

    //产生bug
    public void createBug(View view) {
        String error = "error = ";
//        Logger.d(error+1/0);
//        Logger.d("log正常打印");
        Logger.d("createBug执行我是版本1.2");
        Utils.createBug();
    }

    //修复bug
    public void fixBug(View view) {
        AndFixPatchManager.getInstance().addPatch(getPatchName());
    }

    //构造patch文件
    private String getPatchName() {
        return mPatchDir.concat("imooc").concat(FILE_END);
    }
}

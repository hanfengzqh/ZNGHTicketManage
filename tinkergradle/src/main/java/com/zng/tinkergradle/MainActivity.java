package com.zng.tinkergradle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zng.tinkergradle.tinker.TinkerManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private static final String FILE_END = ".apk";
    private String mPatchDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchDir = getExternalCacheDir().getAbsolutePath() + "/tpatch/";
        File file = new File(mPatchDir);
        if (file == null || !file.exists()) {
            file.mkdirs();
        }
        Toast.makeText(this,"Patch 加载成功+叠加包",Toast.LENGTH_SHORT).show();

        BuildConfig.AUTO_TYPE.equals("3");
    }

    public void loadPatch(View view) {
        TinkerManager.loadPatch(getPatchName());
    }

    private String getPatchName() {
        return mPatchDir.concat("patch_signed").concat(FILE_END);
    }

}

package com.zng.ticket_manage.znghticketmanage.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.znghticketmanage.R;

import butterknife.BindView;

/**
 * Created by zqh on 2017/11/30.
 */

public class BindActivity extends BaseActivity {

    @BindView(R.id.bt_back)
    Button bt_back;
    @BindView(R.id.ed_tax_person)
    EditText ed_tax_person;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_bind;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}

package com.zng.ticket_manage.znghticketmanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.znghticketmanage.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.bt_login)
    Button bt_login;//账户登陆
    @BindView(R.id.bt_dev_bind)
    Button dev_bind;//设备绑定
    @BindView(R.id.bt_dev_unbind)
    Button dev_unbind;//设备解绑
    @BindView(R.id.bt_activate_quire)
    Button activate_quire;//激活查询
    @BindView(R.id.bt_red_ticket_bill)
    Button bt_red_ticket_bill;//红票开票流程
    @BindView(R.id.bt_blue_ticket_bill)
    Button bt_blue_ticket_bill;//蓝票开票流程

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bt_login.setOnClickListener(this);
        dev_bind.setOnClickListener(this);
        dev_unbind.setOnClickListener(this);
        activate_quire.setOnClickListener(this);
        bt_red_ticket_bill.setOnClickListener(this);
        bt_blue_ticket_bill.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login://账户登录
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.bt_dev_bind://设备绑定
                startActivity(new Intent(this, BindActivity.class));
                break;
            case R.id.bt_dev_unbind://设备解绑
                startActivity(new Intent(this, UnBindActivity.class));
                break;
            case R.id.bt_activate_quire://激活查询
                startActivity(new Intent(this, ActivateQueryActivity.class));
                break;
            case R.id.bt_red_ticket_bill://红票开票

                break;
            case R.id.bt_blue_ticket_bill://蓝票开票
                break;
            default:
                break;
        }
    }
}

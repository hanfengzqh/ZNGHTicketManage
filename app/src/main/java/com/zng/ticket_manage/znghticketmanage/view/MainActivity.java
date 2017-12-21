package com.zng.ticket_manage.znghticketmanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bt_qrCode)
    Button bt_qrCode;
    @BindView(R.id.tv_order_title)
    TextView tv_order_title;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        toolbar.setTitle("票务管理调试");
        toolbar.setSubtitle("首页");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_login.setOnClickListener(this);
        dev_bind.setOnClickListener(this);
        dev_unbind.setOnClickListener(this);
        activate_quire.setOnClickListener(this);
        bt_red_ticket_bill.setOnClickListener(this);
        bt_blue_ticket_bill.setOnClickListener(this);
        bt_qrCode.setOnClickListener(this);
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
                startActivity(new Intent(this, RedTicketActivity.class));
                break;
            case R.id.bt_blue_ticket_bill://蓝票开票
                startActivity(new Intent(this, BlueTicketActivity.class));
                break;
            case R.id.bt_qrCode:
                startActivity(new Intent(this,QRActivity.class));
                break;
            default:
                break;
        }
    }
}

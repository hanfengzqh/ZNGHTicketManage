package com.zng.ticket_manage.znghticketmanage.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.znghticketmanage.R;

import butterknife.BindView;

/**
 * Created by zqh on 2017/12/18.
 */

public class RedTicketActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_title)
    TextView tv_order_title;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_make_invoice_manual;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar.setTitle("手动开票");
        toolbar.setSubtitle("红票开票");
//        toolbar.setLogo(R.mipmap.ic_launcher_round);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

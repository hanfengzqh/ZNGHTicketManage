package com.zng.ticket_manage.znghticketmanage.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.commonlibrary.dialogutil.ToastUtil;
import com.zng.ticket_manage.znghticketmanage.R;

import butterknife.BindView;

/**
 * Created by zqh on 2017/12/18.
 */

public class BlueTicketActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_title)
    TextView tv_order_title;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_person)
    TextView tv_person;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_expand)
    ImageView tv_expand;
    @BindView(R.id.ll_select_info)
    LinearLayout ll_select_info;
    @BindView(R.id.ll_email)
    LinearLayout ll_email;
    @BindView(R.id.ll_recognition_num)
    LinearLayout ll_recognition_num;


    private boolean isExpand = true;//默认关闭

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_make_invoice_manual;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar.setTitle("手动开票");
        toolbar.setSubtitle("蓝票开票");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setLisenter();
    }

    private void setLisenter() {
        tv_company.setOnClickListener(this);
        tv_person.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_expand.setOnClickListener(this);

        tv_expand.setVisibility(View.VISIBLE);
        ll_recognition_num.setVisibility(View.VISIBLE);
        ll_select_info.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_company:
                ll_select_info.setVisibility(View.VISIBLE);
                ll_email.setVisibility(View.VISIBLE);
                ll_recognition_num.setVisibility(View.VISIBLE);
                tv_expand.setVisibility(View.VISIBLE);

                tv_company.setBackgroundResource(R.drawable.shape_left_pressed);
                tv_company.setTextColor(getResources().getColor(R.color.textorange));

                tv_person.setBackgroundResource(R.drawable.shape_right_normal);
                tv_person.setTextColor(getResources().getColor(R.color.textgrey));

                break;
            case R.id.tv_person:
                ll_select_info.setVisibility(View.GONE);
                ll_email.setVisibility(View.VISIBLE);
                ll_recognition_num.setVisibility(View.GONE);
                tv_expand.setVisibility(View.GONE);

                tv_person.setBackgroundResource(R.drawable.shape_right_pressed);
                tv_person.setTextColor(getResources().getColor(R.color.textorange));
                tv_company.setBackgroundResource(R.drawable.shape_left_normal);
                tv_company.setTextColor(getResources().getColor(R.color.textgrey));

                break;
            case R.id.tv_submit:
                ToastUtil.showShortToast(this,"提交");

                break;
            case R.id.tv_expand:
                isExpand = !isExpand;
                ll_recognition_num.setVisibility(View.VISIBLE);
                tv_expand.setVisibility(View.VISIBLE);

                if (isExpand){
                    tv_expand.setImageResource(R.mipmap.icon_pull_down);
                    ll_select_info.setVisibility(View.GONE);
                    ll_email.setVisibility(View.GONE);
                }else{
                    tv_expand.setImageResource(R.mipmap.icon_pull_up);
                    ll_select_info.setVisibility(View.VISIBLE);
                    ll_email.setVisibility(View.VISIBLE);
                }

                break;
        }
    }
}

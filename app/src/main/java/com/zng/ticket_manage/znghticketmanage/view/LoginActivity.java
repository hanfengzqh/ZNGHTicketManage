package com.zng.ticket_manage.znghticketmanage.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.zng.common.POSFunctionUtils;
import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.commonlibrary.dialogutil.ToastUtil;
import com.zng.ticket_manage.commonlibrary.httputil.OkhttpManagerUtils;
import com.zng.ticket_manage.commonlibrary.jsonutil.JsonUtil;
import com.zng.ticket_manage.commonlibrary.utils.SharePreUtil;
import com.zng.ticket_manage.znghticketmanage.R;
import com.zng.ticket_manage.znghticketmanage.bean.ResultInfor;
import com.zng.ticket_manage.znghticketmanage.utils.CertifyDataUtil;
import com.zng.ticket_manage.znghticketmanage.utils.CommonUtil;
import com.zng.ticket_manage.znghticketmanage.utils.Contacts;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by zqh on 2017/12/4.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ed_account)
    EditText ed_account;
    @BindView(R.id.ed_password)
    EditText ed_password;
    @BindView(R.id.bt_login2)
    Button bt_login2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private Context mContext;
    private POSFunctionUtils mPOSFunctionUtils;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mContext = this;
        mPOSFunctionUtils = new POSFunctionUtils(this);
        bt_login2.setOnClickListener(this);
        toolbar.setTitle("登陆");
        toolbar.setSubtitle("登陆");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login2:
                Logger.d("ed_account = " + ed_account.getText().toString());
                Logger.d("ed_password = " + ed_password.getText().toString());
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        // 发行商id 00100017
        String vidCode = CommonUtil.getVid(mContext, mPOSFunctionUtils);
        // 发行商SN H111111122
        String psnCode = CommonUtil.getPsn(mContext, mPOSFunctionUtils);
        // 设备sn 122006000075
        String dsnCode = CommonUtil.getDsn();
        String certSign = CertifyDataUtil.getCertSign(mContext, mPOSFunctionUtils);
        byte[] transKey_byte = CertifyDataUtil.getTransKey();
        String ed_account_num = ed_account.getText().toString();
        String ed_password_num = ed_password.getText().toString();
        ed_account_num = CommonUtil.getAccountNum(ed_account_num);
        ed_password_num = CommonUtil.getAccountNum(ed_password_num);
        String clear_data = ed_account_num.concat(ed_password_num).concat(dsnCode).concat(vidCode);
        Logger.d("login clear_data = " + clear_data);
        byte[] encryData_byte = CertifyDataUtil.encryptData(transKey_byte, clear_data.getBytes());

        HashMap<String, String> params = new HashMap();

        params.put(Contacts.Key.DSN, dsnCode.trim());//sn
        params.put(Contacts.Key.VID, vidCode.trim());//vid
        params.put(Contacts.Key.SIGN, certSign.trim());//签名数据
        params.put(Contacts.Key.ENCRY, CommonUtil.byte2Hex(encryData_byte).trim());//加密数据
        params.put(Contacts.Key.MT, Contacts.Const.DEV_BIND_UNBIND + "");
        params.put(Contacts.Key.ST, CommonUtil.getSystemTime().trim());
        params.put(Contacts.Key.LANGUAGE, CommonUtil.getCurrentLauguage());
        String mapToJson = JsonUtil.parseMapToJson(params);
        Logger.json(mapToJson);

        OkhttpManagerUtils.postAsync(this, Contacts.ServerPath.LOGIN_PATH, params, new OkhttpManagerUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Logger.d(e);
            }

            @Override
            public void requestSuccess(String result) {
                Logger.d(result);
                if (!TextUtils.isEmpty(result)) {
                    ResultInfor infor = JsonUtil.parseJsonToBean(result, ResultInfor.class);
                    if (infor != null) {
                        boolean result1 = infor.isResult();
                        String msg = infor.getMsg();
                        if (result1) {
                            String msgEncry = infor.getEncry();
                            String sign = infor.getSign();
                            if (!TextUtils.isEmpty(msgEncry) && !TextUtils.isEmpty(sign)) {
                                String signVerify = CertifyDataUtil.serverSignVerify(sign, msgEncry, mPOSFunctionUtils);
                                Logger.d("signVerify = " + signVerify.trim());
                                SharePreUtil.putString(mContext, Contacts.Key.TOKEN, signVerify.trim());
                            }
                        } else {
                            ToastUtil.showShortToast(mContext, msg + "");
                        }
                    }
                }
            }
        });
    }
}

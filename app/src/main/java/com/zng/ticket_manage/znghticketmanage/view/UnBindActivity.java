package com.zng.ticket_manage.znghticketmanage.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
 * Created by zqh on 2017/11/30.
 */

public class UnBindActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ed_tax_person)
    EditText ed_tax_person;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Context mContext;
    private POSFunctionUtils mPOSFunctionUtils;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_bind;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mContext = this;
        mPOSFunctionUtils = new POSFunctionUtils(this);
        toolbar.setTitle("解绑");
        toolbar.setSubtitle("解绑");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bindDev();

    }

    private void bindDev() {
        // 发行商id 00100017
        String vidCode = CommonUtil.getVid(mContext, mPOSFunctionUtils);
        // 发行商SN H111111122
        String psnCode = CommonUtil.getPsn(mContext, mPOSFunctionUtils);
        // 设备sn 122006000075
        String dsnCode = CommonUtil.getDsn();
        String certSign = CertifyDataUtil.getCertSign(mContext, mPOSFunctionUtils);
        byte[] transKey_byte = CertifyDataUtil.getTransKey();

        String tax_person_num = ed_tax_person.getText().toString();
        tax_person_num = CommonUtil.getTaxPersonNum(tax_person_num);
        String clear_data = tax_person_num.concat(dsnCode).concat(vidCode);
        Logger.d("clear_data = " + clear_data);
        byte[] encryData_byte = CertifyDataUtil.encryptData(transKey_byte, clear_data.getBytes());

        HashMap<String, String> params = new HashMap();
        String token_data = SharePreUtil.getString(mContext, Contacts.Key.TOKEN, "");
        params.put(Contacts.Key.DSN, dsnCode.trim());//sn
        params.put(Contacts.Key.VID, vidCode.trim());//vid
        params.put(Contacts.Key.SIGN, certSign);//签名数据
        params.put(Contacts.Key.ENCRY, CommonUtil.byte2Hex(encryData_byte).trim());//加密数据
        params.put(Contacts.Key.MT, Contacts.Const.DEV_BIND_UNBIND + "");
        params.put(Contacts.Key.ST, CommonUtil.getSystemTime().trim());
        params.put(Contacts.Key.TOKEN, token_data);
        params.put(Contacts.Key.BIND,Contacts.Const.DEVUNBIND+"");
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
                                String bind_data = CertifyDataUtil.serverSignVerify(sign, msgEncry, mPOSFunctionUtils);
                                Logger.d("unbind_data = " + bind_data.trim());
                            }
                        } else {
                            ToastUtil.showShortToast(mContext, msg + "");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}
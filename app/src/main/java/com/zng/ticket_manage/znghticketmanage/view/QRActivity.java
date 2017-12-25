package com.zng.ticket_manage.znghticketmanage.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zng.common.POSFunctionUtils;
import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.commonlibrary.dialogutil.ToastUtil;
import com.zng.ticket_manage.commonlibrary.httputil.OkhttpManagerUtils;
import com.zng.ticket_manage.commonlibrary.jsonutil.JsonUtil;
import com.zng.ticket_manage.commonlibrary.utils.SharePreUtil;
import com.zng.ticket_manage.znghticketmanage.R;
import com.zng.ticket_manage.znghticketmanage.bean.EncryBean;
import com.zng.ticket_manage.znghticketmanage.bean.ResultInfor;
import com.zng.ticket_manage.znghticketmanage.bean.TaxRateBean;
import com.zng.ticket_manage.znghticketmanage.utils.CertifyDataUtil;
import com.zng.ticket_manage.znghticketmanage.utils.CommonUtil;
import com.zng.ticket_manage.znghticketmanage.utils.Contacts;
import com.zng.ticket_manage.znghticketmanage.utils.QRcodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by zqh on 2017/12/20.
 */

public class QRActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_title)
    TextView tv_order_title;
    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;
    @BindView(R.id.tv_input_hand)
    TextView tv_input_hand;
    @BindView(R.id.tv_print_qr_code)
    TextView tv_print_qr_code;
    private POSFunctionUtils mPOSFunctionUtils;
    private String bmpFileUrl = Environment.getExternalStorageDirectory()
            .getPath() + "/android/data/sign/";


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_make_invoice_immediate;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar.setTitle("开票二维码");
        toolbar.setSubtitle("二维码查询");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.title_back_btn_bg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_input_hand.setOnClickListener(this);
        tv_print_qr_code.setOnClickListener(this);
        mPOSFunctionUtils = new POSFunctionUtils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_print_qr_code://打印二维码
                submit();
                break;
            case R.id.tv_input_hand://手工开票--请求税率
                getTaxRate();
                break;
        }
    }

    private void getTaxRate() {
        HashMap<String, String> params = new HashMap();
        // 发行商id 00100017
        String vidCode = CommonUtil.getVid(mContext, mPOSFunctionUtils);
        String dsnCode = CommonUtil.getDsn();
        String token_data = SharePreUtil.getString(mContext, Contacts.Key.TOKEN, "");
        String certSign = CertifyDataUtil.getCertSign(mContext, mPOSFunctionUtils);
        byte[] transKey_byte = CertifyDataUtil.getTransKey();

        byte[] encryData_byte = CertifyDataUtil.encryptData(transKey_byte, dsnCode.getBytes());

        params.put(Contacts.Key.SIGN, certSign);//签名数据
        params.put(Contacts.Key.ST, CommonUtil.getSystemTime().trim());
        params.put(Contacts.Key.TOKEN, token_data);
        params.put(Contacts.Key.VID, vidCode.trim());
        params.put(Contacts.Key.DSN, dsnCode.trim());
        params.put(Contacts.Key.MT, Contacts.Const.TAXRATE + "");
        params.put(Contacts.Key.LANGUAGE, CommonUtil.getCurrentLauguage());
        params.put(Contacts.Key.ENCRY, CommonUtil.byte2Hex(encryData_byte).trim());//加密数据

        String mapToJson = JsonUtil.parseMapToJson(params);
        Logger.json(mapToJson);

        OkhttpManagerUtils.postAsync(mContext, Contacts.ServerPath.LOGIN_PATH, params, new OkhttpManagerUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Logger.d("failed = " + request.body().toString());
            }

            @Override
            public void requestSuccess(String result) {
                Logger.d("success = " + result);
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
                                Logger.d("blue_ticket = " + bind_data.trim());
                                List<TaxRateBean> list = JsonUtil.parseJsonToList(bind_data.trim(), TaxRateBean.class);
                                for (TaxRateBean bean : list){
                                    Logger.d("bean.name() = "+bean.getGoods_name()+" : bean.tax = "+bean.getTax_rate_desc());
                                }
                            }
                        } else {
                            ToastUtil.showShortToast(mContext, msg + "");
                        }
                    }
                }
            }
        });
    }


    private void submit() {
        HashMap<String, String> params = new HashMap();

        // 发行商id 00100017
        String vidCode = CommonUtil.getVid(mContext, mPOSFunctionUtils);
        String dsnCode = CommonUtil.getDsn();
        String token_data = SharePreUtil.getString(mContext, Contacts.Key.TOKEN, "");
        String certSign = CertifyDataUtil.getCertSign(mContext, mPOSFunctionUtils);
        byte[] transKey_byte = CertifyDataUtil.getTransKey();

        String taxCode = "12345678901231567310";
        String name = "";

        EncryBean encryBean = new EncryBean();
        List<EncryBean.ItemList> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            EncryBean.ItemList item = new EncryBean().new ItemList();
            if (i == 0) {
                item.setDiscountRateValue("0.17");
                item.setGoodsName("小麦");
                item.setInvoiceAmount("117.00");
                item.setMeteringUnit("袋");
                item.setQuantity("1.00000000");
                item.setTaxClassificationCode("1010101020000000000");
                item.setTaxRateValue("0.17");
                item.setUnitPrice("117.00000000");
                item.setIncludeTaxFlag("1");
//                item.setDeductionAmount("80.00");
            } else {
                item.setDiscountRateValue("0.13");
                item.setGoodsName("稻谷商品");
                item.setInvoiceAmount("56.64");
                item.setMeteringUnit("袋");
                item.setQuantity("1.00000000");
                item.setTaxClassificationCode("1010101010000000000");
                item.setTaxRateValue("0.13");
                item.setIncludeTaxFlag("1");
                item.setUnitPrice("56.64000000");
//                item.setDeductionAmount("40.00");
            }
            list.add(item);
        }
        encryBean.setItemList(list);
        encryBean.setInvoiceReqSerialNo("ZNGT1000000000000272");

        String encryData = JsonUtil.parseBeanToJson(encryBean);
        Logger.json(encryData);


        byte[] encryData_byte = CertifyDataUtil.encryptData(transKey_byte, encryData.getBytes());

        params.put(Contacts.Key.SIGN, certSign);//签名数据
        params.put(Contacts.Key.ST, CommonUtil.getSystemTime().trim());
        params.put(Contacts.Key.AUDITNUM, taxCode);
        params.put(Contacts.Key.TOKEN, token_data);
        params.put(Contacts.Key.VID, vidCode.trim());
        params.put(Contacts.Key.DSN, dsnCode.trim());
        params.put(Contacts.Key.MT, Contacts.Const.QRCODE + "");
        params.put(Contacts.Key.LANGUAGE, CommonUtil.getCurrentLauguage());
        params.put(Contacts.Key.ENCRY, CommonUtil.byte2Hex(encryData_byte).trim());//加密数据

        String mapToJson = JsonUtil.parseMapToJson(params);
        Logger.json(mapToJson);

        OkhttpManagerUtils.postAsync(mContext, Contacts.ServerPath.LOGIN_PATH, params, new OkhttpManagerUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Logger.d("failed = " + request.body().toString());
            }

            @Override
            public void requestSuccess(String result) {
                Logger.d("success = " + result);
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
                                Bitmap bitmap = QRcodeUtils.createTwodCode(bind_data, QRActivity.this);

                                //                                String qrCodePath = JsonUtil.getFieldValue(bind_data, "qrCodePath");
//                                String qrCode = JsonUtil.getFieldValue(bind_data, "qrCode");
//                                String invoiceReqSerialNo = JsonUtil.getFieldValue(bind_data, "invoiceReqSerialNo");
//                                byte[] decode = Base64Utils.decode(qrCodePath);
//                                Logger.d("qrCodePath = " + new String(decode));
//                                Bitmap bitmap = stringtoBitmap(qrCode);
                                if (bitmap != null) {
                                    iv_qrcode.setImageBitmap(bitmap);
                                }
                                Logger.d("blue_ticket = " + bind_data.trim());
                            }
                        } else {
                            ToastUtil.showShortToast(mContext, msg + "");
                        }
                    }
                }
            }
        });
    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}

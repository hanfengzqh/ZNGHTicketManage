package com.zng.ticket_manage.znghticketmanage.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zng.common.POSFunctionUtils;
import com.zng.ticket_manage.commonlibrary.base.BaseActivity;
import com.zng.ticket_manage.commonlibrary.dialogutil.ToastUtil;
import com.zng.ticket_manage.commonlibrary.httputil.OkhttpManagerUtils;
import com.zng.ticket_manage.commonlibrary.jsonutil.JsonUtil;
import com.zng.ticket_manage.commonlibrary.utils.Base64Utils;
import com.zng.ticket_manage.commonlibrary.utils.SharePreUtil;
import com.zng.ticket_manage.znghticketmanage.R;
import com.zng.ticket_manage.znghticketmanage.bean.EncryBean;
import com.zng.ticket_manage.znghticketmanage.bean.ResultInfor;
import com.zng.ticket_manage.znghticketmanage.utils.CertifyDataUtil;
import com.zng.ticket_manage.znghticketmanage.utils.CommonUtil;
import com.zng.ticket_manage.znghticketmanage.utils.Contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

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
    @BindView(R.id.image)
    ImageView image;

    private boolean isExpand = true;//默认关闭
    private POSFunctionUtils mPOSFunctionUtils;
    private String bmpFileUrl = Environment.getExternalStorageDirectory()
            .getPath() + "/android/data/sign/";

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
        mPOSFunctionUtils = new POSFunctionUtils(this);
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
                ToastUtil.showShortToast(this, "提交");
                submit();
                break;
            case R.id.tv_expand:
                isExpand = !isExpand;
                ll_recognition_num.setVisibility(View.VISIBLE);
                tv_expand.setVisibility(View.VISIBLE);

                if (isExpand) {
                    tv_expand.setImageResource(R.mipmap.icon_pull_down);
                    ll_select_info.setVisibility(View.GONE);
                    ll_email.setVisibility(View.GONE);
                } else {
                    tv_expand.setImageResource(R.mipmap.icon_pull_up);
                    ll_select_info.setVisibility(View.VISIBLE);
                    ll_email.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    private void submit() {
        HashMap<String, String> params = new HashMap();

        // 发行商id 00100017
        String vidCode = CommonUtil.getVid(mContext, mPOSFunctionUtils);
        String dsnCode = CommonUtil.getDsn();
        String token_data = SharePreUtil.getString(mContext, Contacts.Key.TOKEN, "");
        String certSign = CertifyDataUtil.getCertSign(mContext, mPOSFunctionUtils);
        byte[] transKey_byte = CertifyDataUtil.getTransKey();

        String taxCode = "12345678911234567890";
        String invoiceHeard = "北京信息科技大学";
        String buyerAddress = "北京市海淀区中关村大街1号海龙大厦10层1026";
        String name = "";

        EncryBean encryBean = new EncryBean();
        try {
            encryBean.setBuyerName(invoiceHeard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EncryBean.ItemList> list = new ArrayList<>();
        encryBean.setTaxpayerNum("110101201702071");
        encryBean.setBuyerTaxpayerNum("110101201702071");
        encryBean.setBuyerAddress(buyerAddress);
        encryBean.setBuyerTel("0351-3053694");
        encryBean.setBuyerBankName("建设银行");
        encryBean.setBuyerBankAccount("98785623245666");
        encryBean.setSellerAddress(buyerAddress);
        encryBean.setSellerTel("0351-3053694");
        encryBean.setSellerBankName("建设银行");
        encryBean.setSellerBankAccount("621700002000678");
        encryBean.setCasherName("zhzh");
        encryBean.setReviewerName("姬");
        encryBean.setDrawerName("刘公子");
        encryBean.setTakerName("姬发");
        encryBean.setTakerTel("15773150949");
        encryBean.setTakerEmail("438742956@qq.com");

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
            }
            else {
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
        encryBean.setInvoiceReqSerialNo("ZNGT1000000001000267");

        String encryData = JsonUtil.parseBeanToJson(encryBean);
        Logger.json(encryData);


        byte[] encryData_byte = CertifyDataUtil.encryptData(transKey_byte, encryData.getBytes());

        params.put(Contacts.Key.SIGN, certSign);//签名数据
        params.put(Contacts.Key.ST, CommonUtil.getSystemTime().trim());
        params.put(Contacts.Key.AUDITNUM, taxCode);
        params.put(Contacts.Key.TOKEN, token_data);
        params.put(Contacts.Key.VID, vidCode.trim());
        params.put(Contacts.Key.DSN, dsnCode.trim());
        params.put(Contacts.Key.MT, Contacts.Const.BLUETICKET + "");
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
                if (!TextUtils.isEmpty(result)){
                    ResultInfor infor = JsonUtil.parseJsonToBean(result, ResultInfor.class);
                    if (infor != null){
                        boolean result1 = infor.isResult();
                        String msg = infor.getMsg();
                        if (result1){
                            String msgEncry = infor.getEncry();
                            String sign = infor.getSign();
                            if (!TextUtils.isEmpty(msgEncry) && !TextUtils.isEmpty(sign)){
                                String bind_data = CertifyDataUtil.serverSignVerify(sign, msgEncry, mPOSFunctionUtils);
                                String qrCodePath = JsonUtil.getFieldValue(bind_data, "qrCodePath");
                                String qrCode = JsonUtil.getFieldValue(bind_data, "qrCode");
                                String invoiceReqSerialNo = JsonUtil.getFieldValue(bind_data, "invoiceReqSerialNo");

                                byte[] decode = Base64Utils.decode(qrCodePath);

                                Logger.d("qrCodePath = "+new String(decode));

                                Bitmap bitmap = stringtoBitmap(qrCode);
                                if (bitmap != null){
                                    image.setImageBitmap(bitmap);
                                }
                                saveBmp(bitmap,invoiceReqSerialNo,invoiceReqSerialNo);
                                Logger.d("blue_ticket = "+bind_data.trim());
                            }
                        }else{
                            ToastUtil.showShortToast(mContext,msg+"");
                        }
                    }
                }
            }
        });
    }

    public Bitmap stringtoBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将Bitmap存为 .bmp格式图片
     *
     * @param bitmap
     */
    private boolean saveBmp(Bitmap bitmap, String folder, String fileName) {
        if (bitmap == null)
            return false;
        // 位图大小
        int nBmpWidth = bitmap.getWidth();
        int nBmpHeight = bitmap.getHeight();
        // 图像数据大小
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);
        try {
            File file = new File(bmpFileUrl + folder + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileos = new FileOutputStream(bmpFileUrl + folder
                    + "/" + fileName);
            // bmp文件头
            int bfType = 0x4d42;
            long bfSize = 14 + 40 + bufferSize;
            int bfReserved1 = 0;
            int bfReserved2 = 0;
            long bfOffBits = 14 + 40;
            // 保存bmp文件头
            writeWord(fileos, bfType);
            writeDword(fileos, bfSize);
            writeWord(fileos, bfReserved1);
            writeWord(fileos, bfReserved2);
            writeDword(fileos, bfOffBits);
            // bmp信息头
            long biSize = 40L;
            long biWidth = nBmpWidth;
            long biHeight = nBmpHeight;
            int biPlanes = 1;
            int biBitCount = 24;
            long biCompression = 0L;
            long biSizeImage = 0L;
            long biXpelsPerMeter = 0L;
            long biYPelsPerMeter = 0L;
            long biClrUsed = 0L;
            long biClrImportant = 0L;
            // 保存bmp信息头
            writeDword(fileos, biSize);
            writeLong(fileos, biWidth);
            writeLong(fileos, biHeight);
            writeWord(fileos, biPlanes);
            writeWord(fileos, biBitCount);
            writeDword(fileos, biCompression);
            writeDword(fileos, biSizeImage);
            writeLong(fileos, biXpelsPerMeter);
            writeLong(fileos, biYPelsPerMeter);
            writeDword(fileos, biClrUsed);
            writeDword(fileos, biClrImportant);
            // 像素扫描
            byte bmpData[] = new byte[bufferSize];
            int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);
            for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)
                for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {
                    int clr = bitmap.getPixel(wRow, nCol);
                    bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color
                            .blue(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color
                            .green(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color
                            .red(clr);
                }

            fileos.write(bmpData);
            fileos.flush();
            fileos.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void writeWord(FileOutputStream stream, int value)
            throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        stream.write(b);
    }

    protected void writeDword(FileOutputStream stream, long value)
            throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    protected void writeLong(FileOutputStream stream, long value)
            throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }
}

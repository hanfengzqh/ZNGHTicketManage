package com.zng.ticket_manage.znghticketmanage.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zng.common.POSFunctionUtils;
import com.zng.common.utils.Utils;
import com.zng.ticket_manage.commonlibrary.utils.HexUtils;

import java.util.Date;
import java.util.Locale;

/**
 * Created by zqh on 2017/12/1.
 */

public class CommonUtil {

    /**
     * 获取发行商id 00100017
     *
     * @param context
     * @param mPOSFunctionUtils
     * @return
     */
    public static String getVid(Context context, POSFunctionUtils mPOSFunctionUtils) {
        StringBuffer sb = new StringBuffer();
        String pSn = "";
        if (mPOSFunctionUtils != null) {
            pSn = mPOSFunctionUtils.getPublisherId();
        }
        if (TextUtils.isEmpty(pSn)) {
            for (int i = 0; i < 16; i++) {
                sb.append(" ");
            }
            pSn = sb.toString();
        } else {
            int length = 16 - pSn.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            pSn = pSn.concat(sb.toString());
        }
        Logger.d("pSn = " + pSn);
        return pSn;
    }

    /**
     * 获取发行商SN H111111122
     *
     * @param mContext
     * @param mPOSFunctionUtils
     * @return
     */
    public static String getPsn(Context mContext, POSFunctionUtils mPOSFunctionUtils) {
        StringBuffer sb = new StringBuffer();
        String posSn = "";

        if (mPOSFunctionUtils != null) {
            posSn = mPOSFunctionUtils.getPosSn();
        }

        if (TextUtils.isEmpty(posSn)) {
            posSn = Utils.getStringValuesIntoSettings(mContext, "device_code");
        }

        if (TextUtils.isEmpty(posSn)) {
            for (int i = 0; i < 16; i++) {
                sb.append(" ");
            }
            posSn = sb.toString();
        } else {
            int length = 16 - posSn.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            posSn = posSn.concat(sb.toString());
        }

        Logger.d("posSn = " + posSn);
        return posSn;
    }

    /**
     * 获取设备认证发送时间戳
     */
    public static String getCurrentTimeMillis() {
        long time;
        Date date = new Date(System.currentTimeMillis());
        if (null == date) {
            time = 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            time = Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            time = 0;
        }
        StringBuilder sb = new StringBuilder();
        String times = String.valueOf(time);
        if (times.length() < 16) {
            int addZero = 16 - times.length();
            for (int i = 0; i < addZero; i++) {
                sb.append(" ");
            }
        }
        byte[] bytes = times.concat(sb.toString()).getBytes();
        return byteArray2HexString(bytes, bytes.length);
    }

    private static String byteArray2HexString(byte[] paramArrayOfByte,
                                              int paramInt) {
        int i = paramInt;
        StringBuilder localStringBuilder = new StringBuilder(paramInt);
        if (paramArrayOfByte.length < paramInt) {
            i = paramArrayOfByte.length;
        }
        for (int j = 0; ; j++) {
            if (j >= i)
                return localStringBuilder.toString();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Byte.valueOf(paramArrayOfByte[j]);
            localStringBuilder.append(String.format("%02X", arrayOfObject));
        }
    }

    /**
     * 获取20位纳税人识别号
     *
     * @param tax_person_num 输入的纳税人识别号
     * @return
     */
    public static String getTaxPersonNum(String tax_person_num) {
        StringBuffer sb = new StringBuffer();
        if (TextUtils.isEmpty(tax_person_num)) {
            for (int i = 0; i < 20; i++) {
                sb.append(" ");
            }
            tax_person_num = sb.toString();
        } else {
            int length = 20 - tax_person_num.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            tax_person_num = tax_person_num.concat(sb.toString());
        }
        return tax_person_num;
    }

    /**
     * 获取20位纳税人识别号
     *
     * @param account_num 输入的纳税人识别号
     * @return
     */
    public static String getAccountNum(String account_num) {
        StringBuffer sb = new StringBuffer();
        if (TextUtils.isEmpty(account_num)) {
            for (int i = 0; i < 30; i++) {
                sb.append(" ");
            }
            account_num = sb.toString();
        } else {
            int length = 30 - account_num.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
            account_num = account_num.concat(sb.toString());
        }
        return account_num;
    }

    /**
     * 获取设备SN
     *
     * @return
     */
    public static String getDsn() {
        StringBuffer sb = new StringBuffer();
        String serial = Build.SERIAL;
        if (TextUtils.isEmpty(serial)) {
            for (int i = 0; i < 16; i++) {
                sb.append(" ");
            }
            serial = sb.toString();
        } else {
            int length = 16 - serial.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }

            serial = serial.concat(sb.toString());
        }
        Logger.d("serial = " + serial);
        return serial;
    }


    public static byte[] strToHexByte(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = (hexString.length() + 1) / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    // 转换成十六进制字符串
    public static String byte2Hex(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((b[i] & 0xff) < 0x10)
                result.append("0");
            result.append(Long.toString(b[i] & 0xff, 16));
        }
        return result.toString().toUpperCase();
    }

    // 获取系统当前年月日时分秒时间
    public static String getSystemTime() {
        String valueOf = String.valueOf(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();

        if (valueOf.length() < 16) {
            int length = 16 - valueOf.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
        }
        return valueOf.concat(sb.toString());
    }

    public static String bytesToHexStr(byte[] bytes) {
        String returnStr = "";
        if (bytes != null) {
            for (int i = 0; i < bytes.length; i++) {
                returnStr += Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1);
            }
        }
        return returnStr.toUpperCase();
    }

    /**
     * 获取语言信息
     */
    public static String getCurrentLauguage() {
        //获取系统当前使用的语言
        String mCurrentLanguage = Locale.getDefault().getLanguage();
        //设置成简体中文的时候，getLanguage()返回的是zh
        return mCurrentLanguage;
    }

    /**
     * 获取购买方名称
     * @param invoiceHeard
     * @return
     */
    public static String getInvoiceHeard(String invoiceHeard) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(invoiceHeard)) {
            invoiceHeard = "";
        }

        if (invoiceHeard.length() < 200) {
            int length = 200 - invoiceHeard.length();
            for (int i = 0; i < length; i++) {
                sb.append(" ");
            }
        }
        invoiceHeard = invoiceHeard.concat(sb.toString());
        invoiceHeard = HexUtils.str2HexStr(invoiceHeard);

        return invoiceHeard;
    }
}

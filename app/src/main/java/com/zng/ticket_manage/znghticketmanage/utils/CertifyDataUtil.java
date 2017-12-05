package com.zng.ticket_manage.znghticketmanage.utils;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zng.common.POSFunctionUtils;
import com.zng.ticket_manage.commonlibrary.jsonutil.JsonUtil;
import com.zng.ticket_manage.commonlibrary.utils.Base64Utils;
import com.zng.ticket_manage.commonlibrary.utils.Des3Utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.zng.ticket_manage.znghticketmanage.utils.CommonUtil.strToHexByte;

/**
 * Created by zqh on 2017/12/1.
 */

public class CertifyDataUtil {

    /**
     * 传输密钥
     */
    public static String transKey = "";

    /**
     * 获取2.0认证数据
     *
     * @param mContext
     * @param mPosFunctionUtils
     * @return
     */
    public static String getCertSign(Context mContext, POSFunctionUtils mPosFunctionUtils) {
        String certSign = "";
        mPosFunctionUtils.IConnectCard(0);
        String time = "10".concat(CommonUtil.getCurrentTimeMillis());
        String commandData = "FE860000".concat(time);
        String mIExchangeCardData = mPosFunctionUtils.IExchangeCard(0, commandData);
        mPosFunctionUtils.IDisconnectCard(0);
        Logger.json(mIExchangeCardData);
        int result_code = JsonUtil.getIntValue(mIExchangeCardData, Contacts.Key.RETURN_CODE);
        if (result_code == 0) {
            String commandResult = JsonUtil.getFieldValue(mIExchangeCardData, Contacts.Key.SEND_COMMAND_RESULT);
            Logger.d("commandResult = " + commandResult);
            if (commandResult != null && commandResult.length() >= 4) {
                int length = commandResult.length();
                String commandResult_last4 = commandResult.substring(length - 4, length);
                if (TextUtils.equals(commandResult_last4, "9000")) {
                    int len = Integer.valueOf(commandResult.substring(0, 2), 16) * 2;//432
                    Logger.d("len = " + len);
                    byte[] bytes = commandResult.getBytes();
                    try {
                        certSign = new String(bytes, 2, len, "UTF-8");
                        Logger.d("cerSign = " + certSign);
                        transKey = commandResult.substring(len + 4, length - 4);
                        Logger.d("transKey = " + transKey);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return certSign;
    }


    /**
     * 获取传输密钥
     *
     * @return
     */
    public static byte[] getTransKey() {

        byte[] transKey_bytes = strToHexByte(transKey);
        byte[] keyTotal = Des3Utils.TrippleDesCbcEncode(
                strToHexByte(Contacts.Const.ROOTKEY),
                Des3Utils.iv_bytes, transKey_bytes,
                transKey_bytes.length);
        return keyTotal;
    }

    /**
     * @param trans_Key        传输密钥
     * @param clear_data_bytes 明文数据
     * @return 加密数据
     */
    public static byte[] encryptData(byte[] trans_Key, byte[] clear_data_bytes) {
        int len;
        len = clear_data_bytes.length;
        if ((len % 8) != 0) {
            int mod = 8 - (len % 8);
            len += mod;
        }
        byte[] last_cmd_byte = new byte[len];
        Arrays.fill(last_cmd_byte, (byte) 0);
        System.arraycopy(clear_data_bytes, 0, last_cmd_byte, 0, clear_data_bytes.length);

        byte[] keyLast = Des3Utils.TrippleDesCbcEncode(trans_Key,
                Des3Utils.iv_bytes, last_cmd_byte, last_cmd_byte.length);
        String strLast = CommonUtil.byte2Hex(keyLast);
        Logger.d("strLast = " + strLast);
        return keyLast;
    }


    public static byte[] getProcessKey_byte(String sign, POSFunctionUtils mPosFunctionUtils) {
        byte[] processKey_byte = null;
        if (TextUtils.isEmpty(sign) || mPosFunctionUtils == null) {
            return null;
        }

        int powerOnRet = mPosFunctionUtils.IConnectCard(0);
        if (powerOnRet == 0) {
            String time = "10".concat(CommonUtil.getCurrentTimeMillis());
            byte[] signbuf = sign.getBytes();
            String signDatas = Integer.toHexString(sign.length()).toUpperCase()
                    + CommonUtil.bytesToHexStr(signbuf);
            String length = Integer.toHexString(time.length() / 2
                    + sign.length() + 1);

            String IExchangeCardData = mPosFunctionUtils.IExchangeCard(0,
                    "FE870000" + length + time + signDatas);
            Logger.json(IExchangeCardData);
            mPosFunctionUtils.IDisconnectCard(0);
            if (!TextUtils.isEmpty(IExchangeCardData)) {
                int result_code = JsonUtil.getIntValue(IExchangeCardData, Contacts.Key.RETURN_CODE);
                if (result_code == 0) {
                    String commandResult = JsonUtil.getFieldValue(IExchangeCardData, Contacts.Key.SEND_COMMAND_RESULT);
                    Logger.d("commandResult = " + commandResult);
                    if (commandResult != null && commandResult.length() >= 4) {
                        int length2 = commandResult.length();
                        String commandResult_last4 = commandResult.substring(length2 - 4, length2);
                        if (TextUtils.equals(commandResult_last4, "9000")) {
                            processKey_byte = strToHexByte(commandResult.substring(0, length2 - 4));
                        }
                    }
                }
            }
        }
        return processKey_byte;
    }

    /**
     * 对下行数据产生的过程密钥加密得到 传输密钥
     *
     * @param processKey_byte 过程密钥
     */
    public static String encrypProcess(byte[] processKey_byte) {
        String encry_process_data = "";
        if (processKey_byte == null) {
            return encry_process_data;
        }
        byte[] keyTotal = Des3Utils.TrippleDesCbcEncode(
                strToHexByte(Contacts.Const.ROOTKEY),
                Des3Utils.iv_bytes, processKey_byte,
                processKey_byte.length);
        String strLast = CommonUtil.byte2Hex(keyTotal);
        return strLast;
    }

    // 3DES解密
    private static String decrypted(byte[] lastKey_byte, byte[] encry_byte) {
        String decryptedData = "";
        if (lastKey_byte == null || encry_byte == null) {
            return "";
        }

        byte[] trippleDesCbcDecode_byte = Des3Utils.TrippleDesCbcDecode(lastKey_byte,
                Des3Utils.iv_bytes, encry_byte, encry_byte.length);
        try {
            decryptedData = new String(trippleDesCbcDecode_byte, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decryptedData;
    }

    // 数据验证并解密
    public static String serverSignVerify(String sign, String encry,
                                          POSFunctionUtils mPosFunctionUtils) {

        byte[] processKey_byte = getProcessKey_byte(sign, mPosFunctionUtils);

        String encrypProcess = encrypProcess(processKey_byte);

        String decrypted = decrypted(strToHexByte(encrypProcess), Base64Utils.decode(encry));
        return decrypted;

    }
}

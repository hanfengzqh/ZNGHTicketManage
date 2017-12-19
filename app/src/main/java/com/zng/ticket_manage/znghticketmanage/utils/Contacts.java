package com.zng.ticket_manage.znghticketmanage.utils;

/**
 * Created by zqh on 2017/12/1.
 */

public class Contacts {

    public static final class Key {
        public static final String RETURN_CODE = "returnCode";
        public static final String SEND_COMMAND_RESULT = "SCommandResult";

        public static final String MT = "mT";//请求类型
        public static final String ST = "sT";//请求时间
        public static final String VID = "vId";// vid
        public static final String DSN = "dsn";// 签名
        public static final String SIGN = "sign";// 签名
        public static final String BIND = "bind";//绑定
        public static final String TOKEN = "token";//有效期
        public static final String ENCRY = "encry";// 签名
        public static final String LANGUAGE = "language";//语言国际化

    }

    public static final class Const {
        public static final String ROOTKEY = "";

        public static final int LOGIN = 1;//登录
        public static final int DEVICEBIND = 2;  // 设备绑定/解绑

        public static final int DEVUNBIND = 0;//解绑
        public static final int DEVBIND = 1;//绑定

        public static final int ACTIVATIONSEL = 3; //激活查询
        public static final int REDTICKET = 4; //红票开票流程
        public static final int BLUETICKET = 5; // 蓝票开票流程
        public static final int QRcode = 6; // 二维码
    }

    public static final class ServerPath {
        public static final String LOGIN_PATH = "http://172.16.3.245:8028/bill/bill/index";
    }

    public static final class Code {

        public static final int MSG_SUCCESS = 1111;   //成功
        public static final int MSG_PARAM_ERROR = 1010;   // 参数错误
        public static final int MSG_UNLOGIN = 1110;    //请先登录
        public static final int MSG_UNFOUND_DEVKEY = 2222;   //未找到设备秘钥
        public static final int MSG_DECRPT_FAIL = 3333;    //解密失败
        public static final int MSG_CHECKSIGN_FAIL = 4444;  //签名校验失败    签名数据异常
        public static final int MSG_REQUEST_TIMEOUT = 5555;  //请求超时
        public static final int MSG_LOGIN_FAIL = 1100;   //登录失败
        public static final int MSG_DEVICE_BIND_OTHER_MERCHANT = 6600;  //设备已绑定到其他商户下
        public static final int MSG_DEVICE_BOUND = 6666;  //设备已绑定
        public static final int MSG_UNLAWFUL_REQUEST = 5500; //非法请求
        public static final int MSG_REQUEST_PARAM_ERROR = 5000; // 非法请求参数
        public static final int MSG_EXCEPTION = 7777;    //异常
        public static final int MSG_TICKETING_NOTBEEN_ACTIVATED = 8880; // 尚未激活票务功能
        public static final int MSG_DEVICE_UNBIND = 6655;   //设备尚未绑定
        public static final int HAS_BEEN_ACTIVATED_AND_BOUND = 6688; //查询成功
        public static final int MSG_MERCHANT_LOCKED = 9999;   //设备被锁定
        public static final int MSG_BIND_ERROR = 6677;  //绑定失败   数据库插入失败
        public static final int MSG_CANCEL_ERROR = 6655;   //注销失败

    }
}

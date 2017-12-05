package com.zng.ticket_manage.znghticketmanage.utils;

/**
 * Created by zqh on 2017/12/1.
 */

public class Contacts {

    public static final class Key {
        public static final String RETURN_CODE = "returnCode";
        public static final String SEND_COMMAND_RESULT = "SCommandResult";

        public static final String SIGN = "sign";// 签名
        public static final String VID = "vId";// vid
        public static final String DSN = "dsn";// 签名
        public static final String ENCRY = "encry";// 签名
        public static final String MT = "mT";//请求类型
        public static final String ST = "sT";//请求时间
        public static final String TOKEN = "token";//有效期
        public static final String LANGUAGE = "language";//语言国际化
        public static final String BIND = "bind";

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

    public static final class ServerPath{
        public static final String LOGIN_PATH = "http://172.16.3.245:8028/bill/bill/index";
    }
}

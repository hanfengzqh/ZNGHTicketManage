package com.zng.ticket_manage.znghticketmanage.bean;

import java.io.Serializable;

/**
 * Created by zqh on 2017/12/4.
 */

public class ResultInfor implements Serializable {

    private String responsetime;
    private boolean result;
    private String msg;
    private String sign;
    private String encry;
    private int code;
    private String language;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncry() {
        return encry;
    }

    public void setEncry(String encry) {
        this.encry = encry;
    }
}

package com.zng.ticket_manage.commonlibrary.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by zqh on 2017/12/20.
 */

public class Utils {
    /**
     * 判断是否为字符型字符串
     *
     * @param number
     * @return
     */
    public static final boolean isInteger(final String number) {
        boolean result = false;
        if (number != null) {
            Pattern pattern = Pattern.compile("^-?\\d+$");
            Matcher matcher = pattern.matcher(number.trim());
            result = matcher.matches();
        }
        return result;
    }

    public static final boolean isFloat(final String number) {
        boolean result = false;
        if (number != null) {
            Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
            Matcher matcher = pattern.matcher(number);
            result = matcher.matches();
        }
        return result;
    }

    // 过滤特殊字符
    // 清除掉所有特殊字符

    public static String StringFilter(String str) throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
    public static String StringLetterNum(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}

package com.tan.ec.utils;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class DataUtil {

    //    判断整数（int）
    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //    判断浮点数（double和float）
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isIntOrDouble(String str){

        if(isInteger(str)||isDouble(str)){
            return true;
        }else{
            return false;
        }

    }

}

package com.ytxd.spp.net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 2017/5/16.
 */

public class Apis {
    public final static String BASE_URL = "http://api.zooheng.com:8889";
    public final static String BASE_SRC_URL = "http://api.zooheng.com:8888";

    public static String AddPATH(String url) {
        Pattern pattern = Pattern.compile("http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return url;
        } else {
            return BASE_SRC_URL + url;
        }
    }

    /**
    *@param Phone
     * @param LoginPwd
    * */
    public static String PhoneRegister = BASE_URL+"/API_User/PhoneRegister";



    /**
     *@param Phone
     * @param LoginPwd
     * */
    public static String PhoneLogin = BASE_URL+"/API_User/PhoneLogin";
}

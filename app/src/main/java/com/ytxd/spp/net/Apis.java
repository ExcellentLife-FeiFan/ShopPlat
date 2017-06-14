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
     * 根据手机号注册
     *
     * @param Phone
     * @param LoginPwd
     */
    public static String PhoneRegister = BASE_URL + "/API_User/PhoneRegister";


    /**
     * 登陆
     *
     * @param Phone
     * @param LoginPwd
     */
    public static String PhoneLogin = BASE_URL + "/API_User/PhoneLogin";


    /**
     * 第三方登录
     *
     * @param Type 类别,QQ,WeChat,WeiBo;传对应的参数
     * @param Code 第三方唯一号
     */
    public static String ThirdPartyLogin = BASE_URL + "/API_User/ThirdPartyLogin";


    /**
     * 发送验证码
     *
     * @param Phone
     */
    public static String SendSmscode_P = BASE_URL + "/API_User/SendMessageRetuenCode";


    /**
     * 根据手机号修改密码
     *
     * @param Phone
     * @param NewPwd
     */
    public static String ModifyPwdByPhone = BASE_URL + "/API_User/ModifyPwdByPhone";


    /**
     * 添加收货地址
     *
     * @param UserCode
     * @param Contacts
     * @param Phone
     * @param PhoneCheck 电话是否验证(1/0)
     * @param Lng
     * @param Lat
     * @param AddressTitle
     * @param AddressDescribe
     * @param AddressContent
     * @param IsDefault  是否默认(1/0)
     */
    public static String AddSHAddress_P = BASE_URL + "/API_User/AddSHAddress";


    /**
     * 获得用户收货地址列表
     *
     * @param UserCode
     */
    public static String GetUserSHAddressList = BASE_URL + "/API_User/GetUserSHAddressList";


    /**
     * 删除收货地址
     *
     * @param SHAddressCode
     */
    public static String DeleteSHAddress = BASE_URL + "/API_User/DeleteSHAddress";


    /**
     * 设置默认收货地址
     *
     * @param SHAddressCode
     * @param UserCode
     */
    public static String SetDefaultSHAddress = BASE_URL + "/API_User/SetDefaultSHAddress";


    /**
     * 获取超市列表
     *
     * @param CityName
     */
    public static String GetSupermarkeyList = BASE_URL + "/API_User/GetSupermarkeyList";
}

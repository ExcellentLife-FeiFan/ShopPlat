package com.ytxd.sppm.net;

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

    public static String AddPATH2(String url) {
        Pattern pattern = Pattern.compile("http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return url;
        } else {
            return BASE_URL + url;
        }
    }


    /**
     * 登陆
     *
     * @param LoginName
     * @param LoginPwd
     */
    public static String Login = BASE_URL + "/API_CS/CSLogin";


    /**
     * 获取订单列表
     * <p>
     *
     * @param SupermarketCode:超市编号
     * <p>
     * @param PageIndex:页码
     * <p>
     * @param PageSize:一页多少条数据
     * @param OrderStateCode
     */
    public static String GetCSOrderList = BASE_URL + "/API_CS/GetCSOrderList";

    /**
     * 获取商家配送员
     *
     * @param SupermarketCode
     */
    public static String GetCSPSStaff = BASE_URL + "/API_CS/GetCSPSStaff";

    /**
     * 更新订单已接单(等待配送)
     *
     * @param OrderCode:订单编号
     * @param AndroidOrIos:超市是安卓还是苹果
     * @param TSAlias:超市alias(用于给超市推送信息)
     */
    public static String UpdateOrderTaking = BASE_URL + "/API_CS/UpdateOrderTaking";

    /**
     * 更新订单已配送
     *
     * @param OrderCode:订单编号
     * @param DeliveryStaffCode:配送员编号
     * @param AndroidOrIos:超市是安卓还是苹果
     * @param TSAlias:超市alias(用于给超市推送信息)
     */
    public static String UpdateOrderYPS = BASE_URL + "/API_CS/UpdateOrderYPS";


    /**
     * 取消订单
     *
     * @param OrderCode
     * @param IsHFUserCoupon:是否恢复已使用优惠券(1/0)（有可能用户下单后直接取消订单，这样就要恢复等等。）
     * @param UserCouponCode:用户优惠券编号(上一个参数为1的情况，就更新这个优惠券可以恢复使用)
     * @param CancelInfo:删除原因(100个以内字符)
     */
    public static String SupermarketCancelOrder = BASE_URL + "/API_CS/SupermarketCancelOrder";



    /**
     * 更新推送别名
     *
     * @param SupermarketCode:用户编号
     * @param AndroidOrIos:安卓=1，苹果=2
     * @param TSAlias:alias
     */
    public static String UpdateCSAlias = BASE_URL + "/API_CS/UpdateCSAlias";



    /**
     * 更新订单已退款
     *
     * @param OrderCode:订单编号
     */
    public static String UpdateOrderYTK = BASE_URL + "/API_CS/UpdateOrderYTK";


    /**
     * 确认收货
     *
     * @param OrderCode:订单编号
     * @param AndroidOrIos:安卓=1，苹果=2
     * @param TSAlias:alias
     */
    public static String CSDetermineSH = BASE_URL + "/API_CS/CSDetermineSH";


}

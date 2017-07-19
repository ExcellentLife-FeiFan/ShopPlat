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
     * 修改收货地址
     *
     * @param SHAddressCode
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
    public static String ModifySHAddress = BASE_URL + "/API_User/ModifySHAddress";


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


    /**
     * 获取超市商品列表
     *
     * @param SupermarketCode 超市编号
     */
    public static String GetGoodsBySupermarket = BASE_URL + "/API_User/GetGoodsBySupermarket";


    /**
     * 生成未付款订单
     *
     * @param UserCode
     * @param SupermarketCode
     * @param SHAddressCode
     * @param UserCouponCode
     * @param ManJianCode
     * @param GoodsInfo :商品信息格式为：商品编号[商品数量, s001[2,s002[1
     * @param SDTime
     * @param Remarks
     * @param PayType
     * @param YPrice:原总价
     * @param SJPrice:现总价(所有优惠后价格)
     */
    public static String GenerateWFKOrder = BASE_URL + "/API_User/GenerateWFKOrder";


    /**
     * 获取订单列表
     *
     * @param UserCode
     * @param PageIndex
     * @param PageSize
     */
    public static String GetUserOrderList = BASE_URL + "/API_User/GetUserOrderList";


    /**
     * 更新订单已付款
     *
     * @param OrderCode
     * @param AndroidOrIos:超市是安卓还是苹果
     * @param TSAlias:超市alias(用于给超市推送信息)
     */
    public static String UpdateOrderPay = BASE_URL + "/API_User/UpdateOrderPay";

    /**
     * 确认收货（完成订单）
     *
     * @param OrderCode
     */
    public static String DetermineSH = BASE_URL + "/API_User/DetermineSH";

    /**
     * 取消订单
     *
     * @param OrderCode
     * @param IsHFUserCoupon:是否恢复已使用优惠券(1/0)（有可能用户下单后直接取消订单，这样就要恢复等等。）
     * @param UserCouponCode:用户优惠券编号(上一个参数为1的情况，就更新这个优惠券可以恢复使用)
     * @param CancelInfo:删除原因(100个以内字符)
     */
    public static String UserCancelOrder = BASE_URL + "/API_User/UserCancelOrder";


    /**
     * 获取用户优惠券
     *
     * @param UserCode
     */
    public static String GetUserCoupon = BASE_URL + "/API_User/GetUserCoupon";

    /**
     * 设置优惠券已读
     *
     * @param UserCode:用户编号
     */
    public static String SetUserCouponAllRead = BASE_URL + "/API_User/SetUserCouponAllRead";

    /**
     * 获取订单商品详情
     *
     * @param OrderCode:订单编号
     */
    public static String GetOrderGoodsInfo = BASE_URL + "/API_User/GetOrderInfo";

    /**
     * 获取超市满减信息
     *
     * @param SupermarketCode 超市编号
     */
    public static String GetSupermarketManJianList = BASE_URL + "/API_User/GetSupermarketManJianList";

    /**
     * 添加评价
     *
     * @param OrderCode:订单编号
     * @param SupermarketCode:超市编号
     * @param OrderCode:订单编号
     * @param UserCode:用户编号
     * @param EvaluateContent:评价内容
     * @param GoodsStar:商品星级（1-5）
     * @param PSStar:配送星级（1-5）
     * @param PicPath:晒图图片地址","逗号分隔(有上传文件的接口，自动返回逗号分隔的格式)
     * @param GoodsEvaluateInfo:商品评价信息，商品ID+$$$$+评价内容+$$$$+(赞/踩)赞=1踩=2 +$fg$
     */
    public static String AddEvaluate = BASE_URL + "/API_User/AddEvaluate";

    /**
     * 提交订单校验
     *
     * @param SupermarketCode:超市编号
     * @param GoodsCodes:商品编号逗号分隔
     * @param ManJianCode:满减编号
     */
    public static String SubmitOrderCheck = BASE_URL + "/API_User/SubmitOrderCheck";

    /**
     * 上传文件
     */
    public static String UpFiles = BASE_URL + "/API_Global/UpFiles";


    /**
     * 获取商家评价
     *
     * @param SupermarketCode:超市编号
     */
    public static String GetSupermarketEvaluate = BASE_URL + "/API_User/GetSupermarketEvaluate";


    /**
     * 获取商家商品评价
     *
     * @param GoodsCode
     */
    public static String GetGoodsEvaluate = BASE_URL + "/API_User/GetGoodsEvaluate";

    /**
     * 修改头像
     *
     * @param UserCode:用户编号
     * @param TitlePath:新头像路径
     */
    public static String ModifyIcon = BASE_URL + "/API_User/ModifyTitle";

    /**
     * 修改昵称
     *
     * @param UserCode:用户编号
     * @param NickName:新昵称
     */
    public static String ModifyNickName = BASE_URL + "/API_User/ModifyNickName";

    /**
     * 设置推送别名
     *
     * @param UserCode:用户编号
     * @param AndroidOrIos:安卓=1，苹果=2
     * @param TSAlias:alias
     */
    public static String UpdateUserAlias = BASE_URL + "/API_User/UpdateUserAlias";





}

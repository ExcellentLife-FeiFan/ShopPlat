package com.ytxd.spp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/6/19.
 */

public class OrderM implements Serializable {


    public static final String WATING_PAY = "0001", HAVE_PAY_WATING_ACE = "0002", FASE_PAY_WATING_ACE = "0003", HAVE_ACE_WATING_SEND = "0004", SENDING = "0005", SUCCESS = "0006", CANCEL_U = "7001", CANCEL_M = "7002",HAVE_REFUND = "7003";

    private String OrderCode;
    private String OrderStateCode;
    private String GoodsInfo;
    private MerchantM SuperMarketModel;
    private String UserCode;
    private String SupermarketCode;
    private String DeliveryStaffCode;
    private String SHAddressCode;
    private String Contacts;
    private int Sex;
    private String Phone;
    private String AddressTitle;
    private String AddressDescribe;
    private String AddressContent;
    private String SDTime;
    private String Remarks;
    private String PayType;
    private String PayReturnCode;
    private String YPrice;
    private String ManJianCode;
    private String ManJianName;
    private String MMoney;
    private String JMoney;
    private String UserCouponCode;
    private String CouponPrice;
    private String SJPrice;
    private String PSPrice;
    private String CancelInfo;
    private int IsEvaluate;
    private String CreateTime;
    private DeliveryStaffM DeliveryStaffModel;

    private List<ShoppingCartM.Goods> goods;

    private List<OrderGoodM> ChildrenGoods;

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getOrderStateCode() {
        return OrderStateCode;
    }

    public void setOrderStateCode(String OrderStateCode) {
        this.OrderStateCode = OrderStateCode;
    }

    public String getGoodsInfo() {
        return GoodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        GoodsInfo = goodsInfo;
    }

    public MerchantM getSuperMarketModel() {
        return SuperMarketModel;
    }

    public void setSuperMarketModel(MerchantM superMarketModel) {
        SuperMarketModel = superMarketModel;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getSupermarketCode() {
        return SupermarketCode;
    }

    public void setSupermarketCode(String SupermarketCode) {
        this.SupermarketCode = SupermarketCode;
    }

    public String getDeliveryStaffCode() {
        return DeliveryStaffCode;
    }

    public void setDeliveryStaffCode(String DeliveryStaffCode) {
        this.DeliveryStaffCode = DeliveryStaffCode;
    }

    public String getSHAddressCode() {
        return SHAddressCode;
    }

    public void setSHAddressCode(String SHAddressCode) {
        this.SHAddressCode = SHAddressCode;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String Contacts) {
        this.Contacts = Contacts;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }


    public String getAddressTitle() {
        return AddressTitle;
    }

    public void setAddressTitle(String AddressTitle) {
        this.AddressTitle = AddressTitle;
    }

    public String getAddressDescribe() {
        return AddressDescribe;
    }

    public void setAddressDescribe(String AddressDescribe) {
        this.AddressDescribe = AddressDescribe;
    }

    public String getAddressContent() {
        return AddressContent;
    }

    public void setAddressContent(String AddressContent) {
        this.AddressContent = AddressContent;
    }

    public String getSDTime() {
        return SDTime;
    }

    public void setSDTime(String SDTime) {
        this.SDTime = SDTime;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String PayType) {
        this.PayType = PayType;
    }

    public String getPayReturnCode() {
        return PayReturnCode;
    }

    public void setPayReturnCode(String PayReturnCode) {
        this.PayReturnCode = PayReturnCode;
    }

    public String getYPrice() {
        return YPrice;
    }

    public void setYPrice(String YPrice) {
        this.YPrice = YPrice;
    }

    public String getManJianCode() {
        return ManJianCode;
    }

    public void setManJianCode(String ManJianCode) {
        this.ManJianCode = ManJianCode;
    }

    public String getManJianName() {
        return ManJianName;
    }

    public void setManJianName(String ManJianName) {
        this.ManJianName = ManJianName;
    }

    public String getMMoney() {
        return MMoney;
    }

    public void setMMoney(String MMoney) {
        this.MMoney = MMoney;
    }

    public String getJMoney() {
        return JMoney;
    }

    public void setJMoney(String JMoney) {
        this.JMoney = JMoney;
    }

    public String getUserCouponCode() {
        return UserCouponCode;
    }

    public void setUserCouponCode(String UserCouponCode) {
        this.UserCouponCode = UserCouponCode;
    }

    public String getCouponPrice() {
        return CouponPrice;
    }

    public void setCouponPrice(String CouponPrice) {
        this.CouponPrice = CouponPrice;
    }

    public String getSJPrice() {
        return SJPrice;
    }

    public void setSJPrice(String SJPrice) {
        this.SJPrice = SJPrice;
    }

    public String getPSPrice() {
        return PSPrice;
    }

    public void setPSPrice(String PSPrice) {
        this.PSPrice = PSPrice;
    }


    public int getIsEvaluate() {
        return IsEvaluate;
    }

    public void setIsEvaluate(int isEvaluate) {
        IsEvaluate = isEvaluate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }


    public List<ShoppingCartM.Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCartM.Goods> goods) {
        this.goods = goods;
    }

    public String getCancelInfo() {
        return CancelInfo;
    }

    public void setCancelInfo(String cancelInfo) {
        CancelInfo = cancelInfo;
    }

    public DeliveryStaffM getDeliveryStaffModel() {
        return DeliveryStaffModel;
    }

    public void setDeliveryStaffModel(DeliveryStaffM deliveryStaffModel) {
        DeliveryStaffModel = deliveryStaffModel;
    }

    public List<OrderGoodM> getChildrenGoods() {
        return ChildrenGoods;
    }

    public void setChildrenGoods(List<OrderGoodM> childrenGoods) {
        ChildrenGoods = childrenGoods;
    }
}

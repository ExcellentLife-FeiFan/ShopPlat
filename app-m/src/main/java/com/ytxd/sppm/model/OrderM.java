package com.ytxd.sppm.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/7/12.
 */

public class OrderM implements Serializable {

    /**
     * OrderCode : 96de242615f141d897150ff1d78005b5
     * OrderStateCode : 0002
     * UserCode : 584b771118054e6ab835aac587b34f92
     * SupermarketCode : 00000001
     * DeliveryStaffCode :
     * SHAddressCode : c190958ea74546aca5db6c20df9247a5
     * Contacts : 刘亮亮
     * Sex : 1
     * Phone : 18312365478
     * Lng : 116.484151
     * Lat : 39.900175
     * AddressTitle : 后现代城B区4号B座
     * AddressDescribe : 北京市朝阳区百子湾路后现代城B区
     * AddressContent : 北京市朝阳区百子湾路后现代城B区
     * SDTime : 2017-07-11 09:58:46
     * Remarks : 无其他要求
     * PayType : 0002
     * PayReturnCode :
     * YPrice : 126.80
     * ManJianCode : 9dc273c2ebdf4f518217a75a7d088625
     * ManJianName : 满100减40
     * MMoney : 100
     * JMoney : 30
     * UserCouponCode : 18
     * CouponPrice :
     * SJPrice : 78.80
     * IsDel : 0
     * CreateTime : 2017-07-11T09:58:48
     * IsEvaluate : 0
     * PSPrice : 5.00
     */

    public static final String WATING_PAY = "0001", HAVE_PAY_WATING_ACE = "0002", FASE_PAY_WATING_ACE = "0003", HAVE_ACE_WATING_SEND = "0004", SENDING = "0005", SUCCESS = "0006", CANCEL = "0007", HAVE_REFUND = "0008";


    private String OrderCode;
    private String OrderStateCode;
    private String UserCode;
    private String SupermarketCode;
    private String DeliveryStaffCode;
    private String SHAddressCode;
    private String Contacts;
    private int Sex;
    private String Phone;
    private String Lng;
    private String Lat;
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
    private String CancelInfo;
    private int TodayNo;
    private int IsDel;
    private String CreateTime;
    private int IsEvaluate;
    private String PSPrice;
    private ComUser UserModel;

    private DeliveryStaffM DeliveryStaffModel;

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

    public String getLng() {
        return Lng;
    }

    public void setLng(String Lng) {
        this.Lng = Lng;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
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

    public String getCancelInfo() {
        return CancelInfo;
    }

    public void setCancelInfo(String cancelInfo) {
        CancelInfo = cancelInfo;
    }

    public int getTodayNo() {
        return TodayNo;
    }

    public void setTodayNo(int todayNo) {
        TodayNo = todayNo;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getIsEvaluate() {
        return IsEvaluate;
    }

    public void setIsEvaluate(int IsEvaluate) {
        this.IsEvaluate = IsEvaluate;
    }

    public String getPSPrice() {
        return PSPrice;
    }

    public void setPSPrice(String PSPrice) {
        this.PSPrice = PSPrice;
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

    @Override
    public boolean equals(Object obj) {
        OrderM n= (OrderM) obj;
        return this.getOrderCode().equals(n.getOrderCode());
    }

    public ComUser getUserModel() {
        return UserModel;
    }

    public void setUserModel(ComUser userModel) {
        UserModel = userModel;
    }
}

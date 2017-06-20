package com.ytxd.spp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/6/19.
 */

public class OrderM implements Serializable {

    /**
     * OrderCode : 868d30e5cf2e4db29f326e7564a1ca5a
     * OrderStateCode : 0001
     * UserCode : 665adfdfa9a445b8b98876f1560ca3d7
     * SupermarketCode : 00000001
     * DeliveryStaffCode : null
     * SHAddressCode : a940d22faa0c497da274d6e42317cf10
     * Contacts : 吴秀聪
     * Sex : 1
     * Phone : 18811389063
     * Lng : 116.484379
     * Lat : 39.900254
     * AddressTitle : 唯我唯时尚杂志社
     * AddressDescribe : 北京市朝阳区后现代城B区4号楼C座A202
     * AddressContent : 北京市朝阳区后现代城B区4号楼C座A202
     * SDTime : 2017-06-19 16:47:51
     * Remarks : 无其他要求
     * PayType : 0002
     * PayReturnCode : null
     * YPrice : 8.00
     * ManJianCode : 0
     * ManJianName :
     * MMoney :
     * JMoney :
     * UserCouponCode : 0
     * CouponPrice :
     * SJPrice : 8.00
     * IsDel : 0
     * CreateTime : 2017-06-19T16:47:57
     * ModifyTime : null
     * SupermarketCode1 : 00000001
     * Name : 测试超市1
     */

    private String OrderCode;
    private String OrderStateCode;
    private String GoodsInfo;
    private String UserCode;
    private String SupermarketCode;
    private Object DeliveryStaffCode;
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
    private Object PayReturnCode;
    private String YPrice;
    private String ManJianCode;
    private String ManJianName;
    private String MMoney;
    private String JMoney;
    private String UserCouponCode;
    private String CouponPrice;
    private String SJPrice;
    private int IsDel;
    private String CreateTime;
    private Object ModifyTime;
    private String SupermarketCode1;
    private String Name;
    private List<ShoppingCartM.Goods> goods;

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

    public Object getDeliveryStaffCode() {
        return DeliveryStaffCode;
    }

    public void setDeliveryStaffCode(Object DeliveryStaffCode) {
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

    public Object getPayReturnCode() {
        return PayReturnCode;
    }

    public void setPayReturnCode(Object PayReturnCode) {
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

    public Object getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Object ModifyTime) {
        this.ModifyTime = ModifyTime;
    }

    public String getSupermarketCode1() {
        return SupermarketCode1;
    }

    public void setSupermarketCode1(String SupermarketCode1) {
        this.SupermarketCode1 = SupermarketCode1;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<ShoppingCartM.Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCartM.Goods> goods) {
        this.goods = goods;
    }
}

package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/6/14.
 */

public class AddressM implements Serializable {

    /**
     * SHAddressCode : 425b9f32153946618921730d6bf41375
     * UserCode : 90bdd099b892456c870576d774c6acc2
     * Contacts : 吴亦凡
     * Sex : 1
     * Phone : 18811389067
     * PhoneCheck : true
     * Lng : 116.484379
     * Lat : 39.900254
     * AddressTitle : 唯我唯时尚杂志社
     * AddressDescribe : 北京市朝阳区后现代城B区4号楼C座A202
     * AddressContent : 北京市朝阳区后现代城B区4号楼C座A202
     * IsDefault : false
     * IsDel : 0
     * CreateTime : 2017-06-14T17:21:59
     */

    private String SHAddressCode;
    private String UserCode;
    private String Contacts;
    private int Sex;
    private String Phone;
    private boolean PhoneCheck;
    private String Lng;
    private String Lat;
    private String AddressTitle;
    private String AddressDescribe;
    private String AddressContent;
    private boolean IsDefault;
    private int IsDel;
    private String CreateTime;

    public String getSHAddressCode() {
        return SHAddressCode;
    }

    public void setSHAddressCode(String SHAddressCode) {
        this.SHAddressCode = SHAddressCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
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

    public boolean isPhoneCheck() {
        return PhoneCheck;
    }

    public void setPhoneCheck(boolean PhoneCheck) {
        this.PhoneCheck = PhoneCheck;
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

    public boolean isIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(boolean IsDefault) {
        this.IsDefault = IsDefault;
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
}

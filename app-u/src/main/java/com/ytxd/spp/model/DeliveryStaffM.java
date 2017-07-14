package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/12.
 */

public class DeliveryStaffM implements Serializable {

    /**
     * DeliveryStaffCode : 1695a84e4af54f39b23be3de596e8e6a
     * SupermarketCode : 00000001
     * DeliveryStaffName : 胡尔
     * NickName :
     * Age : 1
     * TitlePath : /File/UpFiles/11(2)(2).png
     * Email :
     * Phone : 17300000000
     */

    private String DeliveryStaffCode;
    private String SupermarketCode;
    private String DeliveryStaffName;
    private String NickName;
    private int Age;
    private String TitlePath;
    private String Email;
    private String Phone;

    public String getDeliveryStaffCode() {
        return DeliveryStaffCode;
    }

    public void setDeliveryStaffCode(String DeliveryStaffCode) {
        this.DeliveryStaffCode = DeliveryStaffCode;
    }

    public String getSupermarketCode() {
        return SupermarketCode;
    }

    public void setSupermarketCode(String SupermarketCode) {
        this.SupermarketCode = SupermarketCode;
    }

    public String getDeliveryStaffName() {
        return DeliveryStaffName;
    }

    public void setDeliveryStaffName(String DeliveryStaffName) {
        this.DeliveryStaffName = DeliveryStaffName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getTitlePath() {
        return TitlePath;
    }

    public void setTitlePath(String TitlePath) {
        this.TitlePath = TitlePath;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}

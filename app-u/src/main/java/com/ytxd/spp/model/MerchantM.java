package com.ytxd.spp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/6/14.
 */

public class MerchantM implements Serializable {

    /**
     * SupermarketCode : 00000001
     * SupermarketTypeCode : 0001
     * ProvinceCode : 110000
     * ProvinceName : 北京
     * CityCode : 110100
     * CityName : 北京市
     * CountyCode : 110105
     * CountyName : 朝阳区
     * Name : 测试超市1
     * LoginName : 1
     * LoginPwd : 1
     * Lng : 116.491523
     * Lat : 39.900446
     * AddressContent : 随便给你露一手
     * QSPrice : 20
     * PSPrice : 5
     * Confines : 3000
     * PSWhenLong : null
     * KeyWord :
     * LogoUrl : /File/UpFiles/f.jpg
     * Notice : lkekekkekekekeefdsa
     * Contacts : 胡尔
     * Phone : 17301092387
     * HJUrl :
     * BusinessBeginTime : 09:00
     * BusinessEndTime : 17:00
     * YYZZUrl :
     * ShopsOpen : null
     * IsWS : true
     * TSAlias :
     * TSTag :
     * OrderNumber : 0
     * IsDel : null
     * CreateTime : null
     * ModifyTime : null
     * ManJian : [{"ManJianCode":"a95a400aa2fe4b34874dd64f7fa3d896","SupermarketCode":"00000001","ManJianName":"满20减5","MMoney":20,"JMoney":5,"IsUse":1,"IsDel":0,"CreateTime":"2017-06-14T14:36:22"}]
     */

    private String SupermarketCode;
    private String SupermarketTypeCode;
    private String ProvinceCode;
    private String ProvinceName;
    private String CityCode;
    private String CityName;
    private String CountyCode;
    private String CountyName;
    private String Name;
    private String LoginName;
    private String LoginPwd;
    private String Lng;
    private String Lat;
    private String AddressContent;
    private String QSPrice;
    private String PSPrice;
    private String Confines;
    private Object PSWhenLong;
    private String KeyWord;
    private String LogoUrl;
    private String Notice;
    private String Contacts;
    private String Phone;
    private String HJUrl;
    private String BusinessBeginTime;
    private String BusinessEndTime;
    private String YYZZUrl;
    private Object ShopsOpen;
    private boolean IsWS;
    private String TSAlias;
    private String TSTag;
    private String OrderNumber;
    private Object IsDel;
    private Object CreateTime;
    private Object ModifyTime;
    private List<ManJianBean> ManJian;
    private float distance;

    public String getSupermarketCode() {
        return SupermarketCode;
    }

    public void setSupermarketCode(String SupermarketCode) {
        this.SupermarketCode = SupermarketCode;
    }

    public String getSupermarketTypeCode() {
        return SupermarketTypeCode;
    }

    public void setSupermarketTypeCode(String SupermarketTypeCode) {
        this.SupermarketTypeCode = SupermarketTypeCode;
    }

    public String getProvinceCode() {
        return ProvinceCode;
    }

    public void setProvinceCode(String ProvinceCode) {
        this.ProvinceCode = ProvinceCode;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getCountyCode() {
        return CountyCode;
    }

    public void setCountyCode(String CountyCode) {
        this.CountyCode = CountyCode;
    }

    public String getCountyName() {
        return CountyName;
    }

    public void setCountyName(String CountyName) {
        this.CountyName = CountyName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getLoginPwd() {
        return LoginPwd;
    }

    public void setLoginPwd(String LoginPwd) {
        this.LoginPwd = LoginPwd;
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

    public String getAddressContent() {
        return AddressContent;
    }

    public void setAddressContent(String AddressContent) {
        this.AddressContent = AddressContent;
    }

    public String getQSPrice() {
        return QSPrice;
    }

    public void setQSPrice(String QSPrice) {
        this.QSPrice = QSPrice;
    }

    public String getPSPrice() {
        return PSPrice;
    }

    public void setPSPrice(String PSPrice) {
        this.PSPrice = PSPrice;
    }

    public String getConfines() {
        return Confines;
    }

    public void setConfines(String Confines) {
        this.Confines = Confines;
    }

    public Object getPSWhenLong() {
        return PSWhenLong;
    }

    public void setPSWhenLong(Object PSWhenLong) {
        this.PSWhenLong = PSWhenLong;
    }

    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String KeyWord) {
        this.KeyWord = KeyWord;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String LogoUrl) {
        this.LogoUrl = LogoUrl;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String Notice) {
        this.Notice = Notice;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String Contacts) {
        this.Contacts = Contacts;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getHJUrl() {
        return HJUrl;
    }

    public void setHJUrl(String HJUrl) {
        this.HJUrl = HJUrl;
    }

    public String getBusinessBeginTime() {
        return BusinessBeginTime;
    }

    public void setBusinessBeginTime(String BusinessBeginTime) {
        this.BusinessBeginTime = BusinessBeginTime;
    }

    public String getBusinessEndTime() {
        return BusinessEndTime;
    }

    public void setBusinessEndTime(String BusinessEndTime) {
        this.BusinessEndTime = BusinessEndTime;
    }

    public String getYYZZUrl() {
        return YYZZUrl;
    }

    public void setYYZZUrl(String YYZZUrl) {
        this.YYZZUrl = YYZZUrl;
    }

    public Object getShopsOpen() {
        return ShopsOpen;
    }

    public void setShopsOpen(Object ShopsOpen) {
        this.ShopsOpen = ShopsOpen;
    }

    public boolean isIsWS() {
        return IsWS;
    }

    public void setIsWS(boolean IsWS) {
        this.IsWS = IsWS;
    }

    public String getTSAlias() {
        return TSAlias;
    }

    public void setTSAlias(String TSAlias) {
        this.TSAlias = TSAlias;
    }

    public String getTSTag() {
        return TSTag;
    }

    public void setTSTag(String TSTag) {
        this.TSTag = TSTag;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public Object getIsDel() {
        return IsDel;
    }

    public void setIsDel(Object IsDel) {
        this.IsDel = IsDel;
    }

    public Object getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Object CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Object getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Object ModifyTime) {
        this.ModifyTime = ModifyTime;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public List<ManJianBean> getManJian() {
        return ManJian;
    }

    public void setManJian(List<ManJianBean> ManJian) {
        this.ManJian = ManJian;
    }

    public static class ManJianBean implements Serializable{

        public ManJianBean() {
        }

        public ManJianBean(String manJianName) {
            ManJianName = manJianName;
        }

        /**
         * ManJianCode : a95a400aa2fe4b34874dd64f7fa3d896
         * SupermarketCode : 00000001
         * ManJianName : 满20减5
         * MMoney : 20
         * JMoney : 5
         * IsUse : 1
         * IsDel : 0
         * CreateTime : 2017-06-14T14:36:22
         */



        private String ManJianCode;
        private String SupermarketCode;
        private String ManJianName;
        private int MMoney;
        private int JMoney;
        private int IsUse;
        private int IsDel;
        private String CreateTime;

        public String getManJianCode() {
            return ManJianCode;
        }

        public void setManJianCode(String ManJianCode) {
            this.ManJianCode = ManJianCode;
        }

        public String getSupermarketCode() {
            return SupermarketCode;
        }

        public void setSupermarketCode(String SupermarketCode) {
            this.SupermarketCode = SupermarketCode;
        }

        public String getManJianName() {
            return ManJianName;
        }

        public void setManJianName(String ManJianName) {
            this.ManJianName = ManJianName;
        }

        public int getMMoney() {
            return MMoney;
        }

        public void setMMoney(int MMoney) {
            this.MMoney = MMoney;
        }

        public int getJMoney() {
            return JMoney;
        }

        public void setJMoney(int JMoney) {
            this.JMoney = JMoney;
        }

        public int getIsUse() {
            return IsUse;
        }

        public void setIsUse(int IsUse) {
            this.IsUse = IsUse;
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
}

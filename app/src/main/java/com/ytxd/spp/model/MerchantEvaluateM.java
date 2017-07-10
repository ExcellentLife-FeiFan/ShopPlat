package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/10.
 */

public class MerchantEvaluateM implements Serializable {

    /**
     * OrderEvaluateCode : 142b2275223146e195de15a78c27c5e8
     * SupermarketCode : 00000001
     * OrderCode : 129143fbb9264c9986a3295d1f78eff5
     * UserCode : afdffd41249b46bcb71d3e9ca996de63
     * EvaluateContent : 总体还行
     * GoodsStar : 5
     * PSStar : 5
     * PicPath : /UpFiles/2017/7/10/img-c6dedda50c76195464137d47d807e783.jpg,/UpFiles/2017/7/10/img-380f49e16cc416917650168b1f37cbf2.jpg
     * IsDel : 0
     * CreateTime : 2017-07-10T14:47:55
     */

    private String OrderEvaluateCode;
    private String SupermarketCode;
    private String OrderCode;
    private String UserCode;
    private String EvaluateContent;
    private int GoodsStar;
    private int PSStar;
    private String PicPath;
    private int IsDel;
    private String CreateTime;
    private String NickName;
    private String TitlePath;

    public String getOrderEvaluateCode() {
        return OrderEvaluateCode;
    }

    public void setOrderEvaluateCode(String OrderEvaluateCode) {
        this.OrderEvaluateCode = OrderEvaluateCode;
    }

    public String getSupermarketCode() {
        return SupermarketCode;
    }

    public void setSupermarketCode(String SupermarketCode) {
        this.SupermarketCode = SupermarketCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getEvaluateContent() {
        return EvaluateContent;
    }

    public void setEvaluateContent(String EvaluateContent) {
        this.EvaluateContent = EvaluateContent;
    }

    public int getGoodsStar() {
        return GoodsStar;
    }

    public void setGoodsStar(int GoodsStar) {
        this.GoodsStar = GoodsStar;
    }

    public int getPSStar() {
        return PSStar;
    }

    public void setPSStar(int PSStar) {
        this.PSStar = PSStar;
    }

    public String getPicPath() {
        return PicPath;
    }

    public void setPicPath(String PicPath) {
        this.PicPath = PicPath;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getTitlePath() {
        return TitlePath;
    }

    public void setTitlePath(String titlePath) {
        TitlePath = titlePath;
    }
}

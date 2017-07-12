package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/10.
 */

public class GoodEvaluateM implements Serializable {


    /**
     * GoodsEvaluateCode : d2a7d246971048b79300d4b0a959cf96
     * OrderEvaluateCode : 142b2275223146e195de15a78c27c5e8
     * GoodsCode : 3138dedb0b5d4ca1ba4fdf9ff12ee587
     * UserCode : afdffd41249b46bcb71d3e9ca996de63
     * EvaluateContent : 不好
     * StarLevel : 0
     * ZOrC : 2
     * IsDel : 0
     * CreateTime : 2017-07-10T14:47:55
     * NickName :
     * TitlePath :
     */

    private String GoodsEvaluateCode;
    private String OrderEvaluateCode;
    private String GoodsCode;
    private String UserCode;
    private String EvaluateContent;
    private int StarLevel;
    private int ZOrC;
    private int IsDel;
    private String CreateTime;
    private String NickName;
    private String TitlePath;

    public String getGoodsEvaluateCode() {
        return GoodsEvaluateCode;
    }

    public void setGoodsEvaluateCode(String GoodsEvaluateCode) {
        this.GoodsEvaluateCode = GoodsEvaluateCode;
    }

    public String getOrderEvaluateCode() {
        return OrderEvaluateCode;
    }

    public void setOrderEvaluateCode(String OrderEvaluateCode) {
        this.OrderEvaluateCode = OrderEvaluateCode;
    }

    public String getGoodsCode() {
        return GoodsCode;
    }

    public void setGoodsCode(String GoodsCode) {
        this.GoodsCode = GoodsCode;
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

    public int getStarLevel() {
        return StarLevel;
    }

    public void setStarLevel(int StarLevel) {
        this.StarLevel = StarLevel;
    }

    public int getZOrC() {
        return ZOrC;
    }

    public void setZOrC(int ZOrC) {
        this.ZOrC = ZOrC;
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

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getTitlePath() {
        return TitlePath;
    }

    public void setTitlePath(String TitlePath) {
        this.TitlePath = TitlePath;
    }
}

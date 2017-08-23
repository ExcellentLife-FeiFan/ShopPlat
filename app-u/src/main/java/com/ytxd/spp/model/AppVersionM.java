package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/28.
 */

public class AppVersionM implements Serializable {


    /**
     * SoftwareVersionAICode : 1
     * SoftwareVersionAIType : 0001
     * SoftwareVersionAIName : 快点安卓1.0.0
     * SoftwareVersionAINo : 1.0.0
     * UpdateContent : 更新了吴秀聪会飞
     * DownUrl : http://api.zooheng.com:8888/a1_0_1.apk
     * IsQZUpdate : 1
     * BytesSize : 1111
     * IsDel : 0
     * CreateTime : 2017-01-01T00:00:00
     * ModifyTime : 2017-01-01T00:00:00
     */

    private String SoftwareVersionAICode;
    private String SoftwareVersionAIType;
    private String SoftwareVersionAIName;
    private String SoftwareVersionAINo;
    private String UpdateContent;
    private String DownUrl;
    private int IsQZUpdate;
    private String BytesSize;
    private int IsDel;
    private String CreateTime;
    private String ModifyTime;

    public String getSoftwareVersionAICode() {
        return SoftwareVersionAICode;
    }

    public void setSoftwareVersionAICode(String SoftwareVersionAICode) {
        this.SoftwareVersionAICode = SoftwareVersionAICode;
    }

    public String getSoftwareVersionAIType() {
        return SoftwareVersionAIType;
    }

    public void setSoftwareVersionAIType(String SoftwareVersionAIType) {
        this.SoftwareVersionAIType = SoftwareVersionAIType;
    }

    public String getSoftwareVersionAIName() {
        return SoftwareVersionAIName;
    }

    public void setSoftwareVersionAIName(String SoftwareVersionAIName) {
        this.SoftwareVersionAIName = SoftwareVersionAIName;
    }

    public String getSoftwareVersionAINo() {
        return SoftwareVersionAINo;
    }

    public void setSoftwareVersionAINo(String SoftwareVersionAINo) {
        this.SoftwareVersionAINo = SoftwareVersionAINo;
    }

    public String getUpdateContent() {
        return UpdateContent;
    }

    public void setUpdateContent(String UpdateContent) {
        this.UpdateContent = UpdateContent;
    }

    public String getDownUrl() {
        return DownUrl;
    }

    public void setDownUrl(String DownUrl) {
        this.DownUrl = DownUrl;
    }

    public int getIsQZUpdate() {
        return IsQZUpdate;
    }

    public void setIsQZUpdate(int IsQZUpdate) {
        this.IsQZUpdate = IsQZUpdate;
    }

    public String getBytesSize() {
        return BytesSize;
    }

    public void setBytesSize(String BytesSize) {
        this.BytesSize = BytesSize;
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

    public String getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(String ModifyTime) {
        this.ModifyTime = ModifyTime;
    }
}

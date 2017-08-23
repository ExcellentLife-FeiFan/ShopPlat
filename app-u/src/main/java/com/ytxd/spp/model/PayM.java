package com.ytxd.spp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/26.
 */

public class PayM implements Serializable {

    /**
     * PayType : 0002
     * Wx_Result : {"appid":"wx69aca0bd92b5dca3","partnerid":"1486477482","prepayid":"wx20170801102516cfa2fe4d990861857931","package":"Sign=WXPay","noncestr":"651907AB32C24A6BB0C74BD7115E332F","timestamp":"1501554317","sign":"B0BC6090EB89AFAF621BF30CB56BFB12"}
     */

    private String PayStr;

    private String OrderCode;


    private String PayType;

    private WxResultBean Wx_Result;

    public String getPayStr() {
        return PayStr;
    }

    public void setPayStr(String payStr) {
        PayStr = payStr;
    }



    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String PayType) {
        this.PayType = PayType;
    }

    public WxResultBean getWx_Result() {
        return Wx_Result;
    }

    public void setWx_Result(WxResultBean Wx_Result) {
        this.Wx_Result = Wx_Result;
    }

    public static class WxResultBean {
        /**
         * appid : wx69aca0bd92b5dca3
         * partnerid : 1486477482
         * prepayid : wx20170801102516cfa2fe4d990861857931
         * package : Sign=WXPay
         * noncestr : 651907AB32C24A6BB0C74BD7115E332F
         * timestamp : 1501554317
         * sign : B0BC6090EB89AFAF621BF30CB56BFB12
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

package com.ytxd.spp.net;

import java.io.Serializable;

/**
 * api请求返回信息
 */
public class ApiResult2<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int State;
    private String Msg = "操作成功";

    public boolean isSuccess() {
        return this.State == 200;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

}

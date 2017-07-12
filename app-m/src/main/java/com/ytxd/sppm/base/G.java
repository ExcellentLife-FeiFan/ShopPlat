package com.ytxd.sppm.base;


import com.ytxd.sppm.util.SdCardUtil;

/**
 * 全局变量存储类
 *
 * @author ForeverHt
 */
public class G {

    /**
     * 应用程序名
     */
    public static final String APPNAME = "KuaiDian-M";

    /**
     * 文件根目录
     */
    public static final String STORAGEPATH = SdCardUtil.getNormalSDCardPath() + "/" + APPNAME + "/";


    public static final int CONNECT_TIME_OUT = 5 * 1000;

}

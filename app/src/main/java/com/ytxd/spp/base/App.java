package com.ytxd.spp.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.litesuits.orm.LiteOrm;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.SPUtil;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import butterknife.BuildConfig;
import butterknife.ButterKnife;

import static com.ytxd.spp.base.G.CONNECT_TIME_OUT;

/**
 * Created by XY on 2017/5/27.
 */

public class App extends Application{

    public static Context context;
    private static LiteOrm liteOrm;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(BuildConfig.DEBUG);//设置ButterKnife调试模式
        context = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initPrefs();
        initDirs();//初始化APP文件夹
        initOkGo();//初始化OkGo网络请求工具
        initRecovery();
    }

    private void initRecovery() {
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new MyCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
//                .skip(TestActivity.class)
                .init(this);
    }
    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Log.e("zxy", "exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Log.e("zxy", "cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Log.e("zxy", "exceptionClassName:" + exceptionType);
            Log.e("zxy", "throwClassName:" + throwClassName);
            Log.e("zxy", "throwMethodName:" + throwMethodName);
            Log.e("zxy", "throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }

    private void initOkGo() {
        OkGo.init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(CONNECT_TIME_OUT)  //全局的连接超时时间
                    .setReadTimeOut(CONNECT_TIME_OUT)     //全局的读取超时时间
                    .setWriteTimeOut(CONNECT_TIME_OUT)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(1000 * 60 * 60 * 2)
                    //如果不想让框架管理cookie,以下不需要
                    .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore());         //cookie持久化存储，如果cookie不过期，则一直有效
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SPUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    public static void initDataBase(Context context) {
/*        liteOrm = LiteOrm.newSingleInstance(context, CommonUtils.getUserCachePath() + "china_cities.db");
        liteOrm.setDebugged(true); // open the log*/
    }


    private void initDirs() {
      /*  File path1 = new File(G.STORAGEPATH);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File path2 = new File(CommonUtils.getUserCachePath());
        if (!path2.exists()) {
            path2.mkdirs();
        }*/
    }
}

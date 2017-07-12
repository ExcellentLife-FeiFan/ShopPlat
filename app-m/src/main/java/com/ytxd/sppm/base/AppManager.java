package com.ytxd.sppm.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AppManager {

    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity(堆栈中最后一个压人的)
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity(堆栈中最后一个压入的)
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     *
     * @param activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                activity.finishAfterTransition();
            }else{
                activity.finish();
            }
//            activity.overridePendingTransition(R.anim.in_fade,R.anim.out_fade);
            activity = null;
        }
    }
    /**
     * 结束指定类名的Activity
     *
     * @param activity
     */
    public void killActivityNotRemove(Activity activity) {
        if (activity != null) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                activity.finishAfterTransition();
            }else{
                activity.finish();
            }
//            activity.overridePendingTransition(R.anim.in_fade,R.anim.out_fade);
            activity = null;
        }
    }

    /**
     * 结束Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (mActivityStack.get(i) != null) {
                mActivityStack.get(i).finish();
            } else {
                continue;
            }
        }
        mActivityStack.clear();
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity != null) {
                if (activity.getClass().equals(cls)) {
                    killActivity(activity);
                    return;
                }
            } else {
                continue;
            }

        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killAllActivityButThis(Class<?> cls) {
        List<Activity> removeAcs = new ArrayList<>();
        for (Activity activity : mActivityStack) {
            if (activity != null) {
                if (activity.getClass().equals(cls)) {
                    continue;
                }else{
                    killActivityNotRemove(activity);
                    removeAcs.add(activity);
                }
            } else {
                killActivityNotRemove(activity);
            }

        }
        for (int i = 0; i < removeAcs.size(); i++) {
            mActivityStack.remove(removeAcs.get(i));
        }
    }


    /**
     * 退出应用程序
     *
     * @param context
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}









package com.ytxd.sppm.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ytxd.sppm.R;


/**
 * Created by Administrator on 2016/9/19 0019.
 */

public class DialogUtils {
    private static MaterialDialog myDialog;

    private static DialogInterface.OnDismissListener myDissmissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (null != myDialog) {
                myDialog = null;
            }
        }
    };

    public static void dismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
            myDialog = null;
        }

    }

    public static void showDialog(Activity activity, String title) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title("提示")
                    .content(title)
                    .canceledOnTouchOutside(false)
                    .progress(true, 100)
                    .progressIndeterminateStyle(false).show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

    public static void showDialog(Activity activity) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title("提示")
                    .content("正在加载......")
                    .canceledOnTouchOutside(false)
                    .progress(true, 100)
                    .progressIndeterminateStyle(false).show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

    public static void showDialog(Activity activity, String title, String content, String ok, String cancel, MaterialDialog.SingleButtonCallback callback) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(content)
                    .canceledOnTouchOutside(false)
                    .positiveText(ok)
                    .negativeText(cancel)
                    .positiveColor(activity.getResources().getColor(R.color.red))
                    .negativeColor(activity.getResources().getColor(R.color.gray))
                    .onAny(callback)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

    public static void showDialog(Activity activity, String title, String content, MaterialDialog.SingleButtonCallback callback) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(content)
                    .canceledOnTouchOutside(false)
                    .positiveText("确定")
                    .negativeText("取消")
                    .positiveColor(activity.getResources().getColor(R.color.red))
                    .negativeColor(activity.getResources().getColor(R.color.gray))
                    .onAny(callback)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }
    public static void showDialog(Context context, String title, String content, MaterialDialog.SingleButtonCallback callback) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(context)
                    .title(title)
                    .content(content)
                    .canceledOnTouchOutside(false)
                    .positiveText("确定")
                    .negativeText("取消")
                    .positiveColor(context.getResources().getColor(R.color.red))
                    .negativeColor(context.getResources().getColor(R.color.gray))
                    .onAny(callback)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }


    public static MaterialDialog showProDialog(Activity activity, String title/*, DialogInterface.OnShowListener showListener*/) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title("提示")
                    .content(title)
                    .canceledOnTouchOutside(false)
                    .contentGravity(GravityEnum.CENTER)
                    .progress(false, 100, true)
//                    .showListener(showListener)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return myDialog;
        }
        myDialog.show();
        return myDialog;

    }


    public static void showDialog(Context context, String title, int arraies, MaterialDialog.ListCallback callback, boolean canceled) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(context)
                    .title(title)
                    .items(arraies)
                    .canceledOnTouchOutside(canceled)
                    .itemsCallback(callback)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }
    public static void showDialog(Context context, String title, int arraies, int index, MaterialDialog.ListCallbackSingleChoice callback, boolean canceled) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(context)
                    .title(title)
                    .items(arraies)
                    .canceledOnTouchOutside(canceled)
                    .itemsCallbackSingleChoice(index,callback)
                    .positiveText("确定")
                    .negativeText("取消")
                    .positiveColor(context.getResources().getColor(R.color.red))
                    .negativeColor(context.getResources().getColor(R.color.gray))
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

    public static void showInputDialog(Context activity, String title, String content, int inputType, int hint, int minLenth, int maxLenth,MaterialDialog.InputCallback callback) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(content)
                    .inputType(inputType)
                    .inputRange(minLenth,maxLenth)
                    .positiveText("确定")
                    .positiveColor(activity.getResources().getColor(R.color.colorPrimary))
                    .input(hint, R.string.null_s2, false, callback)
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

    public static void showDialog(Activity activity, String title, String content) {
        if (myDialog == null) {
            myDialog = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(content)
                    .canceledOnTouchOutside(false)
                    .positiveText("确定")
                    .positiveColor(activity.getResources().getColor(R.color.red))
                    .show();
            myDialog.setOnDismissListener(myDissmissListener);
            return;
        }
        myDialog.show();
    }

}

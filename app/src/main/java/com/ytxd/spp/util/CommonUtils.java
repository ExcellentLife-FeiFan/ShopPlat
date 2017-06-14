package com.ytxd.spp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.ui.views.loadview.CustomDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ytxd.spp.base.App.user;


/**
 * Created by XY on 2016/11/20.
 */

public class CommonUtils {

    private static CustomDialog dialog;

    public static boolean isLogined(final Activity context) {
        if (null != null) {
            return true;
        } else {
//            context.startActivity(new Intent(context, LoginActivity.class));
     /*       DialogUtils.showDialog(context, "您还没有登录哦！", "", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which.name().equals(DialogAction.POSITIVE.name())) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                        AppManager.getInstance().killActivity(context);
                    }

                }
            });*/
            return false;
        }
    }

    public static boolean isLogined2() {
        if (null != user) {
            return true;
        } else {
            return false;
        }
    }

    public static void setEmptyView(Activity context, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        context.addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyView(Context context, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) (listView.getRootView())).addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForFragment(Activity context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForSLV(Activity context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_lv_common_empty, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyView(View emptyView, AbsListView listView) {
        emptyView.setVisibility(View.GONE);
//        ((ViewGroup) listView.getParent()).addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static String getSexStrigByIndex(String index) {
        String sexS = "男";
        switch (index) {
            case "0":
                sexS = "女";
                break;
            case "1":
                sexS = "男";
                break;
        }
        return sexS;

    }

    public static String getSexIndexByString(String string) {
        String sexI = "1";
        switch (string) {
            case "女":
                sexI = "0";
                break;
            case "男":
                sexI = "1";
                break;
        }
        return sexI;
    }


    public static int getColor(Context context, int res) {
        return context.getResources().getColor(res);
    }

    public static Drawable getDrawable(Context context, int res) {
        return context.getResources().getDrawable(res);
    }


/*    public static String getUserCachePath() {
        if (null != userBean) {
            return G.STORAGEPATH + userBean.getUserName() + "/";
        } else {
            return G.STORAGEPATH;
        }
    }

    public static boolean isDBInit(Context context) {
        if (null == liteOrm) {
            ToastUtil.showToastShort(context, "本地数据库未初始化成功");
            return false;
        } else {
            return true;
        }
    }*/

    public static String getFloatString2(float f) {
        return new DecimalFormat("0.00").format(f);
    }


    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return r + g + b;
    }

    /**
     * 获取登录Token
     */
    public static String getToken() {
        return SPUtil.getInstance().getString("token", "");
    }

    /**
     * 设置登录Token
     *
     * @param token
     */
    public static void setToken(String token) {
        SPUtil.getInstance().putString("token", token);
    }


    public static void setText(TextView tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    public static void setText(EditText tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    public static void setBackgroundDrawable(View v, int res) {
        v.setBackgroundDrawable(App.context.getResources().getDrawable(res));
    }

    public static void setTextColor(TextView v, int res) {
        v.setTextColor(App.context.getResources().getColor(res));
    }

    public static void setImageDrawable(ImageView v, int res) {
        v.setImageDrawable(App.context.getResources().getDrawable(res));
    }


    public static <T> List<T> getRandomNumberItemFromList(List<T> list, int num) {
        if (list.size() <= num) {
            return list;
        }
        List<T> listR = new ArrayList<>();
        List<T> list2 = new ArrayList<>();
        list2.addAll(list);
        for (int i = 0; i < num; i++) {
            int r = new Random().nextInt(list2.size());
            if (null != list2.get(r)) {
                listR.add(list2.get(r));
                list2.remove(r);
            }
        }
        return listR;
    }

    // dialog
    public static CustomDialog getDialog(Context context) {
        if (dialog == null) {
            dialog = CustomDialog.instance(context);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public static void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public static void showDialog(Context context) {
        getDialog(context).show();
    }

    public static void setEtClearListener(final EditText et, final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AbStrUtil.isEmpty(et.getText().toString())) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static List<String> getSampleList(int count) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add("");
        }
        return items;
    }
}

package com.ytxd.sppm.util;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.G;
import com.ytxd.sppm.model.OrderGoodM;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.ui.views.loadview.CustomDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ytxd.sppm.base.App.context;
import static com.ytxd.sppm.util.PrintUtils.MEAL_NAME_MAX_LENGTH;


/**
 * Created by XY on 2016/11/20.
 */

public class CommonUtils {

    public static final int REFRESH = 1;
    public static final int LODEMORE = 2;
    public static final String UN_PAY = "0001";
    public static final String HAVE_PAY = "0002";
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
        if (null != App.user) {
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

    public static void setEmptyViewForFragment(Context context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForSLV(Context context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_lv_common_empty, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForSLV(View empty, AbsListView listView) {
        empty.setVisibility(View.GONE);
        listView.setEmptyView(empty);
    }

    public static void setEmptyViewForSLV(Context context, String txt, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_lv_common_empty, null);
        ((TextView) emptyView.findViewById(R.id.tv_empty)).setText(txt);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }


    public static void setEmptyView(View emptyView, AbsListView listView) {
        emptyView.setVisibility(View.GONE);
//        ((ViewGroup) listView.getParent()).addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }


    public static int getColor(Context context, int res) {
        return context.getResources().getColor(res);
    }

    public static String getString(int res) {
        return context.getResources().getString(res);
    }

    public static Drawable getDrawable(Context context, int res) {
        return context.getResources().getDrawable(res);
    }

    public static Drawable getTextDrawable(int res) {
        Drawable drawable = context.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }


    public static String getUserCachePath() {
        if (null != App.user) {
            return G.STORAGEPATH + App.user.getSupermarketCode() + "/";
        } else {
            return G.STORAGEPATH;
        }
    }

    public static boolean isDBInit(Context context) {
        if (null == App.liteOrm) {
            ToastUtil.showToastShort(context, "本地数据库未初始化成功");
            return false;
        } else {
            return true;
        }
    }

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
        v.setBackgroundDrawable(context.getResources().getDrawable(res));
    }

    public static void setBackgroundDrawable(Context context, View v, int res) {
        v.setBackgroundDrawable(context.getResources().getDrawable(res));
    }

    public static void setTextColor(TextView v, int res) {
        v.setTextColor(context.getResources().getColor(res));
    }

    public static void setImageDrawable(ImageView v, int res) {
        v.setImageDrawable(context.getResources().getDrawable(res));
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

    public static String getFormatTimeString(String string) {

        return string.replace("T", " ");
    }

    public static List<String> getStringList(String[] strings) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    public static boolean getBoolean(int b) {
        return b == 1 ? true : false;
    }


    public static void printOrder(BluetoothDevice device, Context context, OrderM orderM) {
        try {
            ToastUtil.showToastShort(context, "开始打印订单");
            BluetoothSocket bluetoothSocket = device.createRfcommSocketToServiceRecord(PrintUtils.uuid);
            bluetoothSocket.connect();
            PrintUtils.setOutputStream(bluetoothSocket.getOutputStream());
            PrintUtils.selectCommand(PrintUtils.RESET);
            PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
            PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
            PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
            PrintUtils.printText(App.user.getName() + "\n\n");
            PrintUtils.selectCommand(PrintUtils.NORMAL);
            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText(PrintUtils.printTwoData("编号:", orderM.getOrderCode() + "\n"));
            PrintUtils.printText("\n");
            PrintUtils.printText(PrintUtils.printTwoData("下单时间:", AbDateUtil.getStringByFormat(CommonUtils.getFormatTimeString(orderM.getCreateTime()), AbDateUtil.dateFormatYMDHM) + "\n"));
            PrintUtils.printText(PrintUtils.printTwoData("期望送达时间:", AbDateUtil.getStringByFormat(CommonUtils.getFormatTimeString(orderM.getSDTime()), AbDateUtil.dateFormatYMDHM) + "\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD);
            PrintUtils.printText(PrintUtils.printThreeData("名称", "数量", "金额\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
            try {
                for (int i = 0; i < orderM.getChildrenGoods().size(); i++) {
                    OrderGoodM goodM = orderM.getChildrenGoods().get(i);
                    if(goodM.getGoodsTitle().length()>MEAL_NAME_MAX_LENGTH){
                        int c10=goodM.getGoodsTitle().length()/MEAL_NAME_MAX_LENGTH;
                        for (int j = 0; j < c10; j++) {
                            if(j==0){
                                PrintUtils.printText(PrintUtils.printThreeData(goodM.getGoodsTitle().substring(0,MEAL_NAME_MAX_LENGTH), goodM.getBuyNumber() + "", goodM.getXPrice() + "\n"));
                            }else{
                                PrintUtils.printText(goodM.getGoodsTitle().substring(j*MEAL_NAME_MAX_LENGTH,(j+1)*MEAL_NAME_MAX_LENGTH)+"\n");
                            }
                        }
                        if(goodM.getGoodsTitle().length()>MEAL_NAME_MAX_LENGTH*c10){
                            PrintUtils.printText(goodM.getGoodsTitle().substring(c10*MEAL_NAME_MAX_LENGTH,goodM.getGoodsTitle().length())+"\n");
                        }
                    }else{
                        PrintUtils.printText(PrintUtils.printThreeData(goodM.getGoodsTitle(), goodM.getBuyNumber() + "", goodM.getXPrice() + "\n"));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            PrintUtils.printText(PrintUtils.printTwoData("配送费", orderM.getPSPrice() + "\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("合计", orderM.getYPrice() + "\n"));
            if (!AbStrUtil.isEmpty(orderM.getJMoney())) {
                PrintUtils.printText(PrintUtils.printTwoData("满减", "-" + CommonUtils.getFloatString2(Float.valueOf(orderM.getJMoney())) + "\n"));
            }
            if (!AbStrUtil.isEmpty(orderM.getCouponPrice())) {
                PrintUtils.printText(PrintUtils.printTwoData("优惠券", "-" + CommonUtils.getFloatString2(Float.valueOf(orderM.getCouponPrice())) + "\n"));
            }
//            PrintUtils.printText(PrintUtils.printTwoData("优惠", CommonUtils.getFloatString2(Float.valueOf(orderM.getYPrice()) - Float.valueOf(orderM.getSJPrice())) + "\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText(PrintUtils.printTwoData("实收", orderM.getSJPrice() + "\n"));
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
            PrintUtils.printText("姓名：" + orderM.getContacts() + "   " + "电话:" + orderM.getPhone() + "\n");
            PrintUtils.printText("地址：" + orderM.getAddressTitle() + "-" + orderM.getAddressContent() + "\n");
            PrintUtils.printText("--------------------------------\n");
            PrintUtils.printText("备注：" + orderM.getRemarks());
            PrintUtils.printText("\n\n\n\n");
            bluetoothSocket.close();
            ToastUtil.showToastShort(context, "打印的订单成功");
        } catch (Exception e) {
            ToastUtil.showToastShort(context, "该设备不是打印机或连接失败");
            e.printStackTrace();
        }

    }


}

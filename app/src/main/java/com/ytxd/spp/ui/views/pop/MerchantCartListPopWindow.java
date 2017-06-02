package com.ytxd.spp.ui.views.pop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ytxd.spp.R;


public class MerchantCartListPopWindow extends PopupWindow implements View.OnClickListener {


    private View mMenuView;

    public MerchantCartListPopWindow(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_merchant_cart_list, null);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Pop);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(CommonUtils.getColor(context,R.color.red));
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(CommonUtils.getDrawable(context,R.color.red));
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
        switch (view.getId()) {
         /*   case R.id.rl_share_weibo:
                break;
            case R.id.rl_share_qq:
                break;
            case R.id.rl_share_qqzone:
                break;
            case R.id.rl_share_wechat:
                break;*/
        }
    }
}

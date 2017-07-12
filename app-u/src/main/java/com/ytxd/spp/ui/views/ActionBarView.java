package com.ytxd.spp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.util.AbStrUtil;


/**
 * 动作导航条
 *
 * @author yuanc
 */
public class ActionBarView extends LinearLayout {

    private ImageView iv_left, iv_right;
    private TextView tv_left, tv_title, tv_right;
    private RelativeLayout rl_back, rl_v_right;

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.actionbar_layout, this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_v_right = (RelativeLayout) findViewById(R.id.rl_v_right);
        tv_left = (TextView) findViewById(R.id.tv_left);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);

    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, OnClickListener listener) {
        tv_title.setText(title);
        if (null != listener) {
            rl_back.setOnClickListener(listener);
            tv_title.setOnClickListener(listener);
        }
    }



    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, String leftRes, OnClickListener listener) {
        if (!AbStrUtil.isEmpty(leftRes)) {
            tv_left.setVisibility(VISIBLE);
            rl_back.setVisibility(GONE);
            tv_left.setText(leftRes);
        }

        if (null != listener) {
            tv_title.setOnClickListener(listener);
            if (!AbStrUtil.isEmpty(leftRes)) {
                tv_left.setOnClickListener(listener);
            }
        }
    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, String leftRes, String rightRes, OnClickListener listener) {
        if (!AbStrUtil.isEmpty(leftRes)) {
            tv_left.setVisibility(VISIBLE);
            rl_back.setVisibility(GONE);
            tv_left.setText(leftRes);
        }
        if (!AbStrUtil.isEmpty(rightRes)) {
            tv_right.setVisibility(VISIBLE);
            tv_right.setText(rightRes);
        }

        if (null != listener) {
            tv_title.setOnClickListener(listener);
            if (!AbStrUtil.isEmpty(leftRes)) {
                tv_left.setOnClickListener(listener);
            }
            if (!AbStrUtil.isEmpty(rightRes)) {
                tv_right.setOnClickListener(listener);
            }
        }
    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, int leftRes, int rightRes, OnClickListener listener) {

        tv_title.setText(title);

        if (leftRes > 0) {
            rl_back.setVisibility(VISIBLE);
            iv_left.setImageResource(leftRes);
        } else {
            rl_back.setVisibility(GONE);
        }

        if (rightRes > 0) {
            rl_v_right.setVisibility(VISIBLE);
            iv_right.setImageResource(rightRes);
        } else {
            rl_v_right.setVisibility(GONE);
        }

        if (null != listener) {
            tv_title.setOnClickListener(listener);
            rl_back.setOnClickListener(listener);
            rl_v_right.setOnClickListener(listener);
        }
    }

    /**
     * 初始化ActionBar
     */
    public void initActionBar(String title, String leftT, String rightT, int leftRes, int rightRes, OnClickListener listener) {

        tv_left.setVisibility(VISIBLE);
        tv_right.setVisibility(VISIBLE);
        tv_left.setText(leftT);
        tv_right.setText(rightT);
        tv_title.setText(title);

        if(AbStrUtil.isEmpty(leftT)){
            tv_left.setVisibility(GONE);
        }

        if(AbStrUtil.isEmpty(rightT)){
            tv_right.setVisibility(GONE);
        }

        if (leftRes > 0) {
            rl_back.setVisibility(VISIBLE);
            iv_left.setImageResource(leftRes);
        } else {
            rl_back.setVisibility(GONE);
        }

        if (rightRes > 0) {
            rl_v_right.setVisibility(VISIBLE);
            iv_right.setImageResource(rightRes);
        } else {
            rl_v_right.setVisibility(GONE);
        }


        if (null != listener) {
            tv_title.setOnClickListener(listener);
            rl_back.setOnClickListener(listener);
            tv_left.setOnClickListener(listener);
            tv_right.setOnClickListener(listener);
            rl_v_right.setOnClickListener(listener);
        }
    }

}
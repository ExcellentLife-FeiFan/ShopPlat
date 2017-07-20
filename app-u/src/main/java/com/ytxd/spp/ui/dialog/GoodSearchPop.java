package com.ytxd.spp.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.activity.main.GoodDetailActivity;
import com.ytxd.spp.ui.adapter.GoodSearchLV;
import com.ytxd.spp.util.AbStrUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Excellent on 2016/4/22.
 */
public class GoodSearchPop extends PopupWindow {

    private Activity context;
    public ListView lv;
    private EditText et;
    MerchantM merchant;

    public GoodSearchPop(Activity context, MerchantM merchant) {
        this.context = context;
        this.merchant = merchant;
        initView();
    }

    private void initView() {
        //设置可以获得焦点
        setFocusable(true);
        //设置弹窗内可点击
        setTouchable(true);
        //设置弹窗外可点击
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.PopupAnimation);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_book_search, null);
        lv = (ListView) contentView.findViewById(R.id.lv);
        et = (EditText) contentView.findViewById(R.id.et);
        final GoodSearchLV adapter = new GoodSearchLV(new ArrayList<GoodM>(), context, merchant.getSupermarketCode());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent itent = new Intent(context, GoodDetailActivity.class);
                itent.putExtra("data", adapter.getItem(i));
                itent.putExtra("merchantCode", merchant.getSupermarketCode());
                itent.putExtra("merchant", merchant);
                context.startActivity(itent);
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
                String c = s.toString();
                if (!AbStrUtil.isEmpty(c)) {
                    QueryBuilder queryBuilder = new QueryBuilder<GoodM>(GoodM.class)
                            .where("GoodsTitle" + " LIKE ?", new String[]{"%" + c + "%"});
                    List<GoodM> items = App.liteOrm.query(queryBuilder);
                    adapter.addItems(items, true);
                } else {
                    adapter.clearItems();
                }

            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if(null!=adapter){
                    adapter.clearItems();
                    et.setText("");
                }
            }
        });

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(contentView);
    }


}

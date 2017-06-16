package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.ytxd.spp.R;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.event.MerchantSelectGoodStandEvent;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.activity.main.GoodDetailActivity;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ShoppingCartUtil;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by apple on 2017/6/1.
 */

public class MerchantGoodA extends SectioningAdapter {

    private Context mContext;
    private MerchantM merchantM;

    ArrayList<CatagaryM> items = new ArrayList<>();

    public MerchantGoodA(Context context, MerchantM merchantM) {
        mContext = context;
        this.merchantM = merchantM;
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        public View rootView;
        public ImageView ivPlus, iv;
        public TextView tvOriginP, tvNowP, tv_name, tv_select_stand;
        public AnimShopButton btnAdd;
        public RelativeLayout rl_add_btn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tvOriginP = (TextView) rootView.findViewById(R.id.tv_origin_p);
            this.tvNowP = (TextView) rootView.findViewById(R.id.tv_now_p);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.btnAdd = (AnimShopButton) rootView.findViewById(R.id.btnAdd);
            this.ivPlus = (ImageView) rootView.findViewById(R.id.iv_plus);
            this.iv = (ImageView) rootView.findViewById(R.id.iv);
            this.rl_add_btn = (RelativeLayout) rootView.findViewById(R.id.rl_add_btn);
            this.tv_select_stand = (TextView) rootView.findViewById(R.id.tv_select_stand);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        public View rootView;
        public TextView tvCatagory;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tvCatagory = (TextView) rootView.findViewById(R.id.tv_catagory);
        }

    }

    @Override
    public int getNumberOfSections() {
        return items.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return items.get(sectionIndex).getChildren().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_mer_good, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_merchant_good_hader, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, final int sectionIndex, final int itemIndex, int itemType) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;
        GoodM good = items.get(sectionIndex).getChildren().get(itemIndex);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(mContext, GoodDetailActivity.class);
                itent.putExtra("data", items.get(sectionIndex).getChildren().get(itemIndex));
                itent.putExtra("merchantCode", merchantM.getSupermarketCode());
                itent.putExtra("merchant", merchantM);
                mContext.startActivity(itent);
            }
        });
        CommonUtils.setText(holder.tv_name, good.getGoodsTitle());
        CommonUtils.setText(holder.tvOriginP, good.getYPrice());
        CommonUtils.setText(holder.tvNowP, good.getXPrice());
        ImageLoadUtil.setImageNP(good.getLogoPaths(), holder.iv, mContext);
        if (null != good.getGoods() && good.getGoods().size() > 0) {
            holder.rl_add_btn.setVisibility(View.GONE);
            holder.tv_select_stand.setVisibility(View.VISIBLE);
        } else {
            holder.rl_add_btn.setVisibility(View.VISIBLE);
            holder.tv_select_stand.setVisibility(View.GONE);
        }
        holder.btnAdd.setCount(ShoppingCartUtil.getLocalCartGoodCount(good.getGoodsCode(), merchantM.getSupermarketCode()));
        holder.btnAdd.invalidate();
        holder.btnAdd.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new GoodAddEvent(holder.ivPlus, items.get(sectionIndex).getChildren().get(itemIndex),1));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                EventBus.getDefault().post(new GoodMinusEvent(items.get(sectionIndex).getChildren().get(itemIndex),1));
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
        holder.tv_select_stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MerchantSelectGoodStandEvent(items.get(sectionIndex).getChildren().get(itemIndex)));
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        LogUtils.e(sectionIndex + "");
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        holder.tvCatagory.setText(items.get(sectionIndex).getGoodsTypeName());
//        items.get(sectionIndex).setAdapterP(viewHolder.getAdapterPosition());
        LogUtils.e(holder.getAdapterPosition() + "");
    }

    public void addAll(List<CatagaryM> items) {
        this.items.addAll(items);
        notifyAllSectionsDataSetChanged();
    }

    public CatagaryM getSection(int section) {
        if (section >= 0 && section <= this.items.size()) {
            return items.get(section);
        } else {
            return null;
        }
    }

}

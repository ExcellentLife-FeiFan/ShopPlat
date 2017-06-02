package com.ytxd.spp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.ytxd.spp.R;
import com.ytxd.spp.event.MerchantGoodAddEvent;
import com.ytxd.spp.model.MerhchantGoodCategoryM;
import com.ytxd.spp.util.LogUtils;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by apple on 2017/6/1.
 */

public class MerchantGoodA extends SectioningAdapter {


    ArrayList<MerhchantGoodCategoryM> items = new ArrayList<>();

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        public View rootView;
        public ImageView ivPlus;
        public TextView tvOriginP, tv_name;
        public AnimShopButton btnAdd;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tvOriginP = (TextView) rootView.findViewById(R.id.tv_origin_p);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.btnAdd = (AnimShopButton) rootView.findViewById(R.id.btnAdd);
            this.ivPlus= (ImageView) rootView.findViewById(R.id.iv_plus);
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
        return items.get(sectionIndex).getGoods().size();
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
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;
        holder.tv_name.setText(items.get(sectionIndex).getGoods().get(itemIndex).getName());
        holder.btnAdd.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new MerchantGoodAddEvent(holder.ivPlus));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {

            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        LogUtils.e(sectionIndex + "");
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        holder.tvCatagory.setText(items.get(sectionIndex).getName());
//        items.get(sectionIndex).setAdapterP(viewHolder.getAdapterPosition());
        LogUtils.e(holder.getAdapterPosition()+"");
    }

    public void addAll(List<MerhchantGoodCategoryM> items) {
        this.items.addAll(items);
        notifyAllSectionsDataSetChanged();
    }
    public MerhchantGoodCategoryM getSection(int section){
        if(section >= 0 && section <= this.items.size()) {
            return items.get(section);
        } else {
            return null;
        }
    }

}

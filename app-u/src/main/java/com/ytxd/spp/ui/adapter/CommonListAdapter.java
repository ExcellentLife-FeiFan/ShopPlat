package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ytxd.spp.presenter.AddressManaPresenter;

import java.util.List;

/**
 * Created by Excellent on 2016/4/29.
 */
public class CommonListAdapter<T> extends BaseAdapter {

    protected List<T> items;
    protected Context context;
    protected Activity activity;
    protected LayoutInflater inflater;
    protected  T item;

    public CommonListAdapter(List<T> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public CommonListAdapter(List<T> items, Activity activity) {
        this.items = items;
        this.activity = activity;
        this.context = activity;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item = getItem(position);
        return null;
    }

    public void addItems(List<T> items, boolean isRefresh) {
        if (items != null) {
            if (isRefresh) {
                this.items.clear();
                this.items.addAll(items);
            } else {
                this.items.addAll(items);
            }
            notifyDataSetChanged();
        }
    }

    public void addItem(T items) {
        if (items != null) {
            this.items.add(items);
            notifyDataSetChanged();
        }
    }
    public void clearItems(){
        items.clear();
        notifyDataSetChanged();
    }

    public void updateItem(T item,int position){
        if(position<=(this.items.size()-1)) {
            this.items.set(position, item);
            notifyDataSetChanged();
        }
    }
    public void removeItem(int position){
        if(position<=(this.items.size()-1)) {
            this.items.remove(position);
            notifyDataSetChanged();
        }
    }
    public void removeItem(T item){
        if(items.contains(item)) {
            this.items.remove(item);
            notifyDataSetChanged();
        }
    }
    public List<T> getItems(){
        if(items!=null){
            return items;
        }else{
            return null;
        }
    }

    AddressManaPresenter presenter;
    public void setPresenter(AddressManaPresenter presenter) {
        this.presenter = presenter;
    }
}

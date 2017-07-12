package com.ytxd.spp.ui.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by XY on 2016/10/18.
 */

public class MyGridLayoutManager extends GridLayoutManager {


    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}

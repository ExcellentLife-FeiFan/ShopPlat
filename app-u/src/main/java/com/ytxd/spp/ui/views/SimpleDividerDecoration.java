package com.ytxd.spp.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ytxd.spp.R;


/**
 * Created by XY on 2016/11/14.
 */

public class SimpleDividerDecoration  extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;

    public SimpleDividerDecoration(Context context,int colorres) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(colorres));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
    }
    public SimpleDividerDecoration(Context context,int colorres,int heightres) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(colorres));
        dividerHeight = (int) context.getResources().getDimension(heightres);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
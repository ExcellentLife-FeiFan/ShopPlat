
package com.ytxd.sppm.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class InGridView extends GridView {

    public InGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public InGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * 设置不滚动
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
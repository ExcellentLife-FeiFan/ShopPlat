package com.ytxd.spp.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ytxd.spp.R;


/**
 * Created by apple on 2017/5/21.
 */

public class MyExpandableLayout extends RelativeLayout {
    private Boolean isAnimationRunning = Boolean.valueOf(false);
    private Boolean isOpened = Boolean.valueOf(false);
    private Integer duration;
    private FrameLayout contentRelativeLayout;
    private FrameLayout headerRelativeLayout;
    public OnStateListener onStateListener;


    public MyExpandableLayout(Context context) {
        super(context);
    }

    public MyExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public MyExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View rootView = View.inflate(context, R.layout.view_expandable, this);
        this.headerRelativeLayout = (FrameLayout)rootView.findViewById(R.id.view_expandable_headerlayout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
        int headerID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_headerLayout, -1);
        int contentID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_contentLayout, -1);
        this.contentRelativeLayout = (FrameLayout)rootView.findViewById(R.id.view_expandable_contentLayout);
        if(headerID != -1 && contentID != -1) {
            this.duration = Integer.valueOf(typedArray.getInt(R.styleable.ExpandableLayout_el_duration, 500));
            View headerView = View.inflate(context, headerID, (ViewGroup)null);
            headerView.setLayoutParams(new LayoutParams(-1, -2));
            this.headerRelativeLayout.addView(headerView);
            View contentView = View.inflate(context, contentID, (ViewGroup)null);
            contentView.setLayoutParams(new LayoutParams(-2, -2));
            this.contentRelativeLayout.addView(contentView);
            this.contentRelativeLayout.setVisibility(GONE);
            this.headerRelativeLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(!isAnimationRunning.booleanValue()) {
                        if(contentRelativeLayout.getVisibility() == VISIBLE) {
                            collapse(contentRelativeLayout);
                        } else {
                            expand(contentRelativeLayout);
                        }

                        isAnimationRunning = Boolean.valueOf(true);
                        (new Handler()).postDelayed(new Runnable() {
                            public void run() {
                                isAnimationRunning = Boolean.valueOf(false);
                            }
                        }, (long)duration.intValue());
                    }

                }
            });
        } else {
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");
        }
    }

    private void expand(final View v) {
        v.measure(-1, -2);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1.0F) {
                    isOpened = Boolean.valueOf(true);
                }

                v.getLayoutParams().height = interpolatedTime == 1.0F?-2:(int)((float)targetHeight * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration((long)this.duration.intValue());
        v.startAnimation(animation);
        if(null!=onStateListener){
            onStateListener.onStateListener(true);
        }
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1.0F) {
                    v.setVisibility(GONE);
                    isOpened = Boolean.valueOf(false);
                } else {
                    v.getLayoutParams().height = initialHeight - (int)((float)initialHeight * interpolatedTime);
                    v.requestLayout();
                }

            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration((long)this.duration.intValue());
        v.startAnimation(animation);
        if(null!=onStateListener){
            onStateListener.onStateListener(false);
        }
    }

    public Boolean isOpened() {
        return this.isOpened;
    }

    public void show() {
        if(!this.isAnimationRunning.booleanValue()) {
            this.expand(this.contentRelativeLayout);
            this.isAnimationRunning = Boolean.valueOf(true);
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    isAnimationRunning = Boolean.valueOf(false);
                }
            }, (long)this.duration.intValue());
        }

    }

    public FrameLayout getHeaderRelativeLayout() {
        return this.headerRelativeLayout;
    }

    public FrameLayout getContentRelativeLayout() {
        return this.contentRelativeLayout;
    }

    public void hide() {
        if(!this.isAnimationRunning.booleanValue()) {
            this.collapse(this.contentRelativeLayout);
            this.isAnimationRunning = Boolean.valueOf(true);
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    isAnimationRunning = Boolean.valueOf(false);
                }
            }, (long)this.duration.intValue());
        }

    }
    public interface OnStateListener{
        public void onStateListener(boolean isOpen);
    }

    public void setOnStateListener(OnStateListener onStateListener) {
        this.onStateListener = onStateListener;
    }
}

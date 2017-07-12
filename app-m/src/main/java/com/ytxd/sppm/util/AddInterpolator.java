package com.ytxd.sppm.util;

import android.animation.TimeInterpolator;

/**
 * Created by XY on 2017/6/2.
 */

public class AddInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result = (float) (Math.sin(Math.PI * input)) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return input;
    }
}

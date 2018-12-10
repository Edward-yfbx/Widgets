package com.yfbx.widgets.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class Px {


    /**
     * dp 转换为 px
     */
    public static float dp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * sp 转换为 px
     */
    protected float sp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, Resources.getSystem().getDisplayMetrics());
    }
}

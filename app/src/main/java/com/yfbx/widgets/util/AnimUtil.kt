package com.useeinfo.caffee.util

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.provider.Settings
import android.util.TypedValue
import android.view.View


/**
 * Author: Edward
 * Date: 2019/2/28
 * Description:
 */

fun translateX(target: View, start: Float, end: Float) {
    ObjectAnimator.ofFloat(target, "translationX", dp2px(start), dp2px(end))
            .setDuration(300)
            .start()
}

fun translateY(target: View, start: Float, end: Float) {
    ObjectAnimator.ofFloat(target, "translationY", dp2px(start), dp2px(end))
            .setDuration(300)
            .start()
}


/**
 * dp 转换为 px
 */
private fun dp2px(value: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().displayMetrics)
}
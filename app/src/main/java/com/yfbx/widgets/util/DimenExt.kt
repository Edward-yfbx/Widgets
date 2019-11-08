package com.yfbx.widgets.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */


fun dp(value: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
}

fun dp(value: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics).toInt()
}

fun sp(value: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics)
}

fun sp(value: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), metrics).toInt()
}


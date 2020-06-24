package com.yfbx.widgets.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */
private val metrics = Resources.getSystem().displayMetrics

val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)
val Float.sp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, metrics)

val Int.dp
    get() = toFloat().dp.toInt()

const val match_parent = -1
const val wrap_content = -2


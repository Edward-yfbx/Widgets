package com.yfbx.widgets.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Author: Edward
 * Date: 2019-11-08
 * Description:
 */

fun Context.findColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}




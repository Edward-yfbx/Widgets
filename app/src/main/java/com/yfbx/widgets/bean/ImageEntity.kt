package com.yfbx.widgets.bean

import android.graphics.Bitmap

/**
 * Author: Edward
 * Date: 2020-08-10
 * Description:
 */
data class ImageEntity(val bitmap: Bitmap, val title: String, val originSize: Int, val size: Int) {


    fun desc(): String {
        return "$title:${size}k/${originSize}k"
    }
}

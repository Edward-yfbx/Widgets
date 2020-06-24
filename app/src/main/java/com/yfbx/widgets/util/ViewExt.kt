package com.yfbx.widgets.util

import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.annotation.ColorRes

/**
 * Author: Edward
 * Date: 2019-11-08
 * Description:
 */

fun TextView.setColor(@ColorRes color: Int) {
    setTextColor(context.findColor(color))
}


/**
 * 设置圆角
 */
fun View.setCorner(corner: Float) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, dp(corner))
        }
    }
}


/**
 * 带缩放动画的点击事件
 */
@SuppressLint("ClickableViewAccessibility")
fun View.setOnScaleClick(onClick: () -> Unit) {
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> scaleTo(0.95f)
            MotionEvent.ACTION_UP -> {
                scaleTo(1.0f)
                onClick.invoke()
            }
            MotionEvent.ACTION_CANCEL -> scaleTo(1.0f)
        }
        true
    }
}

fun View.setPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
}

/**
 * 设置缩放
 */
fun View.scaleTo(scale: Float) {
    scaleX = scale
    scaleY = scale
}
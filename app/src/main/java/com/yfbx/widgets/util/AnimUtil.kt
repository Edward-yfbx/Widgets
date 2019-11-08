package com.yfbx.widgets.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator


/**
 * Author: Edward
 * Date: 2019/2/28
 * Description:
 */

/**
 * Y轴平移
 */
fun View.translateX(start: Float, end: Float, duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "translationX", start, end)
            .setDuration(duration)
            .start()
}

/**
 * Y轴平移
 */
fun View.translateY(start: Float, end: Float, duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "translationY", start, end)
            .setDuration(duration)
            .start()
}


/**
 * 旋转动画
 */
fun View.rotate(startAngle: Float, endAngle: Float, duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "rotation", startAngle, endAngle)
            .setDuration(duration)
            .start()
}


/**
 * 旋转动画(重复)
 * @param duration 旋转一周所用时间(控制旋转速度)
 */
fun View.rotate(duration: Long = 1000): ObjectAnimator {
    val rotation = ObjectAnimator.ofFloat(this, "rotation", 0f, 359f)
    rotation.repeatCount = ObjectAnimator.INFINITE
    rotation.interpolator = LinearInterpolator()
    rotation.duration = duration
    rotation.start()
    return rotation
}

/**
 * 值变化
 */
fun valueInt(start: Int, end: Int, onUpdate: (Int) -> Unit) {
    val anim = ValueAnimator.ofInt(start, end)
    anim.duration = 300
    anim.addUpdateListener {
        val value = it.animatedValue as Int
        onUpdate.invoke(value)
    }
    anim.start()
}

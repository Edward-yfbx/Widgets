package com.yfbx.widgets.widget.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View

/**
 * Author: Edward
 * Date: 2018/7/16
 * Description: 绘制动画
 */
class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var paint = Paint()
    private var degree: Int = 0

    init {
        val colors = intArrayOf(-0xdce105, -0x78ef08, -0x34fa01)
        val sweepGradient = SweepGradient(500f, 400f, colors, null)

        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 15f
        paint.shader = sweepGradient
        paint.isAntiAlias = true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(128, 128)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(8f, 8f, 120f, 120f, degree.toFloat(), 300f, false, paint)
        setRotate()
    }

    private fun setRotate() {
        degree += 10
        if (degree >= 360) {
            degree = 0
        }
        invalidate()
    }
}

package com.yfbx.widgets.widget.anim

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * Author: Edward
 * Date: 2019/1/25
 * Description:
 */

class AnimProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    private val paint = Paint()
    private var mWidth = 0
    private var mHeight = 0
    private val stroke = 48f
    private var progress = stroke

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        paint.strokeWidth = stroke
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = 1000
        mHeight = stroke.toInt()
        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onDraw(canvas: Canvas?) {
        canvas!!.translate(0F, mHeight / 2f)
        paint.color = Color.parseColor("#43291D")
        canvas.drawLine(stroke, 0f, mWidth - stroke, 0f, paint)

        setProgressPaintColor()
        canvas.drawLine(stroke, 0F, progress, 0F, paint)
        paint.shader = null

        mHandler.postDelayed(anim, 100)

    }

    /**
     * 设置进度画笔颜色
     */
    private fun setProgressPaintColor() {
        val colors = intArrayOf(
                Color.parseColor("#F76C31"),
                Color.parseColor("#FB591B"),
                Color.parseColor("#FF3D00"),
                Color.parseColor("#FF3D00"),
                Color.parseColor("#43291D"))
        paint.shader = LinearGradient(0F, 0F, mWidth.toFloat(), 0F, colors, null, Shader.TileMode.CLAMP)
    }

    private val mHandler = Handler()

    /**
     * 动画
     */
    private val anim = Runnable {
        if (progress < mWidth - stroke - 20) {//不能满
            progress++
            invalidate()
        }
    }


    fun finish() {
        mHandler.removeCallbacks(anim)
        progress = mWidth - stroke
        invalidate()
    }


    /**
     * dp 转换为 px
     */
    private fun dp2px(value: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics).toInt()
    }
}
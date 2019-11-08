package com.yfbx.widgets.widget.charts

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp

/**
 * Author: Edward
 * Date: 2019/1/25
 * Description:
 */

class HorizontalProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    private var mWidth = 0
    private var mHeight = 0

    private val paint = Paint()

    private var progress = 0F//进度
    private var progressColor = 0//进度颜色
    private var gradientColor = -1//进度颜色(渐变)
    private var progressBackColor = 0//进度背景色


    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar)
        progressColor = array.getColor(R.styleable.HorizontalProgressBar_progressColor, Color.RED)
        gradientColor = array.getColor(R.styleable.HorizontalProgressBar_gradientColor, -1)
        progressBackColor = array.getColor(R.styleable.HorizontalProgressBar_progressBackColor, Color.LTGRAY)
        progress = array.getFloat(R.styleable.HorizontalProgressBar_progress, 0f)
        array.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec)
        } else {
            mWidth = dp(128)
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            mHeight = MeasureSpec.getSize(heightMeasureSpec)
        } else {
            mHeight = dp(4)
        }
        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onDraw(canvas: Canvas?) {
        canvas!!.translate(0F, mHeight / 2f)

        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        paint.strokeWidth = mHeight.toFloat()

        paint.color = progressBackColor
        canvas.drawLine(paddingLeft.toFloat(), 0f, (mWidth - paddingRight).toFloat(), 0f, paint)

        val end = progress / 100f * (mWidth - paddingRight)
        if (end > 0) {
            setProgressPaintColor()
            canvas.drawLine(paddingLeft.toFloat(), 0F, end, 0F, paint)
            paint.shader = null
        }
    }

    /**
     * 设置进度画笔颜色
     */
    private fun setProgressPaintColor() {
        if (gradientColor == -1) {
            paint.color = progressColor
        } else {
            paint.shader = LinearGradient(0F, 0F, mWidth.toFloat(), 0F, progressColor, gradientColor, Shader.TileMode.CLAMP)
        }
    }


    /**
     * 设置进度颜色
     */
    fun setProgressColor(vararg colors: Int) {
        progressColor = colors[0]
        if (colors.size > 1) {
            gradientColor = colors[1]
        }
        invalidate()
    }

    /**
     * 设置进度
     */
    fun setProgress(progress: Float) {
        if (progress in 0F..100F) {
            this.progress = progress
            invalidate()
        }
    }
}
package com.yfbx.widgets.charts

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.yfbx.widgets.R

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
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            mWidth = dp2px(128f)
        }

        //height
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            mHeight = dp2px(4f)
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

        setProgressPaintColor()
        val end = progress / 100f * (mWidth - paddingRight)
        canvas.drawLine(paddingLeft.toFloat(), 0F, end, 0F, paint)
        paint.shader = null

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

    /**
     * dp 转换为 px
     */
    private fun dp2px(value: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics).toInt()
    }
}
package com.yfbx.widgets.kotlin.charts

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.yfbx.widgets.R

/**
 * Author: Edward
 * Date: 2019/1/31
 * Description:
 */

class GradeChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var mWidth = 0
    private var mHeight = 0
    private var gap = 0f
    private var radius = 0f

    private val colors = mutableListOf(
            Color.GRAY,
            Color.GRAY,
            Color.GRAY,
            Color.GRAY,
            Color.GRAY
    )

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.GradeChart)
        val colorStr = array.getString(R.styleable.GradeChart_colors)
        gap = array.getDimension(R.styleable.GradeChart_gap, dp2px(2f))
        radius = array.getDimension(R.styleable.GradeChart_radius, dp2px(4f))
        array.recycle()


        if (!TextUtils.isEmpty(colorStr)) {
            val colorArray = colorStr.split(",")
            colors.clear()
            colorArray.forEach { colors.add(Color.parseColor(it)) }

        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            val w = dp2px(24f) * colors.size + gap * 4
            mWidth = w.toInt()
        }

        //height
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            mHeight = dp2px(48f).toInt()
        }
        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onDraw(canvas: Canvas) {
        clipPath(canvas)
        val width = (mWidth - gap * 4) / colors.size
        colors.forEach {
            paint.color = it
            canvas.drawRect(0f, 0f, width, mHeight.toFloat(), paint)
            canvas.translate(width + gap, 0f)
        }
    }


    /**
     * 圆角
     */
    private fun clipPath(canvas: Canvas) {
        val path = Path()
        path.addRoundRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), radius, radius, Path.Direction.CCW)
        canvas.clipPath(path)
    }


    /**
     * dp 转换为 px
     */
    private fun dp2px(value: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
    }

}
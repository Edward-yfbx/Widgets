package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.yfbx.widgets.R

/**
 * Author: Edward
 * Date: 2019/2/28
 * Description:
 */
class PageIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val SELECTED_STATE = intArrayOf(android.R.attr.state_selected)
    private val UNSELECTED_STATE = intArrayOf(-android.R.attr.state_selected)

    private var mWidth = 0
    private var mHeight = 0

    private var indicator: Drawable? = null
    private var count = 0
    private var space = 0f

    private var selected = 0


    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.PageIndicator)
        indicator = array.getDrawable(R.styleable.PageIndicator_indicator)
        count = array.getInt(R.styleable.PageIndicator_count, 0)
        space = array.getDimension(R.styleable.PageIndicator_space, defaultSpace())
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            measureHeight()
        }
        mWidth = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            measureWidth()
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun measureWidth(): Int {
        val width = indicator?.intrinsicWidth ?: 0
        return width * count + space.toInt() * (count - 1)
    }

    private fun measureHeight(): Int {
        return indicator?.intrinsicHeight ?: 0
    }

    private fun defaultSpace(): Float {
        return indicator?.intrinsicWidth?.toFloat() ?: 0f
    }


    override fun onDraw(canvas: Canvas) {
        val drawable = indicator ?: return

        for (i in 0 until count) {
            val imgWidth = drawable.intrinsicWidth
            val imgHeight = drawable.intrinsicHeight
            drawable.state = if (i == selected) SELECTED_STATE else UNSELECTED_STATE
            drawable.setBounds(0, 0, imgWidth, imgHeight)
            drawable.draw(canvas)

            canvas.translate(space + imgWidth, 0f)
        }
    }

    fun select(selected: Int) {
        this.selected = selected
        invalidate()
    }

    fun setCount(count: Int) {
        this.count = count
        invalidate()
    }

}
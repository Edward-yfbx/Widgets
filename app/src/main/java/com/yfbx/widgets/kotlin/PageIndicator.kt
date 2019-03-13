package com.useeinfo.caffee.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Author: Edward
 * Date: 2019/2/28
 * Description:
 */
class PageIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    private var count = 3
    private var select = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            mHeight = 20
        }
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            mWidth = height * 2 * count * 2
        }
        setMeasuredDimension(mWidth, mHeight)

    }

    override fun onDraw(canvas: Canvas) {
        val radius = mHeight / 2f
        val total = radius * 2 * count * 2
        val start = (mWidth - total) / 2
        canvas.translate(start, radius)

        for (i in 1..count) {
            if (i == select + 1) {
                paint.color = Color.WHITE
            } else {
                paint.color = 0x55FFFFFF
            }
            canvas.drawCircle(i * radius * 3, 0f, radius, paint)
        }
    }


    fun select(select: Int) {
        this.select = select
        invalidate()
    }

    fun setCount(count: Int) {
        this.count = count
        invalidate()
    }

}
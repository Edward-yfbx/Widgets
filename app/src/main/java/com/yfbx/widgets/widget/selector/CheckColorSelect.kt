package com.yfbx.widgets.widget.selector

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Checkable
import com.yfbx.widgets.R
import com.yfbx.widgets.util.sp

/**
 * Author:Edward
 * Date:2018/10/26
 * Description:
 */

class CheckColorSelect @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes), Checkable {


    private var paint: TextPaint = TextPaint()
    private var width = 0f
    private var height = 0f

    private var colorStateList: ColorStateList? = null
    private var checked: Boolean = false
    private var text: String? = null


    init {
        getAttr(context, attrs)
        paint.textSize = 14f.sp
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CheckColorSelect)
        checked = array.getBoolean(R.styleable.CheckColorSelect_android_checked, false)
        text = array.getString(R.styleable.CheckColorSelect_android_text)
        colorStateList = array.getColorStateList(R.styleable.CheckColorSelect_android_textColor)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val textBounds = getTextBounds()
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        } else {
            width = (textBounds.width() + paddingLeft + paddingRight).toFloat()
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {
            height = (textBounds.height() + paddingTop + paddingBottom).toFloat()
        }
        setMeasuredDimension(width.toInt(), height.toInt())//存在精度损失
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        height = h.toFloat()
        width = w.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.translate(0f, height / 2)
        val color = colorStateList!!.getColorForState(drawableState, colorStateList!!.defaultColor)
        paint.color = color
        val textBounds = getTextBounds()
        canvas.drawText(text!!, paddingLeft.toFloat(), (-textBounds.centerY()).toFloat(), paint)

    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(states, intArrayOf(android.R.attr.state_checked))
        }
        return states
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return event.action != MotionEvent.ACTION_UP || performClick()
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun setChecked(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            refreshDrawableState()
        }
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun toggle() {
        isChecked = !checked
    }


    private fun getTextBounds(): Rect {
        val rect = Rect()
        paint.getTextBounds(text, 0, text!!.length, rect)
        return rect
    }

}

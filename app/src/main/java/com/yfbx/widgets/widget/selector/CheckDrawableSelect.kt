package com.yfbx.widgets.widget.selector

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Checkable
import com.yfbx.widgets.R

/**
 * Author:Edward
 * Date:2018/10/26
 * Description:
 */

class CheckDrawableSelect @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes), Checkable {

    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)

    private var width = 0f
    private var height = 0f

    private var checked: Boolean = false
    private var src: Drawable? = null

    init {
        getAttr(context, attrs)
    }

    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CheckDrawableSelect)
        checked = array.getBoolean(R.styleable.CheckDrawableSelect_android_checked, false)
        src = array.getDrawable(R.styleable.CheckDrawableSelect_android_src)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        } else {
            width = (src!!.intrinsicWidth + paddingLeft + paddingRight).toFloat()
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {
            height = (src!!.intrinsicHeight + paddingTop + paddingBottom).toFloat()
        }
        setMeasuredDimension(width.toInt(), height.toInt())//存在精度损失
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        height = h.toFloat()
        width = w.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        val imgWidth = src!!.intrinsicWidth
        val imgHeight = src!!.intrinsicHeight
        val left = ((width - imgWidth) / 2).toInt()
        val top = ((height - imgHeight) / 2).toInt()
        src!!.state = drawableState
        src!!.setBounds(left, top, left + imgWidth, top + imgHeight)
        src!!.draw(canvas)

    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(states, CHECKED_STATE_SET)
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


}

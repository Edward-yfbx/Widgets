package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import com.yfbx.widgets.R


/**
 * Author:Edward
 * Date:2018/4/3
 * Description:
 */

class ClearEdit @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : EditText(context, attrs, defStyleAttr), TextWatcher {

    private var show: Boolean = false
    private var bitmap: Bitmap? = null
    private var textChangeListener: OnTextChangeListener? = null

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_close)
        addTextChangedListener(this)
        isFocusable = true
        isFocusableInTouchMode = true
    }


    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        show = text != null && text.toString().isNotEmpty() && focused
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (show) {
            val left = width - bitmap!!.width - paddingRight
            val top = (height - bitmap!!.height) / 2
            canvas.drawBitmap(bitmap!!, left.toFloat(), top.toFloat(), null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (bitmap != null && show) {
            val left = width - bitmap!!.width - paddingRight
            if (event.rawX >= left) {
                setText("")
                show = false
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        inputType = inputType
    }

    override fun afterTextChanged(s: Editable?) {
        show = s != null && s.isNotEmpty() && isFocused
        invalidate()
        if (textChangeListener != null) {
            textChangeListener!!.onTextChanged(s)
        }
    }

    fun setOnTextChangeListener(textChangeListener: OnTextChangeListener) {
        this.textChangeListener = textChangeListener
    }

    interface OnTextChangeListener {

        fun onTextChanged(editable: Editable?)
    }
}

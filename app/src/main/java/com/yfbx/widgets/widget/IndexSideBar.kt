package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.sp
import com.yfbx.widgets.util.wrap_content


/**
 * Author: Edward
 * Date: 2019-12-11
 * Description:
 */
class IndexSideBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val textPaint = Paint()
    private val textRect = Rect()
    private var mWidth = 0
    private var mHeight = 0

    private var textSize = 10f.sp
    private var textColor = Color.parseColor("#191E23")
    private var colorSelected = Color.parseColor("#D72D3C")
    private var space = 2f.dp
    private var radius = 8f.dp
    private var diam = radius * 2

    private var letters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    private var listener: ((index: Int, letter: String) -> Unit)? = null
    private var selected = 0
    private var pop: PopupWindow? = null


    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.IndexSideBar)
        textSize = array.getDimension(R.styleable.IndexSideBar_android_textSize, 10f.sp)
        textColor = array.getColor(R.styleable.IndexSideBar_android_textColor, Color.parseColor("#191E23"))
        colorSelected = array.getColor(R.styleable.IndexSideBar_colorSelected, Color.parseColor("#D72D3C"))
        space = array.getDimension(R.styleable.IndexSideBar_space, 2f.dp)
        radius = array.getDimension(R.styleable.IndexSideBar_radius, 8f.dp)
        diam = radius * 2
        array.recycle()
        createPop()
    }


    /**
     * 测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureTextBounds(letters[0])
        //width
        mWidth = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            (diam + paddingStart + paddingEnd).toInt()
        }
        //height
        mHeight = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            (diam * letters.size + space * (letters.size - 1)).toInt()
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    /**
     * 文字测量
     */
    private fun measureTextBounds(text: String) {
        textPaint.textSize = textSize
        textPaint.getTextBounds(text, 0, text.length, textRect)
    }

    /**
     * 绘制
     */
    override fun onDraw(canvas: Canvas) {
        paint.color = Color.RED
        letters.forEachIndexed { index, item ->
            setPaint(index)
            canvas.drawCircle(mWidth / 2f, radius, radius, paint)
            canvas.drawText(item, mWidth / 2f, (diam + textRect.height()) / 2f, textPaint)
            canvas.translate(0f, diam + space)
        }
    }

    /**
     * 画笔
     */
    private fun setPaint(index: Int) {
        paint.reset()
        textPaint.reset()

        paint.isAntiAlias = true
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = textSize
        paint.color = if (index == selected) colorSelected else Color.TRANSPARENT
        textPaint.color = if (index == selected) Color.WHITE else textColor
    }


    /**
     * Touch事件
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val itemHeight = diam + space
        val newChoose = (event.y / itemHeight).toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onChange(newChoose)
            }
            MotionEvent.ACTION_UP -> {
                hideIndicator()
            }
            MotionEvent.ACTION_CANCEL -> {
                hideIndicator()
            }
            MotionEvent.ACTION_MOVE -> {
                onChange(newChoose)
            }
        }
        return true
    }

    /**
     * 移动变化
     */
    private fun onChange(newChoose: Int) {
        if (newChoose in letters.indices) {
            if (selected != newChoose) {
                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                selected = newChoose
                listener?.invoke(selected, letters[newChoose])
                invalidate()
            }
            showIndicator()
        }
    }

    /**
     * 指示器
     */
    private fun showIndicator() {
        updateText()
        val itemHeight = diam + space
        val offset = -mHeight / 2f + itemHeight * selected + popHeight() / 2f + itemHeight / 2f
        if (pop?.isShowing == true) {
            pop?.update(mWidth, offset.toInt(), wrap_content, wrap_content, true)
        } else {
            pop?.showAtLocation(this, Gravity.END, mWidth, offset.toInt())
        }
    }

    /**
     * 隐藏指示器
     */
    private fun hideIndicator() {
        if (pop?.isShowing == true) {
            pop?.dismiss()
        }
    }

    /**
     * 指示器文本
     */
    private fun updateText() {
        val textView = pop?.contentView as? TextView
        textView?.text = letters[selected]
    }

    /**
     * Pop offset 以左上角为起点
     */
    private fun popHeight(): Float {
        return 48f.dp
    }

    private fun createPop() {
        val view = TextView(context)
        view.setTextColor(Color.WHITE)
        view.setBackgroundResource(R.drawable.pop_indicator)
        view.textSize = 24f
        view.gravity = Gravity.CENTER
        view.setPadding(0, 0, 12.dp, 0)
        view.paint.isFakeBoldText = true
        pop = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    fun setLetters(letters: Array<String>) {
        if (letters.isNotEmpty()) {
            this.letters = letters
            requestLayout()
            invalidate()
        }
    }

    fun setSelected(index: Int) {
        this.selected = index
        invalidate()
    }

    fun onIndexChange(listener: (index: Int, letter: String) -> Unit) {
        this.listener = listener
    }

}

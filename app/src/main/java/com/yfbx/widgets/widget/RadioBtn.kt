package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.sp

/**
 * Author:Edward
 * Date:2018/10/22
 * Description:
 */

class RadioBtn @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var circlePaint = Paint()
    private var textPaint = TextPaint()
    private var descPaint = TextPaint()
    private var width = 0f
    private var height = 0f

    //属性
    private var checked: Boolean = false
    private var text: String? = null
    private var textColor: Int = 0
    private var textSize: Float = 0.toFloat()
    private var desc: String? = null
    private var descColor: Int = 0
    private var descSize: Float = 0.toFloat()

    private var radiusL: Float = 0.toFloat()//大圆半径
    private var radiusS: Float = 0.toFloat()//小圆半径
    private var gradient: IntArray? = null//渐变色


    var isChecked: Boolean
        get() = checked
        set(checked) {
            this.checked = checked
            invalidate()
        }

    init {
        getAttr(context, attrs)
        circlePaint.style = Paint.Style.FILL
        circlePaint.isAntiAlias = true

        textPaint.style = Paint.Style.FILL
        textPaint.isAntiAlias = true

        descPaint.style = Paint.Style.FILL
        descPaint.isAntiAlias = true
    }

    /**
     * 获取属性
     */
    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RadioBtn)
        checked = array.getBoolean(R.styleable.RadioBtn_android_checked, false)
        val startColor = array.getColor(R.styleable.RadioBtn_android_startColor, Color.GRAY)
        val centerColor = array.getColor(R.styleable.RadioBtn_android_centerColor, Color.GRAY)
        val endColor = array.getColor(R.styleable.RadioBtn_android_endColor, Color.GRAY)
        gradient = intArrayOf(startColor, centerColor, endColor)

        text = array.getString(R.styleable.RadioBtn_android_text)
        textColor = array.getColor(R.styleable.RadioBtn_android_textColor, Color.DKGRAY)
        textSize = array.getDimension(R.styleable.RadioBtn_android_textSize, 20f.sp)

        desc = array.getString(R.styleable.RadioBtn_desc)
        descColor = array.getColor(R.styleable.RadioBtn_descColor, Color.DKGRAY)
        descSize = array.getDimension(R.styleable.RadioBtn_descSize, 12f.sp)

        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        } else {
            width = 80f.dp
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {
            height = width
        }
        setMeasuredDimension(width.toInt(), height.toInt())//存在精度损失
    }


    override fun onDraw(canvas: Canvas) {
        canvas.translate(width / 2, 0f)

        drawCircle(canvas)
        drawText(canvas)
        drawDescription(canvas)

    }


    /**
     * 绘制渐变圆
     */
    private fun drawCircle(canvas: Canvas) {
        //大圆半径是控件最小边长的一半
        radiusL = Math.min(width, height) / 2
        //小圆半径自定义合适大小
        radiusS = radiusL * 0.65f
        //根据选中状态绘制圆
        val radius = if (checked) radiusL else radiusS
        //设置渐变色
        circlePaint.shader = LinearGradient(radius, radius * 2, -radius, 0f, gradient!!, null, Shader.TileMode.CLAMP)
        //绘制圆
        canvas.drawCircle(0f, radius, radius, circlePaint)
    }

    /**
     * 绘制中心文字
     */
    private fun drawText(canvas: Canvas) {
        if (text != null) {
            textPaint.color = if (checked) Color.WHITE else Color.DKGRAY
            textSize = if (checked) 32f.sp else 20f.sp
            val rect = measureText(textPaint, text ?: "", textSize)
            val left = -textPaint.measureText(text) / 2
            val top = radiusS - rect.exactCenterY()
            canvas.drawText(text!!, left, top, textPaint)
        }
    }

    /**
     * 绘制底部描述文字
     */
    private fun drawDescription(canvas: Canvas) {
        if (desc != null) {
            descPaint.color = if (checked) Color.WHITE else Color.DKGRAY
            val rect = measureText(descPaint, desc ?: "", descSize)
            val left = -descPaint.measureText(desc) / 2
            val top = if (checked) height - radiusS - rect.centerY().toFloat() else height + rect.centerY()
            canvas.drawText(desc!!, left, top, descPaint)
        }
    }

    /**
     * 测量文字
     */
    private fun measureText(paint: TextPaint, text: String, size: Float): Rect {
        val rect = Rect()
        paint.textSize = size
        paint.getTextBounds(text, 0, text.length, rect)
        return rect
    }


    /**
     * Touch事件
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return event.action != MotionEvent.ACTION_UP || performClick()
    }

    /**
     * 点击事件
     */
    override fun performClick(): Boolean {
        if (checked) {
            isChecked = false
        } else {
            checkReset()
            isChecked = true
        }
        return super.performClick()
    }

    /**
     * 重置LinearLayout 中子View的选中状态
     */
    private fun checkReset() {
        val parent = parent
        if (parent !is LinearLayout) {
            return
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (child is RadioBtn) {
                if (child.isChecked) {
                    child.isChecked = false
                }
            }
        }
    }


    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        invalidate()
    }


    fun getDesc(): String? {
        return desc
    }

    fun setDesc(desc: String) {
        this.desc = desc
        invalidate()
    }

    fun getDescColor(): Int {
        return descColor
    }

    fun setDescColor(descColor: Int) {
        this.descColor = descColor
        invalidate()
    }

    fun getDescSize(): Float {
        return descSize
    }

    fun setDescSize(descSize: Float) {
        this.descSize = descSize
        invalidate()
    }

}

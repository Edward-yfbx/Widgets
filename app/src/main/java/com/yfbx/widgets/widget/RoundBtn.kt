package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.sp

/**
 * Author:Edward
 * Date:2018/4/9
 * Description:
 */

@Suppress("unused")
class RoundBtn @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var title: String? = null
    private var titleSize = 0f
    private var subSize = 0f
    private var sub1: String? = null
    private var sub2: String? = null
    private var checked: Boolean = false
    private var subColor = 0
    private var bgColor = 0
    private var startColor = 0
    private var centerColor = 0
    private var endColor = 0
    private var gradient: IntArray? = null

    private var paint = Paint()
    private var height = 0f
    private var width = 0f
    private var radius = 0f
    private var checkListener: OnCheckChangeListener? = null
    private var clickListener: OnClickListener? = null

    /**
     * 设置选中状态
     */
    var isChecked: Boolean
        get() = checked
        set(checked) {
            this.checked = checked
            setWeight(this, if (checked) 2 else 1)
            invalidate()

            if (checkListener != null) {
                checkListener!!.onCheckChange(this, checked)
            }
        }

    init {
        getAttr(context, attrs)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        gradient = intArrayOf(startColor, centerColor, endColor)
    }

    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundBtn)
        checked = array.getBoolean(R.styleable.RoundBtn_android_checked, false)
        bgColor = array.getColor(R.styleable.RoundBtn_bgColor, Color.WHITE)
        startColor = array.getColor(R.styleable.RoundBtn_startColor, bgColor)
        centerColor = array.getColor(R.styleable.RoundBtn_centerColor, bgColor)
        endColor = array.getColor(R.styleable.RoundBtn_endColor, bgColor)

        title = array.getString(R.styleable.RoundBtn_android_text)
        titleSize = array.getDimension(R.styleable.RoundBtn_android_textSize, 20f.sp)

        sub1 = array.getString(R.styleable.RoundBtn_sub1)
        sub2 = array.getString(R.styleable.RoundBtn_sub2)
        subColor = array.getColor(R.styleable.RoundBtn_subColor, -0xcccccd)
        subSize = array.getDimension(R.styleable.RoundBtn_subSize, 12f.sp)

        array.recycle()
    }


    /**
     * 测量
     */
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
            height = width * 1.5f
        }
        setMeasuredDimension(width.toInt(), height.toInt())//存在精度损失
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = width / 3
    }


    /**
     * 绘制
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(width / 2, 0f)
        drawCircle(canvas)
        if (!TextUtils.isEmpty(title)) {
            drawTitle(canvas)
        }
        if (!TextUtils.isEmpty(sub1)) {
            drawSub1(canvas)
        }
        if (!TextUtils.isEmpty(sub2)) {
            drawSub2(canvas)
        }
    }

    /**
     * 绘制背景圆
     */
    private fun drawCircle(canvas: Canvas) {
        paint.shader = LinearGradient(radius, radius * 2, -radius, 0f, gradient!!, null, Shader.TileMode.CLAMP)
        canvas.drawCircle(0f, radius, radius, paint)
        paint.shader = null
    }


    /**
     * 绘制主标题
     */
    private fun drawTitle(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.textSize = titleSize
        val rect = getTextBounds(title)
        val left = (-rect.width() / 2).toFloat()
        val top = if (checked) rect.height() + 8f.dp else rect.height() / 2 + radius
        canvas.drawText(title!!, left, top, paint)
    }

    /**
     * 绘制副标题1
     */
    private fun drawSub1(canvas: Canvas) {
        paint.color = if (checked) Color.WHITE else subColor
        paint.textSize = subSize
        val rect = getTextBounds(sub1)
        val left = (-rect.width() / 2).toFloat()
        val top = if (checked) radius + rect.height() else radius * 2 + rect.height().toFloat() + 4f.dp
        canvas.drawText(sub1!!, left, top, paint)
    }

    /**
     * 绘制副标题2
     */
    private fun drawSub2(canvas: Canvas) {
        paint.color = if (checked) Color.WHITE else subColor
        paint.textSize = subSize
        val rect = getTextBounds(sub2)
        val left = (-rect.width() / 2).toFloat()
        val top = if (checked) radius * 2 - rect.height() else radius * 4 - rect.height() / 2
        canvas.drawText(sub2!!, left, top, paint)
    }

    /**
     * Touch事件
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            checkReset()
            isChecked = true
            if (clickListener != null) {
                clickListener!!.onClick(this)
            }
        }
        return true
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
            if (child is RoundBtn) {
                if (child.isChecked) {
                    child.isChecked = false
                }
            }
        }
    }

    /**
     * 设置权重
     */
    private fun setWeight(view: RoundBtn, weight: Int) {
        val params = LinearLayout.LayoutParams(0, height.toInt())
        params.weight = weight.toFloat()
        view.layoutParams = params
    }

    override fun setOnClickListener(clickListener: OnClickListener?) {
        this.clickListener = clickListener
        super.setOnClickListener(clickListener)

    }

    fun setOnCheckChangeListener(checkListener: OnCheckChangeListener) {
        this.checkListener = checkListener
    }

    interface OnCheckChangeListener {
        fun onCheckChange(btn: RoundBtn, isChecked: Boolean)
    }


    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
        invalidate()
    }

    fun getSubSize(): Float {
        return subSize
    }

    fun setSubSize(subSize: Float) {
        this.subSize = subSize.sp
        invalidate()
    }

    fun getTitleSize(): Float {
        return titleSize
    }

    fun setTitleSize(titleSize: Float) {
        this.titleSize = titleSize.sp
        invalidate()
    }

    fun getSub1(): String? {
        return sub1
    }

    fun setSub1(sub1: String) {
        this.sub1 = sub1
        invalidate()
    }

    fun getSub2(): String? {
        return sub2
    }

    fun setSub2(sub2: String) {
        this.sub2 = sub2
        invalidate()
    }


    fun getBgColor(): Int {
        return bgColor
    }

    fun setBgColor(bgColor: Int) {
        this.bgColor = bgColor
        invalidate()
    }


    /**
     * 测量文字
     */
    private fun getTextBounds(text: String?): Rect {
        val rect = Rect()
        paint.getTextBounds(text, 0, text!!.length, rect)
        return rect
    }
}

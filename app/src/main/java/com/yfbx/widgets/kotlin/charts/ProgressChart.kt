package com.yfbx.widgets.kotlin.charts

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.yfbx.widgets.R


/**
 * Author: Edward
 * Date: 2019/1/18
 * Description:
 */


class ProgressChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var paint = Paint()
    private var textPaint = Paint()

    private var w = 0
    private var h = 0

    private var stroke: Float = 0F
    private var progress: Float = 0F
    private var progressColor: Int = 0
    private lateinit var title: String
    private lateinit var text: String
    private var titleColor: Int = 0
    private var titleSize: Float = 0F
    private var textColor: Int = 0
    private var textSize: Float = 0F

    init {
        getAttr(context, attrs)
        init()
    }


    /**
     * 获取属性
     */
    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ProgressChart)
        text = array.getString(R.styleable.ProgressChart_android_text)
        title = array.getString(R.styleable.ProgressChart_title)
        textColor = array.getColor(R.styleable.ProgressChart_android_textColor, Color.BLACK)
        titleColor = array.getColor(R.styleable.ProgressChart_titleColor, Color.GRAY)
        textSize = array.getDimension(R.styleable.ProgressChart_android_textSize, sp2px(24f))
        titleSize = array.getDimension(R.styleable.ProgressChart_titleSize, sp2px(14f))
        stroke = array.getDimension(R.styleable.ProgressChart_stroke, sp2px(8f))
        progressColor = array.getColor(R.styleable.ProgressChart_progressColor, Color.BLUE)
        progress = array.getFloat(R.styleable.ProgressChart_progress, 0f)
        array.recycle()

    }

    /**
     * 初始化
     */
    private fun init() {
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = stroke
        paint.isAntiAlias = true

        textPaint.strokeCap = Paint.Cap.ROUND
        textPaint.style = Paint.Style.STROKE
        textPaint.strokeWidth = stroke
        textPaint.isAntiAlias = true

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            w = View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            w = dp2px(128f)
        }

        //height
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
            h = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            h = w
        }
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        //背景弧
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#EDF5F5")
        drawArc(100f, canvas, paint)

        //进度弧
        paint.color = progressColor
        drawArc(progress, canvas, paint)

        //中间字
        canvas.translate((w / 2).toFloat(), (h / 2).toFloat())
        textPaint.color = textColor
        textPaint.style = Paint.Style.FILL
        textPaint.strokeWidth = 1f
        val rect = measureText(textPaint, text, textSize)
        canvas.drawText(text, (-rect.width() / 2).toFloat(), (-rect.centerY()).toFloat(), textPaint)

        //底部字
        canvas.translate(0f, h / 2 - stroke)
        textPaint.color = titleColor
        val size = measureText(textPaint, title, titleSize)
        canvas.drawText(title, (-size.width() / 2).toFloat(), (-size.centerY()).toFloat(), textPaint)

    }

    /**
     * 画弧
     */
    private fun drawArc(percent: Float, canvas: Canvas, paint: Paint) {
        if (percent < 0 || percent > 100) {
            return
        }
        canvas.drawArc(stroke, stroke, w - stroke, h - stroke,
                -225f, 270 * percent / 100, false, paint)
    }

    /**
     * 测量文字
     */
    private fun measureText(paint: Paint, text: String, size: Float): Rect {
        val rect = Rect()
        paint.textSize = size
        paint.getTextBounds(text, 0, text.length, rect)
        return rect
    }


    /**
     * 进度颜色
     */
    fun setProgressColor(colors: IntArray) {
        val sweepGradient = SweepGradient(width.toFloat(), height.toFloat(), colors, null)
        paint.shader = sweepGradient
        invalidate()
    }

    /**
     * 设置进度
     */
    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    /**
     * 中间文本
     */
    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    /**
     * sp 转换为 px
     */
    private fun sp2px(value: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics)
    }

    /**
     * dp 转换为 px
     */
    private fun dp2px(value: Float): Int {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics).toInt()
    }

}

package com.yfbx.widgets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.sp
import kotlin.math.max

/**
 * Author:Edward
 * Date:2018/5/25
 * Description:
 */

class ValueText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    companion object {
        /**
         * textAlign 常量
         */
        const val ALIGN_LEFT = 0
        const val ALIGN_RIGHT = 1
    }


    private var paint = Paint()
    private var availableWidth = 0f

    /**
     * 文字尺寸
     */
    private var titleRect = Rect()
    private var textRect = Rect()

    /**
     * View尺寸
     */
    private var width = 0f
    private var height = 0f
    /**
     * View属性
     */
    private var text: String? = null
    private var title: String? = null
    private var textColor: Int = 0
    private var titleColor: Int = 0
    private var textSize = 0f
    private var titleSize = 0f
    private var drawableLeft: Drawable? = null
    private var drawableRight: Drawable? = null
    private var drawablePadding = 0f
    private var textAlign: Int = 0
    private var textMargin = 0f
    var lineSpace = 0f
    var isSingleLine: Boolean = false

    var isOverLength: Boolean = false
    private val builder = StringBuilder()//保存被截掉的文字


    init {
        getAttr(context, attrs)
        paint.style = Paint.Style.FILL
    }

    /**
     * 获取属性
     */
    private fun getAttr(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ValueText)
        text = array.getString(R.styleable.ValueText_android_text)
        title = array.getString(R.styleable.ValueText_title)
        textColor = array.getColor(R.styleable.ValueText_android_textColor, Color.GRAY)
        titleColor = array.getColor(R.styleable.ValueText_titleColor, Color.GRAY)
        textSize = array.getDimension(R.styleable.ValueText_android_textSize, sp(14f))
        titleSize = array.getDimension(R.styleable.ValueText_titleSize, sp(14f))
        drawableLeft = array.getDrawable(R.styleable.ValueText_android_drawableLeft)
        drawableRight = array.getDrawable(R.styleable.ValueText_android_drawableRight)
        drawablePadding = array.getDimension(R.styleable.ValueText_android_drawablePadding, dp(8f))
        textAlign = array.getInt(R.styleable.ValueText_textAlign, ALIGN_LEFT)
        textMargin = array.getDimension(R.styleable.ValueText_textMargin, sp(16f))
        isSingleLine = array.getBoolean(R.styleable.ValueText_android_singleLine, false)
        lineSpace = array.getDimension(R.styleable.ValueText_lineSpace, dp(6f))
        array.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        } else {
            width = measureWidth()
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {
            height = measureHeight()
        }
        setMeasuredDimension(width.toInt(), height.toInt())//存在精度损失
    }

    /**
     * Text 可用宽度
     */
    private fun getTextAvailableWidth(): Double {
        availableWidth = width - paddingLeft.toFloat() - paddingRight.toFloat()
        if (title != null) {
            availableWidth = availableWidth - titleRect.width().toFloat() - textMargin
        }
        if (drawableLeft != null) {
            availableWidth = availableWidth - drawableLeft!!.intrinsicWidth.toFloat() - drawablePadding
        }
        if (drawableRight != null) {
            availableWidth = availableWidth - drawableRight!!.intrinsicWidth.toFloat() - drawablePadding
        }
        return availableWidth.toDouble()
    }


    /**
     * 计算宽度
     */
    private fun measureWidth(): Float {
        var measureWidth = 0f
        //测量title宽度
        if (title != null) {
            paint.textSize = titleSize
            paint.getTextBounds(title, 0, title!!.length, titleRect)
            measureWidth = titleRect.width().toFloat()
        }
        //测量text宽度
        if (text != null) {
            paint.textSize = textSize
            paint.getTextBounds(text, 0, text!!.length, textRect)
            measureWidth += textRect.width()
        }
        //drawableLeft
        if (drawableLeft != null) {
            measureWidth += drawableLeft!!.intrinsicWidth.toFloat() + drawablePadding
        }
        //drawableRight
        if (drawableRight != null) {
            measureWidth += drawableRight!!.intrinsicWidth.toFloat() + drawablePadding
        }
        return measureWidth + paddingLeft.toFloat() + paddingRight.toFloat() + textMargin
    }

    /**
     * 计算高度
     */
    private fun measureHeight(): Float {
        var measureHeight = 0f
        //测量title高度
        if (title != null) {
            paint.textSize = titleSize
            paint.getTextBounds(title, 0, title!!.length, titleRect)
            measureHeight = titleRect.height().toFloat()
        }
        //测量text高度
        if (text != null) {
            paint.textSize = textSize
            paint.getTextBounds(text, 0, text!!.length, textRect)
            measureHeight = Math.max(measureHeight, textRect.height().toFloat())
            isOverLength = getTextAvailableWidth() < textRect.width()
            if (!isSingleLine && isOverLength) {
                measureHeight += textRect.height().toFloat() + lineSpace
            }
        }
        //drawableLeft
        if (drawableLeft != null) {
            measureHeight = max(measureHeight, drawableLeft!!.intrinsicHeight.toFloat())
        }
        //drawableRight
        if (drawableRight != null) {
            measureHeight = max(measureHeight, drawableRight!!.intrinsicHeight.toFloat())
        }
        return measureHeight + paddingTop.toFloat() + paddingBottom.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        canvas.translate(0f, height / 2)
        availableWidth = width - paddingLeft.toFloat() - paddingRight.toFloat()

        //drawableLeft
        if (drawableLeft != null) {
            drawableLeft!!.setBounds(paddingLeft, -drawableLeft!!.intrinsicHeight / 2, paddingLeft + drawableLeft!!.intrinsicWidth, drawableLeft!!.intrinsicHeight / 2)
            drawableLeft!!.draw(canvas)
            availableWidth = availableWidth - drawableLeft!!.intrinsicWidth.toFloat() - drawablePadding
        }

        //drawableRight
        if (drawableRight != null) {
            drawableRight!!.setBounds((width - paddingRight.toFloat() - drawableRight!!.intrinsicWidth.toFloat()).toInt(), -drawableRight!!.intrinsicHeight / 2, (width - paddingRight).toInt(), drawableRight!!.intrinsicHeight / 2)
            drawableRight!!.draw(canvas)
            availableWidth = availableWidth - drawableRight!!.intrinsicWidth.toFloat() - drawablePadding
        }

        //title
        if (title != null) {
            paint.textSize = titleSize
            paint.color = titleColor
            paint.getTextBounds(title, 0, title!!.length, titleRect)
            val left = if (drawableLeft == null) paddingLeft.toFloat() else paddingLeft.toFloat() + drawableLeft!!.intrinsicWidth.toFloat() + drawablePadding
            canvas.drawText(title!!, left, -titleRect.exactCenterY(), paint)
            availableWidth = availableWidth - titleRect.width().toFloat() - textMargin
        }

        //text
        if (text != null) {
            drawText(canvas)
        }

    }

    /**
     * 绘制 text
     */
    private fun drawText(canvas: Canvas) {
        paint.textSize = textSize
        paint.color = textColor
        paint.getTextBounds(text, 0, text!!.length, textRect)

        //超过最大可用宽度，截取文字
        isOverLength = getTextAvailableWidth() < textRect.width()
        if (isOverLength) {
            while (textRect.width() > availableWidth) {
                builder.insert(0, text!!.substring(text!!.length - 1))
                text = text!!.substring(0, text!!.length - 1)
                paint.getTextBounds(text, 0, text!!.length, textRect)
            }
        }

        //左对齐
        if (textAlign == ALIGN_LEFT) {
            drawTextAlignLeft(canvas)
        }

        //右对齐
        if (textAlign == ALIGN_RIGHT) {
            drawTextAlignRight(canvas)
        }
    }

    /**
     * 文字左对齐
     */
    private fun drawTextAlignLeft(canvas: Canvas) {
        var left = paddingLeft.toFloat()
        if (drawableLeft != null) {
            left += drawableLeft!!.intrinsicWidth.toFloat() + drawablePadding
        }
        if (title != null) {
            left += titleRect.width().toFloat() + textMargin
        }

        //超长且单行
        if (isOverLength && isSingleLine) {
            text = text!!.substring(0, text!!.length - 1) + "..."
            canvas.drawText(text!!, left, -textRect.exactCenterY(), paint)
            return
        }
        //超长多行
        if (isOverLength) {
            canvas.drawText(text!!, left, -textRect.bottom - lineSpace / 2, paint)
            canvas.drawText(builder.toString(), left, -textRect.top + lineSpace / 2, paint)
            return
        }
        canvas.drawText(text!!, left, -textRect.exactCenterY(), paint)
    }

    /**
     * 文字右对齐
     */
    private fun drawTextAlignRight(canvas: Canvas) {
        var left = width - paddingRight.toFloat() - textRect.width().toFloat()
        if (drawableRight != null) {
            left = left - drawableRight!!.intrinsicWidth.toFloat() - drawablePadding
        }

        //超长且单行
        if (isOverLength && isSingleLine) {
            text = text!!.substring(0, text!!.length - 1) + "..."
            canvas.drawText(text!!, left, -textRect.exactCenterY(), paint)
            return
        }

        //超长多行
        if (isOverLength) {
            canvas.drawText(text!!, left, -textRect.bottom - lineSpace / 2, paint)
            //测量第二行文字
            val secondLine = builder.toString()
            val rect = Rect()
            paint.getTextBounds(secondLine, 0, secondLine.length, rect)
            var secondLeft = width - paddingRight.toFloat() - rect.width().toFloat()
            if (drawableRight != null) {
                secondLeft = secondLeft - drawableRight!!.intrinsicWidth.toFloat() - drawablePadding
            }
            canvas.drawText(builder.toString(), secondLeft, -textRect.top + lineSpace / 2, paint)
            return
        }
        canvas.drawText(text!!, left, -textRect.exactCenterY(), paint)
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun setTitle(title: String) {
        this.title = title
        invalidate()
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    fun setTitleColor(titleColor: Int) {
        this.titleColor = titleColor
        invalidate()
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        invalidate()
    }

    fun setTitleSize(titleSize: Float) {
        this.titleSize = titleSize
        invalidate()
    }

    fun setDrawableLeft(drawableLeft: Drawable) {
        this.drawableLeft = drawableLeft
        invalidate()
    }

    fun setDrawableRight(drawableRight: Drawable) {
        this.drawableRight = drawableRight
        invalidate()
    }

    fun setDrawablePadding(drawablePadding: Float) {
        this.drawablePadding = drawablePadding
        invalidate()
    }

    fun setTextAlign(textAlign: Int) {
        this.textAlign = textAlign
        invalidate()
    }

    fun getText(): String? {
        return text
    }

    fun getTitle(): String? {
        return title
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun getTitleColor(): Int {
        return titleColor
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun getTitleSize(): Float {
        return titleSize
    }

    fun getDrawableLeft(): Drawable? {
        return drawableLeft
    }

    fun getDrawableRight(): Drawable? {
        return drawableRight
    }

    fun getDrawablePadding(): Float {
        return drawablePadding
    }

    fun getTextAlign(): Int {
        return textAlign
    }


}

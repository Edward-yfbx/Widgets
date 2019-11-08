package com.yfbx.widgets.widget.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yfbx.widgets.util.dp

/**
 * Author: Edward
 * Date: 2018/12/6
 * Description:
 */


class SoundWave @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var paint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var isPlaying: Boolean = false
    private var level = 2


    private val runnable = object : Runnable {
        override fun run() {
            if (level > 2) {
                level = 0
            } else {
                level++
            }
            setLevel(level)
            handler.postDelayed(this, 150)
        }
    }

    init {
        paint = Paint()
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.color = -0x333334
        paint.strokeWidth = 4f
        paint.isAntiAlias = true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = dp(48)
        mHeight = dp(48)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.translate(0f, (mHeight / 2).toFloat())
        drawArc(12f, -4f, 16f, 4f, canvas)
        if (level > 0) {
            drawArc(16f, -8f, 24f, 8f, canvas)
        }
        if (level > 1) {
            drawArc(20f, -12f, 32f, 12f, canvas)
        }
    }

    private fun drawArc(left: Float, top: Float, right: Float, bottom: Float, canvas: Canvas) {
        canvas.drawArc(dp(left), dp(top), dp(right), dp(bottom), -90f, 180f, false, paint)
    }

    private fun setLevel(level: Int) {
        this.level = level
        invalidate()
    }

    fun start() {
        handler.post(runnable)
        isPlaying = true
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        setLevel(2)
        isPlaying = false
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }
}

package com.yfbx.widgets.java

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Author: Edward
 * Date: 2019/1/23
 * Description:
 */

class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()
        val path = Path()
        path.addCircle(width / 2f, height / 2f, width / 2.5f, Path.Direction.CCW)
        canvas.clipPath(path)
        super.onDraw(canvas)
        canvas.restore()
    }


}
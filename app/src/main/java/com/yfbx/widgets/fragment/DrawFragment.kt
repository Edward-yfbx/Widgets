package com.yfbx.widgets.fragment

import android.graphics.*
import android.os.Bundle
import android.view.View
import com.yfbx.widgets.R
import com.yfbx.widgets.util.saveCapture
import com.yfbx.widgets.util.sp
import kotlinx.android.synthetic.main.frag_draw.*


/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:绘制图片
 */
class DrawFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        draw()

        saveBtn.setOnClickListener {
            img.saveCapture()
        }
    }

    override fun getLayout(): Int {
        return R.layout.frag_draw
    }

    private fun draw() {
        //文件容器
        val bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(0x00FFFFFF)//背景透明，默认黑色

        //画布
        val canvas = Canvas(bitmap)
        //画笔
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        //绘制
        canvas.translate(128f, 128f)
        paint.color = Color.WHITE
        canvas.drawCircle(0f, 0f, 100f, paint)

        paint.textSize = sp(36).toFloat()
        paint.color = Color.BLUE
        val rect = Rect()
        val text = "E"
        paint.getTextBounds(text, 0, text.length, rect)
        canvas.drawText(text, (-rect.width() / 2).toFloat(), (rect.height() / 2).toFloat(), paint)

        //展示
        img.setImageBitmap(bitmap)

    }


}

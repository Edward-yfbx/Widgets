package com.yfbx.widgets.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */

/**
 * 保存 Bitmap
 */
fun Bitmap.save(context: Context): Boolean {
    var isSuccess = false
    val path = Environment.DIRECTORY_PICTURES + "/" + System.currentTimeMillis() + ".png"
    val file = File(Environment.getExternalStorageDirectory(), path)
    try {
        val fos = FileOutputStream(file)
        compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        isSuccess = true
        recycle()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    MediaScannerConnection.scanFile(context, arrayOf(file.path), null, null)
    return isSuccess
}

/**
 * 保存Drawable
 */
fun Drawable.save(context: Context): Boolean {
    val bitDrawable = this as BitmapDrawable
    val bitmap = bitDrawable.bitmap
    return bitmap.save(context)
}


/**
 * View 截图
 */
fun View.capture(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    draw(Canvas(bitmap))
    return bitmap
}


/**
 * 保存View 截图
 */
fun View.saveCapture(): Boolean {
    return capture().save(context)
}

package com.yfbx.widgets.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */


/**
 * 将 Bitmap 保存到SD卡
 */
fun Bitmap.save(fileName: String) {
    val sd = Environment.getExternalStorageDirectory().path
    val file = File(sd, "$fileName.png")
    try {
        val outStream = FileOutputStream(file)
        compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
        recycle()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 通知媒体扫描器,扫描媒体文件
 */
fun notifyMediaScanner(context: Context, file: File) {
    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val uri = Uri.fromFile(file)
    intent.data = uri
    context.sendBroadcast(intent)
}

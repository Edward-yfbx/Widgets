package com.yfbx.widgets.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.graphics.scale
import com.yfbx.widgets.R
import com.yfbx.widgets.adapter.adapter
import com.yfbx.widgets.adapter.bind
import com.yfbx.widgets.bean.ImageEntity
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.zibin.luban.Luban
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Author: Edward
 * Date: 2020-08-10
 * Description:图片压缩对比
 */
class ImageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val adapter = adapter {
            bind<ImageEntity>(R.layout.item_image) { helper, item ->
                helper.imageView.setImageBitmap(item.bitmap)
                helper.infoTxt.text = item.desc()
            }
        }

        val src = BitmapFactory.decodeStream(assets.open("test.png"))
        val srcSize = src.size / 1024

        launch {
            val lubanBitmap = src.shrink()
            val scaleBitmap = src.scale(144)

            val data = mutableListOf(
                    ImageEntity(lubanBitmap, "鲁班压缩", srcSize, lubanBitmap.size / 1024),
                    ImageEntity(scaleBitmap, "双线性采样", srcSize, scaleBitmap.size / 1024),
                    ImageEntity(src, "原图", srcSize, srcSize)
            )
            adapter.addAll(data)
        }.invokeOnCompletion {
            it?.printStackTrace()
        }
        viewPager.adapter = adapter
    }

    /**
     * 鲁班压缩
     */
    private suspend fun Bitmap.shrink(): Bitmap {
        return withContext(Dispatchers.IO) {
            val fileName = "temp_${System.currentTimeMillis()}.png"
            val file = File(externalCacheDir, fileName)
            val fos = FileOutputStream(file)
            compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            val compressFile = Luban.with(this@ImageActivity).load(file).get(file.path)
            BitmapFactory.decodeFile(compressFile.path)
        }
    }

    /**
     * 双线性压缩
     * @param size 单位 k
     */
    private suspend fun Bitmap.scale(size: Int): Bitmap {
        val targetSize = size * 1024
        var target = this
        return withContext(Dispatchers.IO) {
            while (target.size > targetSize) {
                target = scale((target.width * 0.99f).toInt(), (target.height * 0.99f).toInt())
            }
            target
        }
    }
}

val Bitmap.size: Int
    get() {
        return getBytes().size
    }

fun Bitmap.getBytes(option: Int = 100): ByteArray {
    require(option in 0..100) { "option should be in 0..100" }
    val output = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, option, output)
    val byte = output.toByteArray()
    output.reset()
    output.close()
    return byte
}

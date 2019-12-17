package com.yfbx.widgets.util

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.FileProvider
import com.yfbx.widgets.BuildConfig
import java.io.File


/**
 * Author: Edward
 * Date: 2019-11-11
 * Description:
 */
class ApkManager(val context: Context) : Runnable {

    private val apkMime = "application/vnd.android.package-archive"
    private var progress: ((loaded: Long, total: Long) -> Unit)? = null
    private val handler = Handler()
    private var id = 0L
    private val manager by lazy { context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager }

    /**
     * 下载
     */
    fun download(url: String, progress: ((loaded: Long, total: Long) -> Unit)? = null) {
        this.progress = progress
        val fileName = url.substring(url.lastIndexOf("/") + 1)
        //请求地址
        val request = DownloadManager.Request(Uri.parse(url))
        //文件目录
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        //文件类型
        request.setMimeType(apkMime)
        //通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        id = manager.enqueue(request)
        handler.post(this)
    }

    /**
     * 查询下载进度
     */
    override fun run() {
        val query = DownloadManager.Query()
        val cursor = manager.query(query.setFilterById(id))
        if (cursor?.moveToFirst() == true) {
            val loaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            if (total > 0) {
                progress?.invoke(loaded, total)
            }

            if (loaded != total) {
                handler.post(this)
            } else {
                val uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                install(uri)
            }
        } else {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 安装APK
     * @param uriStr file:///storage/emulated/0/Download/Yuxiaor_3.0.2_5308-1.apk
     */
    @RequiresPermission("android.permission.REQUEST_INSTALL_PACKAGES")
    private fun install(uriStr: String) {
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val path = uriStr.replace("file://", "")
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", File(path))
        } else {
            Uri.parse(uriStr)
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, apkMime)
        context.startActivity(intent)
    }

}
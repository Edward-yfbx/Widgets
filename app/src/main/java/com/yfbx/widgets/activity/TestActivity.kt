package com.yfbx.widgets.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.yfbx.widgets.R
import com.yfbx.widgets.util.ApkManager
import com.yfbx.widgets.util.format
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Author: Edward
 * Date: 2019-12-17
 * Description:
 */
class TestActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        downloadBtn.setOnClickListener {
            download()
        }
    }


    /**
     * 下载APK
     */
    @SuppressLint("SetTextI18n")
    private fun download() {
        val url = "http://192.168.3.87:8081/release/Yuxiaor_3.0.2_5308.apk"
        ApkManager(this).download(url) { load, total ->
            val loadM = load / 1024.0 / 1024.0
            val totalM = total / 1024.0 / 1024.0

            infoTxt.text = "${loadM.format()} M/${totalM.format()} M"
        }
    }
}
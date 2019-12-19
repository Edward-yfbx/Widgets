package com.yfbx.widgets.activity

import android.net.Uri
import android.os.Bundle
import com.yfbx.widgets.widget.XWebView

/**
 * Author: Edward
 * Date: 2019-09-04
 * Description:
 */
class WebActivity : BaseActivity() {

    private val webView by lazy { XWebView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        if (data != null) {
            startByScheme(data)
        } else {
            startByIntent()
        }
    }


    /**
     * Scheme 启动
     */
    private fun startByScheme(data: Uri) {
        webView.loadUrl(intent.dataString)
    }

    /**
     * 正常启动
     */
    private fun startByIntent() {
        webView.load(intent.getStringExtra("web"))
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.removeAllViews()
        webView.destroy()
    }
}
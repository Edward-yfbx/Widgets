package com.yfbx.widgets.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yfbx.widgets.util.matchParent

/**
 * Author: Edward
 * Date: 2019-09-04
 * Description:
 */
class WebActivity : BaseActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()

        val data = intent.data
        //通过Scheme进来的
        if (data != null) {
            val title = data.getQueryParameter("title") ?: ""
            setToolbar(title)
            webView.loadUrl(intent.dataString)
        } else {
            //正常跳转进来的
            val title = intent.getStringExtra("title")
            setToolbar(title)
            load(intent.getStringExtra("web"))
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView = WebView(this)
        webView.layoutParams = ViewGroup.LayoutParams(matchParent(), matchParent())
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.useWideViewPort = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = WebClient()
    }

    class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }

    private fun load(content: String) {
        if (content.startsWith("http")) {
            webView.loadUrl(content)
            return
        }
        if (content.contains("<!DOCTYPE html>")) {
            webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
            return
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
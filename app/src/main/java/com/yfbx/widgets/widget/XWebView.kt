package com.yfbx.widgets.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.AttributeSet
import android.webkit.*

/**
 * Author: Edward
 * Date: 2019-11-14
 * Description:
 */
@SuppressLint("SetJavaScriptEnabled")
class XWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context.createConfigurationContext(Configuration()), attrs, defStyleAttr) {

    private var interceptor: ((view: WebView?, url: String) -> Unit)? = null

    init {
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.allowFileAccess = true

        settings.allowFileAccessFromFileURLs = true
        settings.allowUniversalAccessFromFileURLs = true
        settings.javaScriptCanOpenWindowsAutomatically = true

        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        settings.domStorageEnabled = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

        webViewClient = WebClient()

        isFocusable = true
        isFocusableInTouchMode = true
    }


    fun load(content: String) {
        if (content.startsWith("http")) {
            loadUrl(content)
            return
        }
        if (content.contains("<!DOCTYPE html>")) {
            loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
            return
        }
    }

    fun loadIntercept(url: String, interceptor: (view: WebView?, url: String) -> Unit) {
        this.interceptor = interceptor
        loadUrl(url)
    }

    inner class WebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()
            //有interceptor 交给 interceptor 处理
            if (interceptor != null) {
                interceptor?.invoke(view, url)
                return true
            }
            //没有interceptor 自动处理
            if (url.startsWith("http")) {
                view?.loadUrl(url)
            } else {
                intentTo(url)
            }
            return true
        }
    }

    private fun intentTo(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse(uri)
        context.startActivity(intent)
    }
}

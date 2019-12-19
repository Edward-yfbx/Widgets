package com.yfbx.widgets.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
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

    fun loadIntercept(url: String, interceptor: (view: WebView, url: String) -> Unit) {
        loadUrl(url)
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                interceptor.invoke(view, request.url.toString())
                return true
            }
        }
    }

    class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }
}
package com.yfbx.widgets.net

import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import com.yfbx.widgets.BuildConfig
import com.yfbx.widgets.dialog.Loading
import com.yfbx.widgets.util.toast
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author Edward
 * @Date 2019/5/24 0024
 * @Description:
 */

const val BASE_URL = "https://www.yuxiaor.com/api/v1/"

/**
 * OkHttp Client
 */
private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .cookieJar(PersistentCookies)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(LoggerInterceptor())
            }
        }
        .retryOnConnectionFailure(true)
        .build()


/**
 * Retrofit with:
 * Header
 * Interceptor
 * Gson Converter
 */
val Net: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


/**
 * 协程-网络请求
 * @param block 网络请求任务
 */
fun CoroutineScope.network(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(CoroutineExceptionHandler { _, throwable ->
        toast(getError(throwable)?.second)
    }, CoroutineStart.DEFAULT, block)
}


/**
 * 生命周期Scope
 * 这种方式不必在Activity 继承 CoroutineScope by MainScope()，OnDestroy()时cancel()协程
 * 但只能在有生命周期的组件中使用
 */
fun ComponentActivity.loading(show: Boolean = true, block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.loading(show, block)
}

fun Fragment.loading(show: Boolean = true, block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.loading(show, block)
}

/**
 * 带loading的网络请求
 */
fun CoroutineScope.loading(show: Boolean = true, block: suspend CoroutineScope.() -> Unit): Job {
    val loading = if (show) Loading.show() else null
    val job = network(block)
    job.invokeOnCompletion { loading?.dismiss() }
    return job
}


fun Job.onError(onError: (code: Int, message: String) -> Unit): Job {
    invokeOnCompletion {
        val error = getError(it)
        if (error != null) {
            onError.invoke(error.first, error.second)
        }
    }
    return this
}

fun getError(throwable: Throwable?): Pair<Int, String>? {
    if (throwable == null || throwable is KotlinNullPointerException) return null
    return when (throwable) {
        is UnknownHostException -> 1 to "网络连接失败，请检查您的网络"
        is SocketTimeoutException -> 2 to "网络连接超时，请检查您的网络"
        is ConnectException -> 3 to "连接服务器失败，请稍后再试"
        is MalformedJsonException -> 4 to "当前为测试环境，请连接内部网络"
        is HttpException -> {
            val response = throwable.response()
            when (response?.code()) {
                401 -> 401 to "请先登录您的账号"
                502 -> 502 to "服务器正在升级，请稍后再试"
                550 -> 550 to "发现新版本，请更新"
                400 -> {
                    val error = response.errorBody()?.string()
                    val map = Gson().fromJson(error, Map::class.java)
                    val codeStr = map["errorCode"] as? String
                    val msg = map["message"] ?: "请求出错，请稍后再试"
                    val code = if (codeStr?.isDigitsOnly() == true) codeStr.toInt() else 0
                    (code) to (msg as String)
                }
                else -> {
                    val code = response?.code() ?: 0
                    val msg = response?.errorBody()?.string() ?: "请求出错，请稍后再试"
                    code to msg
                }
            }
        }
        is RuntimeException -> -1 to (throwable.message ?: "Runtime exception")
        else -> 0 to "请求出错，请稍后再试"
    }
}

fun CharSequence.isDigitsOnly() = TextUtils.isDigitsOnly(this)
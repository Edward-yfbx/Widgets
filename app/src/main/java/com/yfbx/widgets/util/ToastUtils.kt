package com.yfbx.widgets.util

import android.annotation.SuppressLint
import android.widget.Toast
import com.yfbx.widgets.app.App


/**
 * Toast 工具类
 *
 * @author xuzeyang
 * @date 2017/10/25
 */
private var toast: Toast? = null

@SuppressLint("ShowToast")
fun toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (message == null) return
    toast?.cancel()
    toast = Toast.makeText(App.context, message, duration)
    toast?.show()
}

@SuppressLint("ShowToast")
fun toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = Toast.makeText(App.context, message, duration)
    toast?.show()
}
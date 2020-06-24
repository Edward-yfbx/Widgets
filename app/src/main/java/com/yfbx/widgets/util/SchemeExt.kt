package com.yfbx.widgets.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yfbx.widgets.activity.WebActivity

/**
 * Author: Edward
 * Date: 2019-10-29
 * Description:
 */


/**
 * Scheme 跳转
 */
fun Context.startScheme(uri: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setPackage(packageName)
    intent.data = Uri.parse(uri)
    if (uri.startsWith("http") || uri.startsWith("file")) {
        intent.setClassName(packageName, WebActivity::class.java.name)
    }
    startActivity(intent)
}

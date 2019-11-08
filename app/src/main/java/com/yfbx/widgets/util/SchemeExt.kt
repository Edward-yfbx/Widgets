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
fun Context.startScheme(uri: String, clearTask: Boolean = false) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(uri)
    if (clearTask) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    if (uri.startsWith("http") || uri.startsWith("file")) {
        intent.setClassName(packageName, WebActivity::class.java.name)
    }
    startActivity(intent)
}


object Schemes {

    /**
     * 用户信息安全保密协议
     */
    const val PRIVACY = "file:///android_asset/personal_security.html?title=用户信息安全保密协议"
    /**
     * 寓工厂
     */
    const val YU_FACTORY = "https://shop43730129.youzan.com/v2/showcase/homepage?alias=uIVQfrb2Vp&title=寓工厂"
}

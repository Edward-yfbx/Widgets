package com.yfbx.widgets.bean

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
enum class Menu(val title: String, val scheme: String, val params: Map<String, Any>? = null) {

    ANIM("Anim", "anim://"),
    CHART("Chart", "chart://"),
    DRAW("Draw", "draw://"),
    RADIO("Radio", "radio://"),
    ROLL("Roll", "roll://"),
    TEXT("Text", "text://"),
    WEB("Web", "file:///android_asset/personal_security.html?title=用户信息安全保密协议"),
    TEST("Test", "test://")

}
package com.yfbx.widgets.bean

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
enum class Menu(val title: String, val scheme: String) {

    ANIM("Anim", "yfbx://yfbx.com/anim"),
    DRAW("Draw", "yfbx://yfbx.com/draw"),
    RADIO("Radio", "yfbx://yfbx.com/radio"),
    ROLL("Roll", "yfbx://yfbx.com/roll"),
    TEXT("Text", "yfbx://yfbx.com/text"),
    WEB("Web", "file:///android_asset/personal_security.html?title=用户信息安全保密协议"),
    TEST("Test", "yfbx://yfbx.com/test")

}
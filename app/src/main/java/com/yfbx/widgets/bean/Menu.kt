package com.yfbx.widgets.bean

/**
 * Author: Edward
 * Date: 2019-12-19
 * Description:
 */
enum class Menu(val title: String, val scheme: String, val params: Map<String, Any>? = null) {

    ANIM("Anim", ""),
    TEXT("Value Text", ""),
    RADIO("Radio Button", ""),
    TEST("test", "")

}
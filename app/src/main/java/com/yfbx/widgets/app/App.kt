package com.yfbx.widgets.app

import android.app.Application
import android.content.ContextWrapper
import android.graphics.Color
import android.os.IBinder
import android.view.ContextThemeWrapper
import com.billy.android.swipe.SmartSwipeBack
import com.yfbx.widgets.activity.MainActivity

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


class App : Application() {

    companion object {
        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        slideBack()
    }


    /**
     * 侧滑返回
     */
    private fun slideBack() {
        SmartSwipeBack.activitySlidingBack(this, { it !is MainActivity }, 100, Color.parseColor("#80000000"), Color.TRANSPARENT, 0, 0f, 1)
    }
}

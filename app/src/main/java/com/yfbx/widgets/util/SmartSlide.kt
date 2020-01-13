package com.yfbx.widgets.util

import android.app.Activity
import android.graphics.Color
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.yfbx.widgets.R

/**
 * Author: Edward
 * Date: 2020-01-11
 * Description:
 */
fun Activity.slideBack(): SwipeConsumer {
    val consumer = ActivitySlidingBackConsumer(this)
            .setScrimColor(Color.parseColor("#80000000"))
            .setShadowColor(Color.TRANSPARENT)
            .enableLeft()
            .addListener(object : SimpleSwipeListener() {
                override fun onSwipeOpened(wrapper: SmartSwipeWrapper?, consumer: SwipeConsumer?, direction: Int) {
                    onBackPressed()
                    overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                }
            })
    SmartSwipe.wrap(this).addConsumer(consumer)
    return consumer
}

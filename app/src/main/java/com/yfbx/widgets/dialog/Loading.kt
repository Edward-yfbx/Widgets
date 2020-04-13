package com.yfbx.widgets.dialog

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import com.yfbx.widgets.R
import com.yfbx.widgets.app.App
import kotlinx.android.synthetic.main.window_loading.*


/**
 * @Author: Edward
 * @Date: 2019-07-20
 * @Description:
 */
class Loading : FloatingWindow(App.context) {


    companion object {
        fun show(): Loading {
            val loading = Loading()
            loading.show()
            return loading
        }
    }


    override fun onCreate(context: Context) {
        setContentView(R.layout.window_loading)
        alpha = 0.8f
        startAnim(loading)
    }

    private fun startAnim(view: View): ObjectAnimator {
        val rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 359f)
        rotation.repeatCount = ObjectAnimator.INFINITE
        rotation.interpolator = LinearInterpolator()
        rotation.duration = 1000
        rotation.start()
        return rotation
    }
}
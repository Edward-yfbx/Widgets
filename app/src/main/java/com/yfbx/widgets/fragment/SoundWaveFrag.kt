package com.yfbx.widgets.fragment

import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import com.yfbx.widgets.R
import com.yfbx.widgets.widget.anim.SoundWave

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


class SoundWaveFrag : BaseFragment() {

    private var soundWave: SoundWave? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        soundWave = view.findViewById(R.id.sound_wave)
    }

    override fun getLayout(): Int {
        return R.layout.frag_sound_wave
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            soundWave!!.stop()
        } else {
            soundWave!!.start()
        }
        return true
    }

}

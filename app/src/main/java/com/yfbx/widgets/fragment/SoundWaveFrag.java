package com.yfbx.widgets.fragment;

import android.view.MotionEvent;

import com.yfbx.widgets.R;
import com.yfbx.widgets.widgets.anim.SoundWave;

import butterknife.BindView;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class SoundWaveFrag extends BaseFragment {


    @BindView(R.id.sound_wave)
    SoundWave soundWave;

    @Override
    public int getLayout() {
        return R.layout.frag_sound_wave;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            soundWave.stop();
        } else {
            soundWave.start();
        }
        return true;
    }

}

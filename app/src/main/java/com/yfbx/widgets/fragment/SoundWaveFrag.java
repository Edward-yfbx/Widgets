package com.yfbx.widgets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.yfbx.widgets.R;
import com.yfbx.widgets.java.anim.SoundWave;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class SoundWaveFrag extends BaseFragment {

    private SoundWave soundWave;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        soundWave = view.findViewById(R.id.sound_wave);
    }

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

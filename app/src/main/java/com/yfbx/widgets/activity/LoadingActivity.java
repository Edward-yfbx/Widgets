package com.yfbx.widgets.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.yfbx.widgets.R;
import com.yfbx.widgets.anim.LoadingView;
import com.yfbx.widgets.anim.SoundWave;

/**
 * Author: Edward
 * Date: 2018/7/16
 * Description:
 */


public class LoadingActivity extends BaseActivity {


    LoadingView loadingView;
    SoundWave soundWave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingView = findViewById(R.id.loading_view);
        soundWave = findViewById(R.id.sound_wave);
        loadingView.start();
        soundWave.start();
    }

    @Override
    public int attachLayout() {
        return R.layout.activity_circle_anim;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (loadingView.isLoading()) {
                loadingView.stop();
                soundWave.stop();
            } else {
                loadingView.start();
                soundWave.start();
            }
        }
        return true;
    }
}

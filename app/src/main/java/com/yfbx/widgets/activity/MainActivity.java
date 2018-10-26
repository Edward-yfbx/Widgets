package com.yfbx.widgets.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yfbx.widgets.R;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onTextBtnClicked(View view) {
        startActivity(new Intent(this, TextActivity.class));
    }

    public void onRoundBtnClicked(View view) {
        startActivity(new Intent(this, RoundBtnActivity.class));
    }

    public void onCarouselClick(View view) {
        startActivity(new Intent(this, RecyclerCarousel.class));
    }
}

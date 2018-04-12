package com.yfbx.widgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements RoundBtn.OnCheckChangeListener {

    RoundBtn rb1;
    RoundBtn rb2;
    RoundBtn rb3;
    RoundBtn rb5;
    RoundBtn rb6;
    RoundBtn rb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);

        rb1.setOnCheckChangeListener(this);
        rb2.setOnCheckChangeListener(this);
        rb3.setOnCheckChangeListener(this);
        rb4.setOnCheckChangeListener(this);
        rb5.setOnCheckChangeListener(this);
        rb6.setOnCheckChangeListener(this);
        rb6.setOnCheckChangeListener(this);

    }

    /**
     * 界面绘制完成
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        rb1.setChecked(true);
    }

    @Override
    public void onCheckChange(RoundBtn btn, boolean isChecked) {
        float size = isChecked ? 28 : 20;
        float adjust = isChecked ? 4 : 0;
        btn.setTitleSize(size, adjust);
    }

}

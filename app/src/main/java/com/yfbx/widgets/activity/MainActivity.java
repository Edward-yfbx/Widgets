package com.yfbx.widgets.activity;

import android.content.Intent;
import android.os.Bundle;

import com.yfbx.widgets.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int attachLayout() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.textBtn)
    public void onTextBtnClicked() {
        startActivity(new Intent(this, TextActivity.class));
    }

    @OnClick(R.id.roundBtn)
    public void onRoundBtnClicked() {
        startActivity(new Intent(this, RoundBtnActivity.class));
    }
}

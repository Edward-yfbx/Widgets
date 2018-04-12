package com.yfbx.widgets.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:Edward
 * Date:2018/4/12
 * Description:
 */

public abstract class BaseActivity extends AppCompatActivity {

    Unbinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayout());
        binder = ButterKnife.bind(this);
    }

    @LayoutRes
    public abstract int attachLayout();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}

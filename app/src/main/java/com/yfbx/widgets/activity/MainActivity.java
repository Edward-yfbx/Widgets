package com.yfbx.widgets.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.yfbx.resulthelper.ResultHelper;
import com.yfbx.widgets.R;
import com.yfbx.widgets.fragment.DrawFragment;
import com.yfbx.widgets.fragment.LoadingFrag;
import com.yfbx.widgets.fragment.RadioBtnFrag;
import com.yfbx.widgets.fragment.RollRecyclerFrag;
import com.yfbx.widgets.fragment.SelectorFrag;
import com.yfbx.widgets.fragment.SoundWaveFrag;
import com.yfbx.widgets.fragment.ValueTextFrag;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Fragment oldFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        setToolbar();
        setClick();
        switchFragment(new LoadingFrag());
        requestPermission();
    }


    private void requestPermission() {
        ResultHelper.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, b -> {
        });
    }


    /**
     * Toolbar
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(Gravity.START));
    }

    private void setClick() {
        findViewById(R.id.value_txt).setOnClickListener(this::onViewClicked);
        findViewById(R.id.radio_btn).setOnClickListener(this::onViewClicked);
        findViewById(R.id.selector_test).setOnClickListener(this::onViewClicked);
        findViewById(R.id.loading_view).setOnClickListener(this::onViewClicked);
        findViewById(R.id.sound_wave).setOnClickListener(this::onViewClicked);
        findViewById(R.id.roll_view).setOnClickListener(this::onViewClicked);
        findViewById(R.id.draw_img).setOnClickListener(this::onViewClicked);
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.value_txt:
                switchFragment(new ValueTextFrag());
                break;
            case R.id.radio_btn:
                switchFragment(new RadioBtnFrag());
                break;
            case R.id.selector_test:
                switchFragment(new SelectorFrag());
                break;
            case R.id.loading_view:
                switchFragment(new LoadingFrag());
                break;
            case R.id.sound_wave:
                switchFragment(new SoundWaveFrag());
                break;
            case R.id.roll_view:
                switchFragment(new RollRecyclerFrag());
                break;
            case R.id.draw_img:
                switchFragment(new DrawFragment());
                break;
        }
        drawer.closeDrawer(Gravity.START);
    }


    /**
     * 切换Fragment
     */
    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.content_view, fragment);
        }
        if (oldFrag != null) {
            transaction.hide(oldFrag);
        }
        transaction.show(fragment);
        oldFrag = fragment;
        transaction.commit();
    }


}

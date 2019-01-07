package com.yfbx.widgets.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private Fragment oldFrag;
    private LoadingFrag loadingFrag = new LoadingFrag();
    private RadioBtnFrag radioBtnFrag = new RadioBtnFrag();
    private SelectorFrag selectorFrag = new SelectorFrag();
    private SoundWaveFrag soundWaveFrag = new SoundWaveFrag();
    private ValueTextFrag valueTextFrag = new ValueTextFrag();
    private RollRecyclerFrag rollRecyclerFrag = new RollRecyclerFrag();
    private DrawFragment drawFragment = new DrawFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        switchFragment(loadingFrag);
        requestPermission();
    }


    private void requestPermission() {
        ResultHelper.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, b -> {
        });
    }

    @Override
    public int attachLayout() {
        return R.layout.activity_main;
    }

    /**
     * Toolbar
     */
    protected void setToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(Gravity.START));
    }

    @OnClick({R.id.value_txt, R.id.radio_btn, R.id.selector_test, R
            .id.loading_view, R.id.sound_wave, R.id.roll_view, R.id.draw_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.value_txt:
                switchFragment(valueTextFrag);
                break;
            case R.id.radio_btn:
                switchFragment(radioBtnFrag);
                break;
            case R.id.selector_test:
                switchFragment(selectorFrag);
                break;
            case R.id.loading_view:
                switchFragment(loadingFrag);
                break;
            case R.id.sound_wave:
                switchFragment(soundWaveFrag);
                break;
            case R.id.roll_view:
                switchFragment(rollRecyclerFrag);
                break;
            case R.id.draw_img:
                switchFragment(drawFragment);
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

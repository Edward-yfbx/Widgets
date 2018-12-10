package com.yfbx.widgets.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {


    //记录所有活动的Activity
    private final List<BaseActivity> mActivities = new LinkedList<>();
    private Unbinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLandscape(false);
        super.onCreate(savedInstanceState);
        mActivities.add(this);
        setContentView(attachLayout());
        binder = ButterKnife.bind(this);
    }

    /**
     * 布局ID
     */
    @LayoutRes
    public abstract int attachLayout();


    /**
     * 全屏
     */
    protected void setFullScreen() {
        int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flagFullscreen, flagFullscreen);
    }

    /**
     * 屏幕常亮
     */
    protected void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 固定横竖屏
     */
    protected void setLandscape(boolean isLandscape) {
        int orientation = isLandscape ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
    }


    /**
     * 关闭所有Activity
     */
    public void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.remove(this);
        if (binder != null) {
            binder.unbind();
        }
    }
}

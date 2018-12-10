package com.yfbx.widgets.widgets;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:适用于需要控制滚动速度的情况，如：控件宽或高过小，使用PagerSnapHelper时，滚动速度过快，动画效果不好
 * 若不需要控制滚动速度，可直接使用PagerSnapHelper，定时调用scrollToPosition()
 */
public class RollRecyclerView extends RecyclerView implements LifecycleObserver {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private PagerSnapHelper snapHelper;
    private Handler handler = new Handler();
    private int duration = 5000;//默认间隔时间
    private int perScrollPixel = 5;
    private int orientation = VERTICAL;

    public RollRecyclerView(Context context) {
        this(context, null);
    }

    public RollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        Context context = getContext();
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            activity.getLifecycle().addObserver(this);
        }
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

    /**
     * 设置轮播间隔时间
     */
    public void setRollDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 设置滚动方向，每次滚动距离
     */
    public void setScrollParams(int orientation, int perScrollPixel) {
        this.orientation = orientation;
        this.perScrollPixel = perScrollPixel;
    }

    /**
     * 开始轮播
     */
    public void start() {
        handler.post(roll);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        handler.removeCallbacks(roll);
        handler.removeCallbacks(anim);
    }

    /**
     * 轮播
     */
    private Runnable roll = new Runnable() {
        @Override
        public void run() {
            handler.post(anim);
            handler.postDelayed(roll, duration);
        }
    };

    /**
     * 滚动
     */
    private Runnable anim = new Runnable() {
        @Override
        public void run() {
            int distance = toScroll();
            if (distance != 0) {
                handler.post(anim);
            } else {
                handler.removeCallbacks(anim);
            }
        }
    };

    private int toScroll() {
        if (orientation == VERTICAL) {
            scrollBy(0, perScrollPixel);
        } else {
            scrollBy(perScrollPixel, 0);
        }

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        View snapView = snapHelper.findSnapView(layoutManager);
        if (snapView != null) {
            int[] ints = snapHelper.calculateDistanceToFinalSnap(layoutManager, snapView);
            if (ints != null) {
                return orientation == VERTICAL ? ints[1] : ints[0];
            }
        }
        return 0;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume(LifecycleOwner owner) {
        start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause(LifecycleOwner owner) {
        stop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}

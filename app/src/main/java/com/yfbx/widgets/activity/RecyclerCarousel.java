package com.yfbx.widgets.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfbx.widgets.R;
import com.yfbx.widgets.adapter.CarouselAdapter;
import com.yfbx.widgets.util.ScrollRunnable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author:Edward
 * Date:2018/10/26
 * Description:
 */

public class RecyclerCarousel extends BaseActivity implements ScrollRunnable.ScrollHelper {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private Handler handler = new Handler();
    private List<String> data = new ArrayList<>();
    private CarouselAdapter adapter;
    private PagerSnapHelper snapHelper;
    private ScrollRunnable scrollAction;
    private ScrollRunnable scrollAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CarouselAdapter(data);
        recycler.setAdapter(adapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        scrollAction = new ScrollRunnable(this, SCROLL_ACTION);
        scrollAnim = new ScrollRunnable(this, SCROLL_ANIM);
        initTestData();
    }

    @Override
    public int attachLayout() {
        return R.layout.activity_recycler_carousel;
    }

    private void initTestData() {
        data.add("【日程】1-XXX执法，李某，2018-10-10");
        data.add("【日程】2-YYY执法，王某，2018-10-10");
        data.add("【日程】3-ZZZ执法，张某，2018-10-10");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScroll();
    }

    @Override
    public void startScroll() {
        handler.postDelayed(scrollAction, 3000);
    }

    @Override
    public void startAnim() {
        handler.post(scrollAnim);
    }

    @Override
    public int toScroll() {
        recycler.scrollBy(0, 5);
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        View snapView = snapHelper.findSnapView(layoutManager);
        int[] ints = snapHelper.calculateDistanceToFinalSnap(layoutManager, snapView);
        return ints[1];
    }

    @Override
    public void stopScroll() {
        handler.removeCallbacks(scrollAction);
        handler.removeCallbacks(scrollAnim);
    }
}

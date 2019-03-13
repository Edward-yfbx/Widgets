package com.yfbx.widgets.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yfbx.widgets.R;
import com.yfbx.widgets.adapter.CarouselAdapter;
import com.yfbx.widgets.widgets.RollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class RollRecyclerFrag extends BaseFragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RollRecyclerView recycler = view.findViewById(R.id.recycler);

        List<String> data = new ArrayList<>();
        data.add("【日程】1-XXX执法，李某，2018-10-10");
        data.add("【日程】2-YYY执法，王某，2018-10-10");
        data.add("【日程】3-ZZZ执法，张某，2018-10-10");

        CarouselAdapter adapter = new CarouselAdapter(data);
        recycler.setAdapter(adapter);
        recycler.setRollDuration(3000);
    }

    @Override
    public int getLayout() {
        return R.layout.frag_rolll_recycler;
    }

}

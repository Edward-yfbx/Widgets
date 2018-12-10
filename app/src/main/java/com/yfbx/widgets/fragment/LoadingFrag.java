package com.yfbx.widgets.fragment;

import com.yfbx.widgets.R;
import com.yfbx.widgets.widgets.anim.LoadingView;

import butterknife.BindView;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class LoadingFrag extends BaseFragment {


    @BindView(R.id.loading_view)
    LoadingView loadingView;


    @Override
    public int getLayout() {
        return R.layout.frag_loading_view;
    }

}

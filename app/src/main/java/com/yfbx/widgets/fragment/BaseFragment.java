package com.yfbx.widgets.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:Edward
 * Date:2018/10/29
 * Description:
 */

public abstract class BaseFragment extends Fragment {

    Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    public abstract int getLayout();


    @Override
    public void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}

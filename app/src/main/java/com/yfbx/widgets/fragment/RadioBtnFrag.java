package com.yfbx.widgets.fragment;

import android.view.View;

import com.yfbx.widgets.R;
import com.yfbx.widgets.util.ToastUtil;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class RadioBtnFrag extends BaseFragment {


    @Override
    public int getLayout() {
        return R.layout.frag_radio_btn;
    }

    public void onRadioClick(View view) {
        ToastUtil.show("点击事件");
    }

}

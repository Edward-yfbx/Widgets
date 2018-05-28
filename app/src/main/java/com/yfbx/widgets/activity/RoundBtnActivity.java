package com.yfbx.widgets.activity;

import android.os.Bundle;

import com.yfbx.widgets.R;
import com.yfbx.widgets.widgets.RoundBtn;

import butterknife.BindView;

/**
 * Author:Edward
 * Date:2018/4/12
 * Description:
 */

public class RoundBtnActivity extends BaseActivity implements RoundBtn.OnCheckChangeListener {

    @BindView(R.id.rb1)
    RoundBtn rb1;
    @BindView(R.id.rb2)
    RoundBtn rb2;
    @BindView(R.id.rb3)
    RoundBtn rb3;
    @BindView(R.id.rb4)
    RoundBtn rb4;
    @BindView(R.id.rb5)
    RoundBtn rb5;
    @BindView(R.id.rb6)
    RoundBtn rb6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int attachLayout() {
        return R.layout.activity_round_btn;
    }

    /**
     * 界面绘制完成
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        rb1.setChecked(true);
    }

    @Override
    public void onCheckChange(RoundBtn btn, boolean isChecked) {
        btn.setTitleSize(isChecked ? 28 : 20);
    }

}

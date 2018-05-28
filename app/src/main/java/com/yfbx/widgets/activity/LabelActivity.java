package com.yfbx.widgets.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.donkingliang.labels.LabelsView;
import com.yfbx.widgets.R;

import java.util.ArrayList;

/**
 * Author:Edward
 * Date:2018/5/16
 * Description:
 */

public class LabelActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabelsView labelsView = findViewById(R.id.labels);
        ArrayList<String> label = new ArrayList<>();
        label.add("Android");
        label.add("IOS");
        label.add("前端");
        label.add("后台");
        label.add("微信开发");
        label.add("游戏开发");
        label.add("油烟浓度");
        label.add("油烟温度");
        label.add("油烟湿度");
        labelsView.setLabels(label); //直接设置一个字符串数组就可以了。
    }

    @Override
    public int attachLayout() {
        return R.layout.activity_label;
    }
}

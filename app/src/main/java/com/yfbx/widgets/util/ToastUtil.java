package com.yfbx.widgets.util;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.yfbx.widgets.App;


/**
 * Author:Edward
 * Date:2018/8/27
 * Description:
 */

public class ToastUtil {

    private static Toast toast;


    public static void show(@StringRes int strRes) {
        show(App.getInstance().getString(strRes));
    }

    public static void show(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

}

package com.yfbx.widgets;

import android.app.Application;

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


public class App extends Application {

    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static App getInstance() {
        return instance;
    }
}

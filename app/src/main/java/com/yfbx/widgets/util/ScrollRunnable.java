package com.yfbx.widgets.util;


/**
 * Author:Edward
 * Date:2018/10/25
 * Description:
 */

public class ScrollRunnable implements Runnable {

    private ScrollHelper helper;
    private int type;

    public ScrollRunnable(ScrollHelper helper, int type) {
        this.helper = helper;
        this.type = type;
    }

    @Override
    public void run() {
        if (type == ScrollHelper.SCROLL_ACTION) {
            helper.startAnim();
            helper.startScroll();
        }

        if (type == ScrollHelper.SCROLL_ANIM) {
            int distance = helper.toScroll();
            if (distance != 0) {
                helper.startAnim();
            }
        }
    }


    public interface ScrollHelper {

        int SCROLL_ACTION = 0;
        int SCROLL_ANIM = 1;

        void startScroll();

        void startAnim();

        int toScroll();

        void stopScroll();
    }
}

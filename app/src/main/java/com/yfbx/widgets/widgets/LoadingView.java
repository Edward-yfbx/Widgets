package com.yfbx.widgets.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: Edward
 * Date: 2018/7/16
 * Description:
 */


public class LoadingView extends View {

    private static final String TAG = "CircleAnim";

    private Paint paint;
    private int degree;
    private int width;
    private int height;
    private boolean loading;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        int[] colors = {0xFF231EFB, 0xFF8710F8, 0xFFCB05FF};
        SweepGradient sweepGradient = new SweepGradient(500, 400, colors, null);

        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setShader(sweepGradient);
        paint.setAntiAlias(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = 128;
        height = 128;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(8, 8, 120, 120, degree, 300, false, paint);
        if (loading) {
            setRotate();
        }
    }

    private void setRotate() {
        degree += 10;
        if (degree >= 360) {
            degree = 0;
        }
        invalidate();
    }

    public void start() {
        loading = true;
        invalidate();
    }

    public void stop() {
        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

}

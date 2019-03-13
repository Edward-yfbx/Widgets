package com.yfbx.widgets.java.anim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: Edward
 * Date: 2018/12/6
 * Description:
 */


public class SoundWave extends View {

    private Paint paint;
    private int width;
    private int height;
    private boolean isPlaying;
    private int level = 2;
    private Handler handler = new Handler();


    public SoundWave(Context context) {
        this(context, null);
    }

    public SoundWave(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoundWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SoundWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFCCCCCC);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = (int) dp2px(48);
        height = (int) dp2px(48);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0, height / 2);
        drawArc(12, -4, 16, 4, canvas);
        if (level > 0) {
            drawArc(16, -8, 24, 8, canvas);
        }
        if (level > 1) {
            drawArc(20, -12, 32, 12, canvas);
        }
    }

    private void drawArc(float left, float top, float right, float bottom, Canvas canvas) {
        canvas.drawArc(dp2px(left), dp2px(top), dp2px(right), dp2px(bottom), -90, 180, false, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() != MotionEvent.ACTION_UP || performClick();
    }

    @Override
    public boolean performClick() {
        if (isPlaying) {
            stop();
        } else {
            start();
        }
        return super.performClick();
    }

    private void setLevel(int level) {
        this.level = level;
        invalidate();
    }

    public void start() {
        handler.post(runnable);
        isPlaying = true;
    }

    public void stop() {
        handler.removeCallbacks(runnable);
        setLevel(2);
        isPlaying = false;
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (level > 2) {
                level = 0;
            } else {
                level++;
            }
            setLevel(level);
            handler.postDelayed(runnable, 150);
        }
    };


    /**
     * dp 转换为 px
     */
    private float dp2px(float value) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }

}

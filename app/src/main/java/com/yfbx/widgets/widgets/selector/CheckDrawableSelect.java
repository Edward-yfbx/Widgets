package com.yfbx.widgets.widgets.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/10/26
 * Description:
 */

public class CheckDrawableSelect extends View implements Checkable {

    private float width;
    private float height;

    private boolean checked;
    private Drawable src;

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    public CheckDrawableSelect(Context context) {
        this(context, null);
    }

    public CheckDrawableSelect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckDrawableSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CheckDrawableSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckDrawableSelect);
        checked = array.getBoolean(R.styleable.CheckDrawableSelect_android_checked, false);
        src = array.getDrawable(R.styleable.CheckDrawableSelect_android_src);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = src.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight();
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = src.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension((int) width, (int) height);//存在精度损失
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int imgWidth = src.getIntrinsicWidth();
        int imgHeight = src.getIntrinsicHeight();
        int left = (int) ((width - imgWidth) / 2);
        int top = (int) ((height - imgHeight) / 2);
        src.setState(getDrawableState());
        src.setBounds(left, top, left + imgWidth, top + imgHeight);
        src.draw(canvas);

    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECKED_STATE_SET);
        }
        return states;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() != MotionEvent.ACTION_UP || performClick();
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }


    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

}

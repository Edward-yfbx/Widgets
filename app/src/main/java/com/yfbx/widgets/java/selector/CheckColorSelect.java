package com.yfbx.widgets.java.selector;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/10/26
 * Description:
 */

public class CheckColorSelect extends View implements Checkable {

    private TextPaint paint;
    private float width;
    private float height;

    private ColorStateList colorStateList;
    private boolean checked;
    private String text;

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    public CheckColorSelect(Context context) {
        this(context, null);
    }

    public CheckColorSelect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckColorSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CheckColorSelect(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs);
        init();
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckColorSelect);
        checked = array.getBoolean(R.styleable.CheckColorSelect_android_checked, false);
        text = array.getString(R.styleable.CheckColorSelect_android_text);
        colorStateList = array.getColorStateList(R.styleable.CheckColorSelect_android_textColor);
        array.recycle();
    }


    private void init() {
        paint = new TextPaint();
        paint.setTextSize(sp2px(14));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Rect textBounds = getTextBounds();
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = textBounds.width() + getPaddingLeft() + getPaddingRight();
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = textBounds.height() + getPaddingTop() + getPaddingBottom();
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
        canvas.translate(0, height / 2);
        int color = colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
        paint.setColor(color);
        Rect textBounds = getTextBounds();
        canvas.drawText(text, getPaddingLeft(), -textBounds.centerY(), paint);

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

    private Rect getTextBounds() {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
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

    /**
     * sp 转换为 px
     */
    protected float sp2px(float value) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics);
    }

    /**
     * dp 转换为 px
     */
    private float dp2px(float value) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }
}

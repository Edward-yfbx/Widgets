package com.yfbx.widgets.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/10/22
 * Description:
 */

public class RadioBtn extends View {

    private static final String TAG = "RadioBtn";

    private Paint circlePaint;
    private TextPaint textPaint;
    private TextPaint descPaint;
    private float width;
    private float height;

    //属性
    private boolean checked;
    private String text;
    private int textColor;
    private float textSize;
    private String desc;
    private int descColor;
    private float descSize;

    private float radiusL;//大圆半径
    private float radiusS;//小圆半径
    private int[] gradient;//渐变色


    public RadioBtn(Context context) {
        this(context, null);
    }

    public RadioBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RadioBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs);
        init();
    }

    /**
     * 获取属性
     */
    private void getAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RadioBtn);
        checked = array.getBoolean(R.styleable.RadioBtn_android_checked, false);
        int startColor = array.getColor(R.styleable.RadioBtn_android_startColor, Color.GRAY);
        int centerColor = array.getColor(R.styleable.RadioBtn_android_centerColor, Color.GRAY);
        int endColor = array.getColor(R.styleable.RadioBtn_android_endColor, Color.GRAY);
        gradient = new int[]{startColor, centerColor, endColor};

        text = array.getString(R.styleable.RadioBtn_android_text);
        textColor = array.getColor(R.styleable.RadioBtn_android_textColor, Color.DKGRAY);
        textSize = array.getDimension(R.styleable.RadioBtn_android_textSize, sp2px(20));

        desc = array.getString(R.styleable.RadioBtn_desc);
        descColor = array.getColor(R.styleable.RadioBtn_descColor, Color.DKGRAY);
        descSize = array.getDimension(R.styleable.RadioBtn_descSize, sp2px(12));

        array.recycle();
    }


    /**
     * 初始化
     */
    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);

        descPaint = new TextPaint();
        descPaint.setStyle(Paint.Style.FILL);
        descPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //width
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = dp2px(80);
        }

        //height
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = width;
        }
        setMeasuredDimension((int) width, (int) height);//存在精度损失
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(width / 2, 0);

        drawCircle(canvas);
        drawText(canvas);
        drawDescription(canvas);

    }


    /**
     * 绘制渐变圆
     */
    private void drawCircle(Canvas canvas) {
        //大圆半径是控件最小边长的一半
        radiusL = Math.min(width, height) / 2;
        //小圆半径自定义合适大小
        radiusS = radiusL * 0.65f;
        //根据选中状态绘制圆
        float radius = checked ? radiusL : radiusS;
        //设置渐变色
        circlePaint.setShader(new LinearGradient(radius, radius * 2, -radius, 0, gradient, null, Shader.TileMode.CLAMP));
        //绘制圆
        canvas.drawCircle(0, radius, radius, circlePaint);
    }

    /**
     * 绘制中心文字
     */
    private void drawText(Canvas canvas) {
        if (text != null) {
            textPaint.setColor(checked ? Color.WHITE : Color.DKGRAY);
            textSize = checked ? sp2px(32) : sp2px(20);
            Rect rect = measureText(textPaint, text, textSize);
            float left = -textPaint.measureText(text) / 2;
            float top = radiusS - rect.exactCenterY();
            canvas.drawText(text, left, top, textPaint);
        }
    }

    /**
     * 绘制底部描述文字
     */
    private void drawDescription(Canvas canvas) {
        if (desc != null) {
            descPaint.setColor(checked ? Color.WHITE : Color.DKGRAY);
            Rect rect = measureText(descPaint, desc, descSize);
            float left = -descPaint.measureText(desc) / 2;
            float top = checked ? height - radiusS - rect.centerY() : height + rect.centerY();
            canvas.drawText(desc, left, top, descPaint);
        }
    }

    /**
     * 测量文字
     */
    private Rect measureText(TextPaint paint, String text, float size) {
        Rect rect = new Rect();
        paint.setTextSize(size);
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }


    /**
     * Touch事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() != MotionEvent.ACTION_UP || performClick();
    }

    /**
     * 点击事件
     */
    @Override
    public boolean performClick() {
        if (checked) {
            setChecked(false);
        } else {
            checkReset();
            setChecked(true);
        }
        return super.performClick();
    }

    /**
     * 重置LinearLayout 中子View的选中状态
     */
    public void checkReset() {
        ViewParent parent = getParent();
        if (!(parent instanceof LinearLayout)) {
            return;
        }

        LinearLayout group = (LinearLayout) parent;
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            if (child instanceof RadioBtn) {
                RadioBtn tab = (RadioBtn) child;
                if (tab.isChecked()) {
                    tab.setChecked(false);
                }
            }
        }
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        invalidate();
    }

    public int getDescColor() {
        return descColor;
    }

    public void setDescColor(int descColor) {
        this.descColor = descColor;
        invalidate();
    }

    public float getDescSize() {
        return descSize;
    }

    public void setDescSize(float descSize) {
        this.descSize = descSize;
        invalidate();
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
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

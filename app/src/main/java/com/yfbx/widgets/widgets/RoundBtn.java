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
import android.text.TextUtils;
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
 * Date:2018/4/9
 * Description:
 */

public class RoundBtn extends View {

    private String title;
    private float titleSize;
    private float subSize;
    private String sub1;
    private String sub2;
    private boolean checked;
    private int subColor;
    private int bgColor;
    private int startColor;
    private int centerColor;
    private int endColor;
    private int[] gradient;

    private Paint paint;
    private float height;
    private float width;
    private float radius;//圆半径
    private OnCheckChangeListener checkListener;
    @Nullable
    private OnClickListener clickListener;


    public RoundBtn(Context context) {
        this(context, null);
    }

    public RoundBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    private void getAttr(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundBtn);
        checked = array.getBoolean(R.styleable.RoundBtn_android_checked, false);
        bgColor = array.getColor(R.styleable.RoundBtn_bgColor, Color.WHITE);
        startColor = array.getColor(R.styleable.RoundBtn_startColor, bgColor);
        centerColor = array.getColor(R.styleable.RoundBtn_centerColor, bgColor);
        endColor = array.getColor(R.styleable.RoundBtn_endColor, bgColor);

        title = array.getString(R.styleable.RoundBtn_android_text);
        titleSize = array.getDimension(R.styleable.RoundBtn_android_textSize, sp2px(20));

        sub1 = array.getString(R.styleable.RoundBtn_sub1);
        sub2 = array.getString(R.styleable.RoundBtn_sub2);
        subColor = array.getColor(R.styleable.RoundBtn_subColor, 0xFF333333);
        subSize = array.getDimension(R.styleable.RoundBtn_subSize, sp2px(12));

        array.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        gradient = new int[]{startColor, centerColor, endColor};
    }

    /**
     * 测量
     */
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
            height = width * 1.5f;
        }
        setMeasuredDimension((int) width, (int) height);//存在精度损失
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = width / 3;
    }


    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, 0);
        drawCircle(canvas);
        if (!TextUtils.isEmpty(title)) {
            drawTitle(canvas);
        }
        if (!TextUtils.isEmpty(sub1)) {
            drawSub1(canvas);
        }
        if (!TextUtils.isEmpty(sub2)) {
            drawSub2(canvas);
        }
    }

    /**
     * 绘制背景圆
     */
    private void drawCircle(Canvas canvas) {
        paint.setShader(new LinearGradient(radius, radius * 2, -radius, 0, gradient, null, Shader.TileMode.CLAMP));
        canvas.drawCircle(0, radius, radius, paint);
        paint.setShader(null);
    }


    /**
     * 绘制主标题
     */
    private void drawTitle(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(titleSize);
        Rect rect = getTextBounds(title);
        float left = -rect.width() / 2;
        float top = checked ? rect.height() + dp2px(8) : rect.height() / 2 + radius;
        canvas.drawText(title, left, top, paint);
    }

    /**
     * 绘制副标题1
     */
    private void drawSub1(Canvas canvas) {
        paint.setColor(checked ? Color.WHITE : subColor);
        paint.setTextSize(subSize);
        Rect rect = getTextBounds(sub1);
        float left = -rect.width() / 2;
        float top = checked ? radius + rect.height() : radius * 2 + rect.height() + dp2px(4);
        canvas.drawText(sub1, left, top, paint);
    }

    /**
     * 绘制副标题2
     */
    private void drawSub2(Canvas canvas) {
        paint.setColor(checked ? Color.WHITE : subColor);
        paint.setTextSize(subSize);
        Rect rect = getTextBounds(sub2);
        float left = -rect.width() / 2;
        float top = checked ? radius * 2 - rect.height() : radius * 4 - rect.height() / 2;
        canvas.drawText(sub2, left, top, paint);
    }

    /**
     * Touch事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            checkReset();
            setChecked(true);
            if (clickListener != null) {
                clickListener.onClick(this);
            }
        }
        return true;
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
            if (child instanceof RoundBtn) {
                RoundBtn tab = (RoundBtn) child;
                if (tab.isChecked()) {
                    tab.setChecked(false);
                }
            }
        }
    }

    /**
     * 设置选中状态
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
        setWeight(this, checked ? 2 : 1);
        invalidate();

        if (checkListener != null) {
            checkListener.onCheckChange(this, checked);
        }
    }

    /**
     * 设置权重
     */
    private void setWeight(RoundBtn view, int weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (int) height);
        params.weight = weight;
        view.setLayoutParams(params);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener clickListener) {
        this.clickListener = clickListener;
        super.setOnClickListener(clickListener);

    }

    public void setOnCheckChangeListener(OnCheckChangeListener checkListener) {
        this.checkListener = checkListener;
    }

    public interface OnCheckChangeListener {
        void onCheckChange(RoundBtn btn, boolean isChecked);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public float getSubSize() {
        return subSize;
    }

    public void setSubSize(float subSize) {
        this.subSize = sp2px(subSize);
        invalidate();
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = sp2px(titleSize);
        invalidate();
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
        invalidate();
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
        invalidate();
    }

    public boolean isChecked() {
        return checked;
    }


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }


    /**
     * 测量文字
     */
    protected Rect getTextBounds(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
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

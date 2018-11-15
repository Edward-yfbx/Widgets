package com.yfbx.widgets.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2017/9/15
 * Description:
 */

public class TabView extends View implements Checkable {

    private float height;
    private float width;
    private Bitmap bitmap;
    private String text;
    private float textSize;
    private ColorStateList colorStateList;
    private float drawablePadding;
    private TextPaint textPaint;
    private boolean checked;
    private Bitmap checkImg;
    private Bitmap normalImg;
    private float maxImgHeight;
    private OnClickListener clickListener;

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    /**
     * 获得属性
     */
    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        checked = array.getBoolean(R.styleable.TabView_android_checked, false);
        text = array.getString(R.styleable.TabView_android_text);
        colorStateList = array.getColorStateList(R.styleable.TabView_android_textColor);
        textSize = array.getDimension(R.styleable.TabView_android_textSize, sp2px(14));
        drawablePadding = array.getDimension(R.styleable.TabView_android_drawablePadding, dp2px(8));
        checkImg = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.TabView_checkImg, 0));
        normalImg = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.TabView_unCheckImg, 0));
        bitmap = checked ? checkImg : normalImg;
        array.recycle();


    }

    private void init() {
        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Rect textSize = getTextBounds();

        switch (MeasureSpec.getMode(heightMeasureSpec)) {

            case MeasureSpec.EXACTLY://match_parent/具体数值
                width = MeasureSpec.getSize(widthMeasureSpec);
                height = MeasureSpec.getSize(heightMeasureSpec);
                reSizeText();
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST://wrap_content
                width = Math.max(textSize.width(), bitmap.getWidth());
                height = bitmap.getHeight() + textSize.height() + drawablePadding;
                break;
        }

        maxImgHeight = height - textSize.height() - drawablePadding;//计算图片最大高度
        setMeasuredDimension((int) width + 1, (int) height + 1);//存在精度损失
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 设置点击事件监听
     */
    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                checkReset();
                setChecked(true);
                if (clickListener != null) {
                    clickListener.onClick(this);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null || text == null) {
            return;
        }

        //图片缩放
        Matrix matrix = new Matrix();
        float scaleHeight = maxImgHeight / bitmap.getHeight();
        float imgWidth = bitmap.getWidth() * scaleHeight;
        matrix.setScale(scaleHeight, scaleHeight);

        //计算图片起始位置(左上角坐标,并将原点移动到该位置)
        float imgTop = 0;
        float imgLeft = (width - imgWidth) / 2;
        canvas.translate(imgLeft, imgTop);
        //画图
        canvas.drawBitmap(bitmap, matrix, null);

        //计算文字起始位置(注意此时坐标原点位置)
        Rect textSize = getTextBounds();
        float textTop = maxImgHeight + drawablePadding + textSize.height();
        float textLeft = (imgWidth - textSize.width()) / 2;
        canvas.translate(textLeft, textTop);

        //设置文字画笔颜色
        textPaint.setColor(getTextColor());
        //绘制文字
        canvas.drawText(text, 0, 0, textPaint);

    }

    public int getTextColor() {
        return colorStateList == null ? Color.BLACK : colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    /**
     * 测量文字边界,须在Paint文本参数设置完成后调用
     */
    private Rect getTextBounds() {
        Rect rect = new Rect();
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * 文字超过最大宽度，自动缩小
     */
    private void reSizeText() {
        while (width < getTextBounds().width()) {
            textSize--;
        }
    }

    /**
     * 重置LinearLayout 中 TabView  的选中状态
     */
    private void checkReset() {
        ViewParent parent = getParent();
        if (!(parent instanceof LinearLayout)) {
            return;
        }

        LinearLayout group = (LinearLayout) parent;
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            if (child instanceof TabView) {
                TabView tab = (TabView) child;
                tab.setChecked(false);
            }
        }
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
        bitmap = checked ? checkImg : normalImg;
        invalidate();
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
     * sp转换成px
     */
    protected float sp2px(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * dp 转换为 px
     */
    private float dp2px(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return value * scale + 0.5f;
    }

}

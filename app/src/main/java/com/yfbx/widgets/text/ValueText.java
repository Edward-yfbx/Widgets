package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class ValueText extends TextView {

    private Context context;
    private int height;
    private int width;

    private TextPaint paint;
    private float titleSize;
    private int titleColor;
    private String title;
    private Bitmap indicator;
    private boolean showIndicator = true;
    private boolean isAlignLeft;

    public ValueText(Context context) {
        this(context, null);
    }

    public ValueText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    /**
     * 获取属性
     */
    private void getAttr(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ValueText);
        title = array.getString(R.styleable.ValueText_title);
        titleColor = array.getColor(R.styleable.ValueText_titleColor, 0xFF565656);
        titleSize = array.getDimension(R.styleable.ValueText_titleSize, getTextSize());
        isAlignLeft = array.getBoolean(R.styleable.ValueText_alignLeft, false);
        indicator = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.ValueText_indicator, 0));
        array.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new TextPaint();
        paint.setColor(titleColor);
        paint.setTextSize(titleSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    /**
     * 尺寸
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    public TextPaint getTextPaint() {
        return paint;
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getIndicator() {
        return indicator;
    }

    public void setIndicator(Bitmap indicator) {
        this.indicator = indicator;
    }


    public boolean isShowIndicator() {
        return showIndicator;
    }

    public void setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (title == null) {
            return;
        }

        //title
        canvas.translate(0, height / 2);
        paint.setColor(getTitleColor());
        paint.setTextSize(getTitleSize());
        Rect titleSize = getTextBounds(title);
        float left = getPaddingLeft();
        float top = -titleSize.exactCenterY();
        canvas.drawText(title, left, top, paint);

        //indicator
        if (indicator != null && showIndicator) {
            float imgH = indicator.getHeight();
            float imgW = indicator.getWidth();
            float imgLeft = width - imgW - getPaddingRight();
            canvas.drawBitmap(indicator, imgLeft, -imgH / 2, null);
        }

        drawValue(canvas);
    }

    private void drawValue(Canvas canvas) {
        String value = getText() == null ? null : getText().toString();
        if (value == null) {
            return;
        }
        paint.setColor(getCurrentTextColor());
        paint.setTextSize(getTextSize());
        Rect titleSize = getTextBounds(title);
        Rect rect = getTextBounds(value);
        float top = -rect.exactCenterY();
        float left = width - getPaddingRight() - rect.width();
        if (isAlignLeft) {
            left = getPaddingLeft() + titleSize.width() + dp2px(8);
        } else {
            left = indicator == null ? left : left - indicator.getWidth();
        }
        canvas.drawText(value, left, top, paint);
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
     * sp转换成px
     */
    protected float sp2px(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * dp 转换为 px
     */
    private float dp2px(float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return value * scale + 0.5f;
    }
}

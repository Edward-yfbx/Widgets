package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
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
    private boolean showHint;

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
        indicator = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.ValueText_icon, 0));
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

        showHint = TextUtils.isEmpty(getText());
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


    public float getTitleSize() {
        return titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public String getTitle() {
        return title;
    }

    public boolean isShowIndicator() {
        return showIndicator;
    }

    public Bitmap getIndicator() {
        return indicator;
    }

    /**
     * 设置Title
     */
    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    /**
     * 设置Title尺寸
     */
    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
        invalidate();
    }

    /**
     * 设置Title颜色
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        invalidate();
    }

    /**
     * 设置图标
     */
    public void setIndicator(Bitmap indicator) {
        this.indicator = indicator;
        invalidate();
    }

    /**
     * 是否显示图标
     */
    public void setShowIndicator(boolean enable) {
        this.showIndicator = enable;
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //title
        if (title != null) {
            canvas.translate(0, height / 2);
            paint.setColor(getTitleColor());
            paint.setTextSize(getTitleSize());
            Rect titleSize = getTextBounds(title);
            float left = getPaddingLeft();
            float top = -titleSize.exactCenterY();
            canvas.drawText(title, left, top, paint);
        }

        //indicator
        if (indicator != null && showIndicator) {
            float imgH = indicator.getHeight();
            float imgW = indicator.getWidth();
            float imgLeft = width - imgW - getPaddingRight();
            canvas.drawBitmap(indicator, imgLeft, -imgH / 2, null);
        }

        //text
        if (!TextUtils.isEmpty(getText())) {
            drawValue(canvas, getText().toString(), getCurrentTextColor());
        }

        //hint
        if (showHint && !TextUtils.isEmpty(getHint())) {
            drawValue(canvas, getHint().toString(), getCurrentHintTextColor());
        }

    }

    private void drawValue(Canvas canvas, String value, int color) {
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        Rect rect = getTextBounds(value);
        float top = -rect.exactCenterY();
        float left = width - getPaddingRight() - rect.width();
        if (isAlignLeft) {
            int titleWidth = title == null ? 0 : getTextBounds(title).width();
            left = getPaddingLeft() + titleWidth + dp2px(8);
        } else {
            left = indicator == null ? left : left - indicator.getWidth();
        }
        canvas.drawText(value, left, top, paint);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            showHint = false;
        }

        if (!focused && TextUtils.isEmpty(getText())) {
            showHint = true;
        }
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
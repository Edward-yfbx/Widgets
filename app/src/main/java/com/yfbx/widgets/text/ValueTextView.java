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
import android.util.AttributeSet;
import android.widget.TextView;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class ValueTextView extends TextView {

    private Context context;
    private TextPaint paint;
    private int height;
    private int width;
    private float valueSize;
    private int valueColor;
    private String value;
    private Bitmap indicator;

    public ValueTextView(Context context) {
        this(context, null);
    }

    public ValueTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    /**
     * 获取属性
     */
    private void getAttr(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ValueTextView);
        value = array.getString(R.styleable.ValueTextView_value);
        valueColor = array.getColor(R.styleable.ValueTextView_valueColor, 0xFF555555);
        valueSize = array.getDimension(R.styleable.ValueTextView_valueSize, sp2px(14));
        indicator = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.ValueTextView_indicator, 0));
        array.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new TextPaint();
        paint.setColor(valueColor);
        paint.setTextSize(valueSize);
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

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (indicator != null) {
            int imgH = indicator.getHeight();
            int imgW = indicator.getWidth();
            int left = width - imgW * 2;
            int top = (height - imgH) / 2;
            canvas.drawBitmap(indicator, left, top, null);

            if (value != null) {
                Rect valueSize = getTextBounds(value);
                int valueLeft = left - valueSize.right;
                int valueTop = (height - valueSize.top) / 2;
                canvas.drawText(value, valueLeft, valueTop, paint);
            }
            return;
        }

        if (value != null) {
            Rect valueSize = getTextBounds(value);
            int left = width - valueSize.right - getPaddingRight();
            int top = (height - valueSize.top) / 2;
            canvas.drawText(value, left, top, paint);

        }

    }

    /**
     * 测量文字
     */
    private Rect getTextBounds(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * sp转换成px
     */
    private float sp2px(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }
}

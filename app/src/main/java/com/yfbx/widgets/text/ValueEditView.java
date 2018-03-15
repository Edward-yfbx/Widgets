package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class ValueEditView extends EditText implements TextWatcher {

    private Context context;
    private TextPaint paint;
    private int height;
    private int width;
    private float nameSize;
    private int nameColor;
    private String name;
    private Bitmap indicator;
    private boolean showIndicator;
    private boolean showState;
    private TextChangedListener listener;


    public ValueEditView(Context context) {
        this(context, null);
    }

    public ValueEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
        init();
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    /**
     * 获取属性
     */
    private void getAttr(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ValueEditView);
        name = array.getString(R.styleable.ValueEditView_name);
        nameColor = array.getColor(R.styleable.ValueEditView_nameColor, 0xFF555555);
        nameSize = array.getDimension(R.styleable.ValueEditView_nameSize, sp2px(14));
        indicator = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.ValueEditView_icon, 0));
        array.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new TextPaint();
        paint.setColor(nameColor);
        paint.setTextSize(nameSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        addTextChangedListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setSingleLine();

        // TODO: 2018/3/15 bug：输入过长会覆盖右侧图标，超过控件长度会导致移位
        // TODO: 2018/3/15 bug：设置Gravity时出错
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
        if (name != null) {
            Rect textSize = getTextBounds(name);
            int left = (int) dp2px(16);
            int top = (height - textSize.top) / 2;
            canvas.drawText(name, left, top, paint);
            canvas.translate(textSize.right + getPaddingLeft(), 0);
            super.onDraw(canvas);
            canvas.translate(-textSize.right - getPaddingLeft(), 0);
        } else {
            super.onDraw(canvas);
        }

        if (indicator != null && showIndicator) {
            int imgH = indicator.getHeight();
            int imgW = indicator.getWidth();
            int left = width - imgW * 2;
            int top = (height - imgH) / 2;
            canvas.drawBitmap(indicator, left, top, null);
        }
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (indicator != null) {
            int imgW = indicator.getWidth();
            int left = width - imgW * 2;
            float rawX = event.getRawX();
            if (rawX >= left) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
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

    /**
     * dp 转换为 px
     */
    private float dp2px(float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return value * scale + 0.5f;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setInputType(getInputType());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (indicator != null) {
            showIndicator = s != null && s.length() > 0;
            if (!showState && showIndicator) {
                invalidate();
            }
            showState = showIndicator;
        }

        if (listener != null) {
            listener.afterTextChanged(s);
        }
    }

    /**
     * 设置输入监听
     */
    public void setTextChangedListener(TextChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 输入监听
     */
    public interface TextChangedListener {

        void afterTextChanged(Editable s);
    }

}

package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class EditView extends ValueText implements TextWatcher {

    private Context context;
    private int height;
    private int width;
    private boolean showIndicator;
    private boolean showState;
    private TextChangedListener listener;


    public EditView(Context context) {
        this(context, null);
    }

    public EditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    /**
     * 初始化
     */
    private void init() {
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
        super.onDraw(canvas);
        // TODO: 2018/3/28  
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getIndicator() != null) {
            int imgW = getIndicator().getWidth();
            int left = width - imgW * 2;
            float rawX = event.getRawX();
            if (rawX >= left) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            showIndicator = false;
            invalidate();
        }
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
        if (getIndicator() != null) {
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

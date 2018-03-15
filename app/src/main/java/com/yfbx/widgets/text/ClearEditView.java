package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yfbx.widgets.R;

/**
 * Author:Edward
 * Date:2018/3/14
 * Description:带清空按钮的EditText
 */

@SuppressLint("AppCompatCustomView")
public class ClearEditView extends EditText implements TextWatcher {

    private Bitmap bitmap;
    private int height;
    private int width;
    private boolean showDeleteBtn;
    private boolean showState;
    private TextChangedListener listener;

    public ClearEditView(Context context) {
        this(context, null);
    }

    public ClearEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_close);
        addTextChangedListener(this);
    }

    /**
     * 尺寸大小
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
        if (showDeleteBtn) {
            int imgH = bitmap.getHeight();
            int imgW = bitmap.getWidth();
            int left = width - imgW * 2;
            int top = (height - imgH) / 2;
            canvas.drawBitmap(bitmap, left, top, null);
        }
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int imgW = bitmap.getWidth();
        int left = width - imgW * 2;
        float rawX = event.getRawX();
        if (rawX >= left) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    /**
     * 输入监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        showDeleteBtn = s != null && s.length() > 0;
        if (!showState && showDeleteBtn) {
            invalidate();
        }
        showState = showDeleteBtn;

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

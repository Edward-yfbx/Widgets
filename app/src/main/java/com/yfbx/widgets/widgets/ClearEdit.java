package com.yfbx.widgets.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yfbx.widgets.R;


/**
 * Author:Edward
 * Date:2018/4/3
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class ClearEdit extends EditText implements TextWatcher {

    private boolean show;
    private Bitmap bitmap;
    private OnTextChangeListener textChangeListener;

    public ClearEdit(Context context) {
        this(context, null);
    }

    public ClearEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_close);
        addTextChangedListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        show = getText() != null && getText().toString().length() > 0 && focused;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (show) {
            int left = getWidth() - bitmap.getWidth() - getPaddingRight();
            int top = (getHeight() - bitmap.getHeight()) / 2;
            canvas.drawBitmap(bitmap, left, top, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bitmap != null && show) {
            int left = getWidth() - bitmap.getWidth() - getPaddingRight();
            if (event.getRawX() >= left) {
                setText("");
                show = false;
                invalidate();
            }
        }
        return super.onTouchEvent(event);
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
        show = s != null && s.length() > 0 && isFocused();
        invalidate();
        if (textChangeListener != null) {
            textChangeListener.onTextChanged(s);
        }
    }

    public void setOnTextChangeListener(OnTextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public interface OnTextChangeListener {

        void onTextChanged(Editable editable);
    }
}

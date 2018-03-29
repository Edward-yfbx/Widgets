package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Author:Edward
 * Date:2018/3/15
 * Description:
 */

@SuppressLint("AppCompatCustomView")
public class EditView extends ValueText implements TextWatcher {

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


    /**
     * 初始化
     */
    private void init() {
        addTextChangedListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setShowIndicator(false);

        // TODO: 2018/3/29 没有光标
        // TODO: 2018/3/29 输入过长越界
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getIndicator() != null) {
            int imgW = getIndicator().getWidth();
            int left = getWidth() - imgW * 2;
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
            setShowIndicator(false);
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
            setShowIndicator(s != null && s.length() > 0);
            if (!showState && isShowIndicator()) {
                invalidate();
            }
            showState = isShowIndicator();
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

    @Override
    public boolean getFreezesText() {
        return true;
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override
    public Editable getText() {
        return (Editable) super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, BufferType.EDITABLE);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int, int)}.
     */
    public void setSelection(int start, int stop) {
        Selection.setSelection(getText(), start, stop);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int)}.
     */
    public void setSelection(int index) {
        Selection.setSelection(getText(), index);
    }

    /**
     * Convenience for {@link Selection#selectAll}.
     */
    public void selectAll() {
        Selection.selectAll(getText());
    }

    /**
     * Convenience for {@link Selection#extendSelection}.
     */
    public void extendSelection(int index) {
        Selection.extendSelection(getText(), index);
    }

    /**
     * Causes words in the text that are longer than the view's width to be ellipsized instead of
     * broken in the middle. {@link TextUtils.TruncateAt#MARQUEE
     * TextUtils.TruncateAt#MARQUEE} is not supported.
     *
     * @param ellipsis Type of ellipsis to be applied.
     * @throws IllegalArgumentException When the value of <code>ellipsis</code> parameter is
     *                                  {@link TextUtils.TruncateAt#MARQUEE}.
     * @see TextView#setEllipsize(TextUtils.TruncateAt)
     */
    @Override
    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        if (ellipsis == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode "
                    + "TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(ellipsis);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return EditText.class.getName();
    }

    /**
     * 输入监听
     */
    public interface TextChangedListener {

        void afterTextChanged(Editable s);
    }
}

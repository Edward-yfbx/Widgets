package com.yfbx.widgets.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
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
public class EditView extends EditText {

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

    private OnIconClickListener listener;

    public EditView(Context context) {
        this(context, null);
    }

    public EditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        setFocusable(true);
        setFocusableInTouchMode(true);
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

        drawValue(canvas);
    }

    private void drawValue(Canvas canvas) {
        String value = getText() == null ? null : getText().toString();
        if (value == null) {
            return;
        }
        paint.setColor(getCurrentTextColor());
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

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (indicator != null && listener != null) {
            int imgW = getIndicator().getWidth();
            int left = getWidth() - imgW * 2;
            float rawX = event.getRawX();
            if (rawX >= left) {
                listener.onClick();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 图标点击事件
     */
    public void setOnIconClickListener(OnIconClickListener listener) {
        this.listener = listener;
    }

    public interface OnIconClickListener {
        void onClick();
    }

}

package com.yfbx.widgets.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.yfbx.widgets.R;
import com.yfbx.widgets.util.BitmapUtil;
import com.yfbx.widgets.util.PxUtil;

import butterknife.BindView;

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */


public class DrawFragment extends BaseFragment {

    @BindView(R.id.img)
    ImageView img;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        draw();
    }

    @Override
    public int getLayout() {
        return R.layout.frag_draw;
    }


    private void draw() {
        //文件容器
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0x00FFFFFF);//背景透明，默认黑色

        //画布
        Canvas canvas = new Canvas(bitmap);
        //画笔
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        //绘制
        canvas.translate(128, 128);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, 100, paint);

        paint.setTextSize(PxUtil.sp(36));
        paint.setColor(Color.BLUE);
        Rect rect = new Rect();
        String text = "E";
        paint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, -rect.width() / 2, rect.height() / 2, paint);

        //展示
        img.setImageBitmap(bitmap);

        //保存
        BitmapUtil.saveBitmap(bitmap, "test");

    }


}

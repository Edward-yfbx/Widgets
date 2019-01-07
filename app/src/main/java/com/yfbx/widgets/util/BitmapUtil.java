package com.yfbx.widgets.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */


public class BitmapUtil {

    /**
     * 将 Bitmap 保存到SD卡
     */
    public static void saveBitmap(Bitmap bitmap, String fileName) {
        String sd = Environment.getExternalStorageDirectory().getPath();
        File file = new File(sd, fileName + ".png");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知媒体扫描器,扫描媒体文件
     */
    public static void notifyMediaScanner(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}

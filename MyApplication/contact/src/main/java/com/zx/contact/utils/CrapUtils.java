package com.zx.contact.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * 将图片裁剪成圆形
 * Created Time: 2017/2/4 10:04.
 *
 * @author HY
 */
public class CrapUtils {
    public static Bitmap toRoundBitmap(Bitmap photo) {
        int width = photo.getWidth();
        int height = photo.getHeight();
        int r;
        if (width > height) r = height;
        else r = width;

        Bitmap cicleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cicleBitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(cicleBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, r, r);
        canvas.drawRoundRect(rectF, r / 2, r / 2, paint);

        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(photo, null, rectF, paint);
        return cicleBitmap;
    }

}

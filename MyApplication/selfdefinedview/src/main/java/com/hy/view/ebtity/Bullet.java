package com.hy.view.ebtity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.hy.view.view.TouchMoveView;

/**
 * Created Time: 2017/2/6 13:47.
 *
 * @author HY
 */

public class Bullet {
    private Bitmap mBitmap;
    private int frame;
    private boolean destory;
    private float speed=2;
    private float x,y;

    public Bullet(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public final void draw(Canvas canvas, Paint paint, TouchMoveView view) {
        beforeDraw(canvas, paint, view);
        onDraw(canvas, paint, view);
        afterDraw(canvas, paint, view);
    }

    private void beforeDraw(Canvas canvas, Paint paint, TouchMoveView view) {
        if(!isDestory()){
            //在y轴方向移动speed像素
            move(0, speed * view.getDensity());
        }
    }

    public boolean isDestory() {
        return destory;
    }

    public void setDestory(boolean destory) {
        this.destory = destory;
    }

    private void move(float i, float v) {

    }


    private void afterDraw(Canvas canvas, Paint paint, TouchMoveView view) {
    }

    public void onDraw(Canvas canvas, Paint paint, TouchMoveView view) {
        frame++;

    }

    public float getWidth() {
        if (null != mBitmap) {
            return mBitmap.getWidth();
        }
        return 0;
    }

    public float getHeight() {
        if (null != mBitmap) {
            return mBitmap.getHeight();
        }
        return 0;
    }
}

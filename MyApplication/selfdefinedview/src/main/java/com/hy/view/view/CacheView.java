package com.hy.view.view;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CacheView extends View {
    Paint mPaint;
    RectF mRectF;
    CacheThread ct;
    static Vector<Integer> colors = new Vector<>();

    static {
        for (int i = 0; i <= 250; i += 25) {
            int color = Color.rgb(i, i, i);
            colors.add(color);
        }

        colors.add(Color.WHITE);
    }

    public CacheView(Context context) {
        this(context, null);
    }

    public CacheView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CacheView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mRectF = new RectF(500, 800, 600, 900);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(20);
        ct = new CacheThread();
        ct.start();
    }

    public void stop(boolean isCanseled) {
        ct.setCanceled(isCanseled);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 12; i++) {
            mPaint.setColor(colors.get(i));
            canvas.drawArc(mRectF, 30 * i + 5, 20, false, mPaint);
        }

    }

    class CacheThread extends Thread {
        private boolean isCanceled;

        void setCanceled(boolean canceled) {
            isCanceled = canceled;
        }

        @Override
        public void run() {
            while (true) {
                colors.add(colors.remove(0));
                if (isCanceled) break;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }

}

package com.hy.view.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.hy.view.R;
import com.hy.view.utils.Utils;


/**
 * meteor view
 *
 * @author HY
 */
@SuppressWarnings("ALL")
public class MeteorView extends View {

    /*message of view stop*/
    private static final int VIEW_STOP = 0;

    /*screen size*/
    protected int width, height;
    /*X and Y coordinate*/
    protected int coordinateX, coordinateY;
    /*Y coordinate max value*/
    protected int maxY;
    protected Paint mPaint;
    protected Bitmap bitmap;

    protected Resources res;
    /*handle the massage when this view will stop*/
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            listener.viewStop(MeteorView.this);

        }
    };

    public MeteorView(Context context) {
        this(context, null);
    }

    public MeteorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeteorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        res = this.getResources();
        height = Utils.getHeight(res);
        width = Utils.getWidth(res);
        maxY = height - Utils.getDpSize(100, res);
        /*generate random coordinate in screen*/
        coordinateX = Utils.getRanNum(width * 3 / 4) + (width / 4);
        coordinateY = Utils.getRanNum(height / 2);
        mPaint = new Paint();
        bitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_meteor);
        new MeteorThread().start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, coordinateX, coordinateY, mPaint);
    }

    class MeteorThread extends Thread {
        @Override
        public void run() {
            boolean flag = true;
            int rate = Utils.getRanNum(7) + 3;
            while (flag) {
                coordinateX -= rate;
                coordinateY += rate;
                if (coordinateX <= 0 || coordinateY >= maxY) {
//                    bitmap = BitmapFactory.decodeResource(res,
//                            R.mipmap.ic_transparent);
                    flag = false;
                    mHandler.sendEmptyMessage(VIEW_STOP);
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }

    protected OnViewStopListener listener;

    public void setOnViewStopListener(OnViewStopListener listener) {
        this.listener = listener;
    }

    /**
     * view stop listener
     *
     * @author HY
     */
    public interface OnViewStopListener {
        void viewStop(View view);
    }

}

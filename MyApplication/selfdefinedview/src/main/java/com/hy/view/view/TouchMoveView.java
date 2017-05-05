package com.hy.view.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hy.view.R;

/**
 * Created Time: 2017/2/5 16:15.
 *
 * @author HY
 */

public class TouchMoveView extends View {

    //    private static final String TAG = TouchMoveView.class.getSimpleName();
    private Bitmap mIcon;
    private Paint mPaint;
    private float x, y;
    private int mIconCenterX;
    private int mIconCenterY;


    private float density = getResources().getDisplayMetrics().density;
    private int[] mBitmaps = {R.drawable.bg, R.drawable.plane, R.drawable.big, R.drawable.blue_bullet,
            R.drawable.bomb, R.drawable.bomb_award, R.drawable.bullet_award
            , R.drawable.explosion, R.drawable.middle, R.drawable.small, R.drawable.yellow_bullet};

    public TouchMoveView(Context context) {
        super(context);
        init();
    }

    public TouchMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setBackgroundResource(mBitmaps[0]);
        Resources res = getResources();
        mIcon = BitmapFactory.decodeResource(res, mBitmaps[1]);
        mIconCenterX = mIcon.getWidth() / 2;
        mIconCenterY = mIcon.getHeight() / 2;
        x = res.getDisplayMetrics().widthPixels / 2 - mIconCenterX;
        y = res.getDisplayMetrics().heightPixels - 200 - mIconCenterY;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mIcon, x, y, mPaint);
    }

    public float getDensity() {
        return density;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP://抬起
                return super.onTouchEvent(event);
            default:
                x = event.getX() - mIconCenterX;
                y = event.getY() - mIconCenterY;
                postInvalidate();
                return true;
        }
    }
}

package com.hy.view.view;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class RingBallView extends SurfaceView implements Callback {

	SurfaceHolder mHolder;
	Paint mPaint;
	Canvas canvas;
	Integer c;
	float x;
	float y;
	static Vector<Integer> color = new Vector<Integer>();
	static {
		color.add(Color.BLUE);
		color.add(Color.RED);
		color.add(Color.YELLOW);
		color.add(Color.MAGENTA);
	}

	public RingBallView(Context context) {
		super(context);
		mHolder = this.getHolder();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mHolder.addCallback(this);
		x = 50;
		y = 50;
	}

	class DrawThread extends Thread {
		@Override
		public void run() {
			while (true) {				
				canvas = mHolder.lockCanvas();
				if (null == canvas) {
					return;
				}
				canvas.drawColor(Color.WHITE);
				c = color.remove(0);
				color.add(c);
				mPaint.setColor(color.get(0));

				canvas.drawCircle(x, y, 50, mPaint);

				if (y < 1870 && x == 50) {
					y += 20;
				} else if (y == 1870 && x < 1030) {
					x += 20;
				} else if (x == 1030 && y > 50) {
					y -= 20;
				} else if (y == 50 && x >= 50) {
					x -= 20;
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		DrawThread dt = new DrawThread();
		dt.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}

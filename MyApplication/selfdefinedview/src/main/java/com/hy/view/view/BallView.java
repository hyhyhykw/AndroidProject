package com.hy.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.view.View;

public class BallView extends View {

	Paint paint;
	float k = (float) (Math.random() * 3.4 - 1.7);
	float radius = 20;
	float x = Math.round((Math.random() * 880 + 100));
	float y = 1920;
	float b = y - radius - k * x;

	public BallView(Context context) {
		this(context, null);
	}

	public BallView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		while (k == 0) {
			k = (float) (Math.random() * 3.4 - 1.7);
		}
		RollThread rt = new RollThread();
		rt.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(x, y = k * x + b, radius, paint);
	}

	class RollThread extends Thread {
		@Override
		public void run() {
			long time = 3;
			while (true) {

				if (x >= 1080 - radius) {
					k = -k;
					b = y - k * x;
					// paint.setColor(Color.YELLOW);
					while (x >= radius && y >= radius && y <= 1920 - radius) {
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						postInvalidate();
						x--;
					}

				} else if (x <= radius) {
					k = -k;
					b = y - k * x;
					// paint.setColor(Color.RED);
					while (x <= 1080 - radius && y >= radius
							&& y <= 1920 - radius) {
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						postInvalidate();
						x++;
					}
				} else if (y <= radius) {
					k = -k;
					b = y - k * x;
					// paint.setColor(Color.GREEN);
					while (y <= 1920 - radius && x >= radius
							&& x <= 1080 - radius) {
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						postInvalidate();
						y++;
						x = (y - b) / k;
					}
				} else if (y >= 1920 - radius) {
					k = -k;
					b = y - k * x;
					// paint.setColor(Color.BLUE);
					while (y >= radius && x >= radius && x <= 1080 - radius) {
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						postInvalidate();
						y--;
						x = (y - b) / k;
					}
				}
			}

		}
	}
}

package com.hy.view.view;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class CircleRingView extends View {
	static Vector<Integer> color = new Vector<Integer>();
	static Vector<Integer> color2 = new Vector<Integer>();
	Paint paint;
	Paint paint2;
	Paint paint3;
	
	static RectF rectF;
	static RectF rectF2;
	static RectF rectF3;
	
	static {
		color.add(Color.BLUE);
		color.add(Color.CYAN);
		color.add(Color.DKGRAY);
		color.add(Color.GRAY);
		color.add(Color.GREEN);
		color.add(Color.LTGRAY);
		color.add(Color.MAGENTA);
		color.add(Color.RED);
		color.add(Color.YELLOW);
		color2.addAll(color);
	}

	public CircleRingView(Context context) {
		super(context);
		paint = new Paint();
		paint2 = new Paint();
		paint3 = new Paint();
		
		paint.setAntiAlias(true);
		paint.setStrokeWidth(20);
		paint.setStyle(Style.STROKE);
		
		paint2.setAntiAlias(true);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(20);
		
		paint3.setAntiAlias(true);
		paint3.setStrokeWidth(20);
		
		rectF = new RectF(150, 450, 950, 1250);
		rectF2 = new RectF(100, 400, 1000, 1300);
		rectF3 = new RectF(200, 500, 900, 1200);
		
		RingThread rt = new RingThread();
		rt.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < 9; i++) {
			paint.setColor(color.get(i));
			paint2.setColor(color2.get(i));
			paint3.setColor(color2.get(i));
			canvas.drawArc(rectF, 40 * i, 40, false, paint);
			canvas.drawArc(rectF2, 40 * i, 40, false, paint2);
			canvas.drawArc(rectF3, 40 * i, 40, true, paint3);
		}

	}

	class RingThread extends Thread {
		@Override
		public void run() {
			while (true) {
				int c = color.remove(color.size() - 1);
				color.add(0, c);
				int c2 = color2.remove(0);
				color2.add(c2);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postInvalidate();
			}
		}
	}
}

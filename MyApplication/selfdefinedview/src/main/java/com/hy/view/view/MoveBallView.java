package com.hy.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MoveBallView extends View {
	Paint paint;
	int[] colors = { Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN };
	float y = 50;
	int index = 0;

	public MoveBallView(Context context) {
		super(context);
		DrawThread dt=new DrawThread();
		dt.start();
		paint = new Paint();
		
	}

	

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(colors[index]);
		canvas.drawCircle(500, y, 70, paint);
		
	}

	class DrawThread extends Thread {
		@Override
		public void run() {
			while (true) {				
				if (index == 3) {
					index = 0;
				} else {
					index++;
				}
				if (y < 1960) {
					y += 50;
				} else {
					y = 50;
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postInvalidate();
			}
			
		}
	}
}

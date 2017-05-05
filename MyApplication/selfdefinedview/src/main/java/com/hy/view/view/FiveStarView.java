package com.hy.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class FiveStarView extends View {

	public FiveStarView(Context context) {
		this(context, null);
	}

	public FiveStarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FiveStarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		Path path = new Path();
		path.moveTo(50, 0);
		path.lineTo(38.77f, 34.55f);
		path.lineTo(2.44f, 34.55f);
		path.lineTo(31.83f, 55.90f);
		path.lineTo(20.60f, 90.45f);
		path.lineTo(50f, 69.10f);
		path.lineTo(79.38f, 90.45f);
		path.lineTo(68.16f, 55.90f);
		path.lineTo(97.55f, 34.55f);
		path.lineTo(61.22f, 34.55f);
		path.close();
		canvas.drawPath(path, paint);

	}
}

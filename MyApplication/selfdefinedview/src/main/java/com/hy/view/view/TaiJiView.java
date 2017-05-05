package com.hy.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TaiJiView extends View {

	RectF rectF1;
	RectF rectF2;
	RectF rectF3;
	Paint mPaint;

	public TaiJiView(Context context) {
		this(context, null);

	}

	public TaiJiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TaiJiView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);

	}

	int width;
	int height;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		//
		// rectF1 = new RectF(150, 450, 950, 1250);
		// rectF2 = new RectF(350, 450, 750, 850);
		// rectF3 = new RectF(350, 850, 750, 1250);

		rectF1 = new RectF(0, 0, width, height);
		rectF2 = new RectF(width / 4, 0, width * 3 / 4, height / 2);
		rectF3 = new RectF(width / 4, height / 2, width * 3 / 4, height);
		setMeasuredDimension(width, height);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.WHITE);
		canvas.drawArc(rectF1, 90, 180, true, mPaint);
		mPaint.setColor(Color.BLACK);
		canvas.drawArc(rectF1, 270, 180, true, mPaint);
		mPaint.setColor(Color.WHITE);
		canvas.drawArc(rectF2, 270, 180, true, mPaint);
		mPaint.setColor(Color.BLACK);
		canvas.drawArc(rectF3, 90, 180, true, mPaint);

		canvas.drawCircle(width / 2, height / 4, 50, mPaint);
		mPaint.setColor(Color.WHITE);
		canvas.drawCircle(width / 2, height * 3 / 4, 50, mPaint);
	}
}

package com.zx.calculator;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * <p>
 * 带有光标的TextView，比较像EditText
 * 提供一个setText方法，设置显示的文本
 * </p>
 * Created time : 2017/3/15 14:29.
 *
 * @author HY
 */
public class ShineTextView extends LinearLayout {

    private static final int YES = 0;
    private static final int NO = 1;

    private boolean flag = true;
    private TextView mTxtInput;
    private TextView mTxtCursor;

    private ShineHandler mHandler;

    public ShineTextView(Context context) {
        this(context, null);
    }

    public ShineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.shine_text_view, this);

        mTxtInput = (TextView) view.findViewById(R.id.txt_input);
        mTxtCursor = (TextView) view.findViewById(R.id.txt_cursor);

        mHandler = new ShineHandler(this);
        mTxtCursor.setText(R.string.text_gun);
        setTag(YES);
        new ShineThread().start();
    }


    /**
     * 需要设置的文本
     *
     * @param text 文本
     */
    public void setText(CharSequence text) {
        mTxtInput.setTextColor(Color.BLACK);
        mTxtInput.setText(text);
    }

    /**
     * 获取显示的文本
     *
     * @return 显示的文本
     */
    public String getText() {
        return (String) mTxtInput.getText();
    }


    /**
     * 设置显示文本的颜色
     */
    public void showError() {
        mTxtInput.setTextColor(Color.RED);
        mTxtInput.setText("Error");
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        flag = false;
    }

    private class ShineThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                int tag = (Integer) getTag();
                if (tag == YES) {
                    mHandler.sendEmptyMessage(YES);
                } else {
                    mHandler.sendEmptyMessage(NO);
                }
                SystemClock.sleep(800);
            }
        }
    }

    private static class ShineHandler extends Handler {
        private WeakReference<ShineTextView> mWeakReference;

        ShineHandler(ShineTextView shineTextView) {
            mWeakReference = new WeakReference<>(shineTextView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ShineTextView shineTextView = mWeakReference.get();

            if (null == shineTextView) return;

            int what = msg.what;

            if (what == YES) {
                shineTextView.setTag(NO);
                shineTextView.mTxtCursor.setText(R.string.text_space);
            } else {
                shineTextView.setTag(YES);
                shineTextView.mTxtCursor.setText(R.string.text_gun);
            }

        }
    }

}

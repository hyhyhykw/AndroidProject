package com.zx.anew;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created time : 2017/3/8 9:03.
 *
 * @author HY
 */
public class TimerButton extends AppCompatButton implements View.OnClickListener {

    private int time;
    private int tempTime;

    private Timer mTimer;

    private boolean isOk;

    private TimerHandler mHandler;

    private static final int MESSAGE_CHANGE = 0;

    private static final int MESSAGE_FINISH = 1;

    public TimerButton(Context context) {
        this(context, null);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimerButton);
        tempTime = time = ta.getInteger(R.styleable.TimerButton_maxTime, 60);
        ta.recycle();
        setText("点击");
        setOnClickListener(this);
        mHandler = new TimerHandler(this);
    }

    @Override
    public void onClick(View v) {
        setText(String.valueOf(time));
        isOk = true;
        mTimer = new Timer();
        mTimer.schedule(new Task(), 0, 1000);
        setEnabled(false);
    }

    private class Task extends TimerTask {
        @Override
        public void run() {
            while (isOk) {
                SystemClock.sleep(1000);
                mHandler.sendEmptyMessage(MESSAGE_CHANGE);
                time--;
                if (time < 0) {
                    mTimer.cancel();
                    mTimer = null;
                    isOk = false;
                    mHandler.sendEmptyMessage(MESSAGE_FINISH);
                }
            }
        }
    }

    private static class TimerHandler extends Handler {
        private WeakReference<TimerButton> mWeakReference;

        TimerHandler(TimerButton timerButton) {
            mWeakReference = new WeakReference<>(timerButton);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimerButton timerButton = mWeakReference.get();
            if (null == timerButton) return;
            switch (msg.what) {
                case MESSAGE_CHANGE:
                    timerButton.setText(String.valueOf(timerButton.time));
                    break;
                case MESSAGE_FINISH:
                    timerButton.setText("点击");
                    timerButton.setEnabled(true);
                    timerButton.time = timerButton.tempTime;
                    break;
                default:
                    throw new UnsupportedOperationException("unknow message");
            }
        }
    }
}

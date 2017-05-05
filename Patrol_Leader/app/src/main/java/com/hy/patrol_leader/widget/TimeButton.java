package com.hy.patrol_leader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.hy.patrol_leader.main.MyApplication;
import com.hy.patrol_leader.dao.LogWrapper;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created time : 2017/4/26 14:04.
 * <p>
 * 倒计时按钮的封装
 *
 * @author HY
 */
@SuppressWarnings("unused")
public class TimeButton extends AppCompatButton implements View.OnClickListener {

    private static final String TAG = TimeButton.class.getSimpleName();

    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "s";
    private String textbefore = "获取验证码";
    private static final String TIME = "time";
    private static final String CTIME = "ctime";
    private Timer mTimer;
    private TimerTask mTask;
    private long time;

    public TimeButton(Context context) {
        this(context, null);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        setTextColor(Color.parseColor("#66000000"));
        setText(textbefore);
        setGravity(Gravity.CENTER);
    }

    Handler mHandler = new TimeHandler(this);

    private void initTimer() {
        time = lenght;
        mTimer = new Timer();
        mTask = new TimerTask() {

            @Override
            public void run() {
                LogWrapper.e(TAG, time / 1000 + "");
                mHandler.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (mTimer != null)
            mTimer.cancel();
        mTimer = null;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        mTimer.schedule(mTask, 0, 1000);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (MyApplication.map == null)
            MyApplication.map = new HashMap<>();
        MyApplication.map.put(TIME, time);
        MyApplication.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        LogWrapper.e(TAG, "onDestroy");
    }

    /**
     * 和activity的onCreate()方法同步
     */
    @SuppressLint("SetTextI18n")
    public void onCreate() {
        LogWrapper.e(TAG, MyApplication.map + "");
        if (MyApplication.map == null)
            return;
        if (MyApplication.map.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - MyApplication.map.get(CTIME)
                - MyApplication.map.get(TIME);
        MyApplication.map.clear();
        if (time <= 0) {
            initTimer();
            this.time = Math.abs(time);
            mTimer.schedule(mTask, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    private static class TimeHandler extends Handler {
        private WeakReference<TimeButton> mWeakReference;

        TimeHandler(TimeButton button) {
            mWeakReference = new WeakReference<>(button);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            TimeButton button = mWeakReference.get();
            if (null == button) return;


            button.setText(button.time / 1000 + button.textafter);
            button.time -= 1000;
            if (button.time <= 0) {
                button.setEnabled(true);
                button.setText(button.textbefore);
                button.clearTimer();
            }
        }
    }

    /**
     * 设置时间
     *
     * @param lenght 时间
     * @return button
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

    /**
     * 设置计时时候显示的文本
     *
     * @param text1 文本
     */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     *
     * @param text0 文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }
}

package com.zx.contact.view;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 首页右边字母控件
 */
@SuppressWarnings("deprecation")
public class SideBar extends View {

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint;

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context) {
        this(context, null);
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = this.getHeight();// 获取对应高度
        int width = this.getWidth(); // 获取对应宽度

        //300/27=
        int singleHeight = height / mLetters.length;// 获取每一个字母的高度

        //遍历所有的字母
        for (int i = 0; i < mLetters.length; i++) {
            //设置画笔的颜色
            paint.setColor(Color.rgb(33, 65, 98));
            //设置字体的样式
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            //
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            // 选中的状态
            if (i == choose) {
                //
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(mLetters[i]) / 2;
            //i=0
            float yPos = singleHeight * i + singleHeight;
            //绘制文本 字母
            canvas.drawText(mLetters[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    //事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标

        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

        //  触摸的字母的位置
        final int c = (int) (y / getHeight() * mLetters.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.


        switch (action) {
            case MotionEvent.ACTION_UP:
                //背景View颜色  透明
                this.setBackgroundDrawable(new ColorDrawable(0x00000000));

                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                if (oldChoose != c) {//点击是不是同一个
                    if (c >= 0 && c < mLetters.length) {
                        //回调接口
                        if (listener != null) {
                            listener.onTouchingLetterChanged(mLetters[c]);
                        }

                        if (mTextDialog != null) {
                            mTextDialog.setText(mLetters[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener listener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}
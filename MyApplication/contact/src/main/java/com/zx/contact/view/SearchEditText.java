package com.zx.contact.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.zx.contact.R;

/**
 * Created time : 2017/1/13 14:54.
 *
 * @author HY
 *         带有搜索和清除的EditText
 */
@SuppressWarnings("deprecation")
public class SearchEditText extends EditText {
    private Drawable leftIcon;
    private Drawable rightIcon;

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        leftIcon = getResources().getDrawable(R.mipmap.search_bar_icon_normal);
        rightIcon = getResources().getDrawable(R.mipmap.emotionstore_progresscancelbtn);
        leftIcon.setBounds(0, 0, leftIcon.getIntrinsicWidth(),
                leftIcon.getIntrinsicHeight());
        rightIcon.setBounds(0, 0, rightIcon.getIntrinsicWidth(),
                rightIcon.getIntrinsicHeight());
        showRightIcon(false);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showRightIcon(s.toString().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && null != mListener) {
                    mListener.onSearchClick(getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 设置是否显示清除按钮
     */
    private void showRightIcon(boolean isVisible) {
        setCompoundDrawables(leftIcon, null, isVisible ? rightIcon : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            if (x > getWidth() - rightIcon.getIntrinsicWidth() && x < getWidth()) {
                setText("");
                clearFocus();
                if (null != mClearListener)
                    mClearListener.onClearClick();
            } else if (x > 0 && x < rightIcon.getIntrinsicWidth()) {
                if (null != mListener)
                    mListener.onSearchClick(getText().toString().trim());
            }
        }
        return super.onTouchEvent(event);
    }

    private OnSearchClickListener mListener;

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        mListener = onSearchClickListener;
    }

    /**
     * Create Time: 2017-1-23
     *
     * @author HY
     *         搜索的点击回调接口
     */
    public interface OnSearchClickListener {
        void onSearchClick(CharSequence ch);
    }


    private OnClearClickListener mClearListener;

    public void setOnClearClickListener(OnClearClickListener onClearClickListener) {
        mClearListener = onClearClickListener;
    }

    /**
     * Create Time：2017-1-23
     *
     * @author HY
     *         清除的点击回调接口
     */
    public interface OnClearClickListener {
        void onClearClick();
    }
}

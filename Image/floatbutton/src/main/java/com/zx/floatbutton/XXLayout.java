package com.zx.floatbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created Time: 2017/3/2 20:40.
 *
 * @author HY
 */

public class XXLayout extends ViewGroup {
    public XXLayout(Context context) {
        this(context, null);
    }

    public XXLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XXLayout);
        int color = ta.getColor(R.styleable.XXLayout_background_color, Color.BLACK);
        int integer = ta.getInteger(R.styleable.XXLayout_width, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

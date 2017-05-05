package com.zx.eshop.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品选择器
 * Created Time: 2017/3/6 15:59.
 *
 * @author HY
 */
public class SimpleNumberPicker extends RelativeLayout {

    @BindView(R.id.image_minus)
    protected ImageView mIvMinus;
    @BindView(R.id.text_number)
    protected TextView mTvNumber;
    @BindView(R.id.image_plus)
    protected ImageView mIvPlus;

    private OnNumberChangedLister mOnNumberChangedLister;
    private final int min = 0;

    public SimpleNumberPicker(Context context) {
        this(context, null);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化填充布局
     *
     * @param context 上下文，用于获取布局渲染器
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_number_picker, this, true);
        ButterKnife.bind(this);
    }

    //设置数量和拿到数量
    public void setNumber(int number) {
        if (number < min)
            throw new UnsupportedOperationException("The min is " + min);

        mTvNumber.setText(String.valueOf(number));
    }

    public int getNumber() {
        String str = mTvNumber.getText().toString();
        return Integer.parseInt(str);
    }


    public void setOnNumberChangedLister(OnNumberChangedLister onNumberChangedLister) {
        mOnNumberChangedLister = onNumberChangedLister;
    }

    @OnClick({R.id.image_minus, R.id.image_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_minus://减少
                if (getNumber() == min) {
                    return;
                }
                setNumber(getNumber() - 1);
                break;
            case R.id.image_plus://增加
                setNumber(getNumber() + 1);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        mIvMinus.setImageResource(getNumber() == min ?
                R.drawable.btn_minus_gray : R.drawable.btn_minus);

        if (null != mOnNumberChangedLister) {
            mOnNumberChangedLister.onNumberChanged(getNumber());
        }
    }

    //######################     对外提供的数量改变的监听      #####################
    public interface OnNumberChangedLister {
        void onNumberChanged(int number);
    }

}

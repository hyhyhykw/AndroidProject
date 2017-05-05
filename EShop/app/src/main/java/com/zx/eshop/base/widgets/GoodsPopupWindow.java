package com.zx.eshop.base.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.base.wrapper.ToastWrapper;
import com.zx.eshop.network.entity.GoodsInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>
 * 自定义商品选择窗口：
 * 1.布局填充，构造方法中进行
 * 2.显示：show()弹出展示
 * 3.展示和隐藏的时候设置透明度
 * 4.商品数据的展示：需要GoodsInfo数据
 * </p>
 * Created Time: 2017/3/6 16:54.
 *
 * @author HY
 */
public class GoodsPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.image_goods)
    ImageView mIvGoods;//商品图片
    @BindView(R.id.text_goods_price)
    TextView mTvGoodsPrice;//商品价格
    @BindView(R.id.text_inventory_value)
    TextView mTvInventory;//库存数量
    @BindView(R.id.text_number_value)
    TextView mTvNumber;//购买数量
    @BindView(R.id.number_picker)
    SimpleNumberPicker mNumberPicker;//数量选择

    private BaseActivity mActivity;
    private GoodsInfo mGoodsInfo;
    private final ViewGroup mParent;

    private OnConfirmListener mOnConfirmListener;

    public GoodsPopupWindow(BaseActivity activity, @NonNull GoodsInfo goodsInfo) {
        mActivity = activity;
        mGoodsInfo = goodsInfo;

        //最上层的View
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, mParent, false);
        //设置布局
        setContentView(view);
        //设置宽高
        Context context = mParent.getContext();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(context.getResources().getDimensionPixelSize(R.dimen.size_400));

        setBackgroundDrawable(new ColorDrawable());
        // 设置焦点等
        setOutsideTouchable(true);
        setFocusable(true);
        //有软键盘，调整窗体的大小，留出软键盘的空间
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //效果：弹出和隐藏的时候，设置窗体背景的透明度

        setOnDismissListener(this);

        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        Picasso.with(mParent.getContext())
                .load(mGoodsInfo.getImg().getLarge())
                .into(mIvGoods);

        mTvGoodsPrice.setText(mGoodsInfo.getShopPrice());
        mTvInventory.setText(String.valueOf(mGoodsInfo.getNumber()));
        mNumberPicker.setOnNumberChangedLister(new SimpleNumberPicker.OnNumberChangedLister() {
            @Override
            public void onNumberChanged(int number) {
                mTvNumber.setText(String.valueOf(number));
            }
        });
    }

    // 显示窗口
    public void show(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
        showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
//        mNumberPicker.setNumber(1);
    }

    @OnClick(R.id.button_ok)
    public void onClick() {
        int number = mNumberPicker.getNumber();

        if (number == 0) {
            ToastWrapper.show(R.string.goods_msg_must_choose_number);
            return;
        }
        // TODO: 2017/3/6
        mOnConfirmListener.onConfirm(number);
        dismiss();
    }

    //弹窗消失的时候会调用
    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }

    //设置窗体透明度的方法
    private void backgroundAlpha(float alpha) {
        //改变窗体属性,更改窗体透明度的方法
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        mActivity.getWindow().setAttributes(params);
    }

    //#########################      确定按钮的监听       #############################
    public interface OnConfirmListener {
        void onConfirm(int number);
    }
}

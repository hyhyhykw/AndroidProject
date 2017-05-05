package com.feicuiedu.eshop_0221.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.feicuiedu.eshop_0221.R;
import com.feicuiedu.eshop_0221.network.entity.GoodsInfo;

import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/3/6.
 */
// 商品选择的弹窗
public class GoodsPopupWindow extends PopupWindow{

    private final ViewGroup mParent;

    /**
     * 1. 布局填充：构造方法里面
     * 2. 显示：show方法，弹出展示
     * 3. 弹出和隐藏的时候设置背景的透明度
     * 4. 商品数据的展示：需要数据，所以可以在构造方法中传递数据
     */

    public GoodsPopupWindow(BaseActivity activity, @NonNull GoodsInfo goodsInfo) {
        // 布局填充
        // 最上层的View
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = mParent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, mParent, false);
        // 设置布局
        setContentView(view);

        // 设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(context.getResources().getDimensionPixelSize(R.dimen.size_400));

        // 设置背景。。。
        setBackgroundDrawable(new ColorDrawable());
        // todo 设置焦点等都可以

        ButterKnife.bind(this,view);
    }

    // 展示PopupWindow
    public void show(){
        // 从哪显示出来
        showAtLocation(mParent, Gravity.BOTTOM,0,0);
    }
}

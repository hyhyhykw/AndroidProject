package com.zx.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.zx.eshop.network.EShopClient;
import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.core.UICallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created Time: 2017/2/24 16:23.
 *
 * @author HY
 *         Activity基类，包含绑定操作和跳转
 */
public abstract class BaseActivity extends TransitionActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this);
        //初始化视图
        initView();
    }

    /**
     * 绑定视图的操作
     */
    protected abstract void initView();

    /**
     * 获取视图id
     *
     * @return 视图id
     */
    @LayoutRes
    protected abstract int getContentViewLayout();

    /**
     * 在基类中请求的方法
     *
     * @param apiInterface 请求的接口api
     * @return 请求
     */
    protected Call enqueue(final ApiInterface apiInterface) {
        UICallback uiCallback = new UICallback() {
            @Override
            protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
                //处理数据
                BaseActivity.this.onBusinessResponse(apiInterface.getPath(), isSuccess, responseEntity);
            }
        };
        return EShopClient.getInstance().enqeue(apiInterface, uiCallback, getClass().getSimpleName());
    }

    //处理数据，子类实现
    protected abstract void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EShopClient.getInstance().cancelByTag(getClass().getSimpleName());
        mUnbinder.unbind();
        mUnbinder = null;
    }
}

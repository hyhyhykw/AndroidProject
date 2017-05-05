package com.zx.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.eshop.network.EShopClient;
import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.core.UICallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created Time: 2017/2/24 16:39.
 *
 * @author HY
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 获取内容视图
     *
     * @return 视图id
     */
    @LayoutRes
    protected abstract int getContentLayout();

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
                BaseFragment.this.onBusinessResponse(apiInterface.getPath(), isSuccess, responseEntity);
            }
        };
        return EShopClient.getInstance().enqeue(apiInterface, uiCallback, getClass().getSimpleName());
    }

    //处理数据，子类实现

    /**
     * 处理网络请求的结果
     *
     * @param path           路径
     * @param isSuccess      是否成功
     * @param responseEntity 结果的实体类
     */
    protected abstract void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity);


    @Override
    public void onDestroy() {
        super.onDestroy();
//        EShopClient.getInstance().cancelByTag(getClass().getSimpleName());
        mUnbinder.unbind();
        mUnbinder = null;
    }
}

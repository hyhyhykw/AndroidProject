package com.feicuiedu.eshop_0221.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.eshop_0221.network.EShopClient;
import com.feicuiedu.eshop_0221.network.core.ApiInterface;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.core.UICallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by gqq on 2017/2/24.
 */
// 通用的Fragment的基类
public abstract class BaseFragment extends Fragment{

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getContentViewLayout(),container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    // 提供一个请求方法
    protected Call enqueue(final ApiInterface apiInterface){

        UICallback uiCallback = new UICallback() {
            @Override
            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
                // 通过一个抽象方法将数据传递出去
                BaseFragment.this.onBusinessResponse(apiInterface.getPath(),isSucces,responseEntity);
            }
        };

        return EShopClient.getInstance().enqueue(apiInterface,uiCallback,getClass().getSimpleName());
    }

    protected abstract void onBusinessResponse(String path,boolean isSucces, ResponseEntity responseEntity);

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EShopClient.getInstance().CancelByTag(getClass().getSimpleName());
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @LayoutRes protected abstract int getContentViewLayout();
    protected abstract void initView();
}

package com.zx.eshop.feature.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.eshop.base.BaseFragment;
import com.zx.eshop.network.core.ResponseEntity;

/**
 * Created Time: 2017/2/24 9:50.
 *
 * @author HY
 */

public class CartFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
        // TODO: 2017/2/24
    }

    @Override
    protected int getContentLayout() {
        // TODO: 2017/2/24
        return 0;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        // TODO: 2017/3/3
    }
}

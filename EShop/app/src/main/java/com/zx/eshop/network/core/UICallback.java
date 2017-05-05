package com.zx.eshop.network.core;

import java.io.IOException;

import android.os.Handler;
import android.os.Looper;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.zx.eshop.R;
import com.zx.eshop.base.utils.LogUtils;
import com.zx.eshop.base.wrapper.ToastWrapper;
import com.zx.eshop.network.EShopClient;

/**
 * Created Time: 2017/2/23 10:13.
 *
 * @author HY
 */

public abstract class UICallback implements Callback {

    /* 主线程的Handler */
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Class<? extends ResponseEntity> mResponseType;

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailureInUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onResponseInUI(call, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 对成功和失败的处理
     *
     * @param call 请求
     * @param e    网络异常
     */
    public void onFailureInUI(Call call, IOException e) {
        ToastWrapper.show(R.string.error_network);
        LogUtils.e("onFailureInUI", e);
    }

    /**
     * ui线程中执行的响应成功方法
     *
     * @param call     请求
     * @param response 响应
     * @throws IOException 网络错误
     */
    private void onResponseInUI(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            ResponseEntity responseEntity = EShopClient.getInstance().getResponseEntity(response, mResponseType);
            //发生未知错误
            if (null == responseEntity || null == responseEntity.getStatus()) {
                throw new RuntimeException("Fatal Api Error");
            }
            //判断是否拿到数据
            if (responseEntity.getStatus().isSucceed()) {
                //成功，有数据
                onBusinessResponse(true, responseEntity);
            } else {
                ToastWrapper.show(responseEntity.getStatus().getErrorDesc());
                onBusinessResponse(false, responseEntity);
            }
        }
    }

    //让使用者告诉我们实际的实体类型
    public void setResponseType(Class<? extends ResponseEntity> responseType) {
        mResponseType = responseType;
    }

    //给使用者实现的方法，处理拿到的数据
    protected abstract void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity);


}

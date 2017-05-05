package com.zx.easyshop.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created Time: 2017/2/9 17:40.
 *
 * @author HY
 *         将Callback中获取的结果传到主线程中
 */
public abstract class UICallback implements Callback {

    //将子线程中的消息传的主线程中
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailureInUi(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("code error" + response.code());
        final String json = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponseInUi(call, json);
            }
        });
    }

    //主线程中 响应失败
    public abstract void onFailureInUi(Call call, IOException e);

    //主线程中 响应成功
    public abstract void onResponseInUi(Call call, String json);

}

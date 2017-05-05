package com.zx.eshop.network;

import com.google.gson.Gson;
import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.core.UICallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created Time: 2017/2/23 10:09.
 *
 * @author HY
 *         网络请求的客户端，单例模式
 */

public class EShopClient {
    private static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";
    private static EShopClient mESopClient;

    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;

    private EShopClient() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        //网络连接客户端
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        mGson = new Gson();
    }

    //线程同步 获取当前类的实例
    public static synchronized EShopClient getInstance() {
        if (null == mESopClient) {
            mESopClient = new EShopClient();
        }
        return mESopClient;
    }

    //单元测试的时候同步请求直接拿到请求，在代码中使用异步回调的方式
    //为了方便，可以把同步和异步都提取出来

    /**
     * 同步请求网络，直接拿到Response里的实体类
     *
     * @param apiInterface 请求的api
     * @param <T>          实体类的类型
     * @return 实体类的对象
     * @throws IOException 网络访问失败
     */
    @SuppressWarnings("unchecked")
    <T extends ResponseEntity> T execute(ApiInterface apiInterface) throws IOException {
        //请求的构建放在一个方法里
        Response response = newApiCall(apiInterface, null).execute();
        //异步中也会用到，也防到一个方法中去
        Class<T> clazz = (Class<T>) apiInterface.getResponseEntity();
        return getResponseEntity(response, clazz);
    }


    /**
     * <p>
     * 异步回调方式请求数据,为了规范，在方法中直接执行异步方法，
     * 就需要一个UICallback,所以通过参数传递
     * </p>
     *
     * @param apiInterface 请求api
     * @param uiCallback   异步回调
     * @return 请求
     */
    public Call enqeue(ApiInterface apiInterface, UICallback uiCallback, String tag) {
        Call call = newApiCall(apiInterface, tag);
        uiCallback.setResponseType(apiInterface.getResponseEntity());
        call.enqueue(uiCallback);
        return call;
    }


    /**
     * 根据响应获取响应的实体类
     *
     * @param response 响应
     * @param clazz    实体类的class对象
     * @param <T>      实体类的类型
     * @return 实体类对象
     * @throws IOException 访问失败
     */
    public <T extends ResponseEntity> T getResponseEntity(Response response, Class<T> clazz) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Response code is " + response.code());
        return mGson.fromJson(response.body().string(), clazz);
    }

    /**
     * 根据参数构建请求
     *
     * @param apiInterface 请求的API
     * @param tag          tag，用于取消网络请求
     * @return 请求
     */
    private Call newApiCall(ApiInterface apiInterface, String tag) {
        Builder builder = new Builder();
        builder.url(BASE_URL + apiInterface.getPath());
        if (null != apiInterface.getRequestParams()) {
            String json = mGson.toJson(apiInterface.getRequestParams());
            RequestBody requestBody = new FormBody.Builder()
                    .add("json", json)
                    .build();
            builder.post(requestBody);
        }
        //给builder设置tag，用于找到请求并取消
        builder.tag(tag);
        Request request = builder.build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * <p>
     * 通过传入的Tag取消网络请求
     * 1.在请求中设置tag
     * 2.在取消的方法中根据tag取消请求
     * </p>
     *
     * @param tag tag,用于取消网络请求
     */
    public void cancelByTag(String tag) {
        /**
         * 1.在调度器中等待执行的请求
         * 2.找到调度器中正在执行的请求
         */
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }

        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

}

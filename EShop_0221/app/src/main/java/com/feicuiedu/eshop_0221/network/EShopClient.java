package com.feicuiedu.eshop_0221.network;

import com.feicuiedu.eshop_0221.network.core.ApiInterface;
import com.feicuiedu.eshop_0221.network.core.RequestParam;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.core.UICallback;
import com.feicuiedu.eshop_0221.network.entity.CategoryRsp;
import com.feicuiedu.eshop_0221.network.entity.SearchReq;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gqq on 2017/2/23.
 */

// 网络请求的客户端
public class EShopClient {

    public static final String BASE_URL= "http://106.14.32.204/eshop/emobile/?url=";

    private static EShopClient shopClient;
    private final OkHttpClient mOkHttpClient;
    private Gson mGson;

    public static synchronized EShopClient getInstance(){
        if (shopClient==null){
            shopClient = new EShopClient();
        }
        return shopClient;
    }

    private EShopClient() {

        mGson = new Gson();

        // 日志拦截器的创建
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttpClient 的初始化
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    // 分类页面：商品分类请求
//    public Call getCategory(){
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL+"/category")
//                .build();
//        return mOkHttpClient.newCall(request);
//    }
//
//    // 首页：banner请求接口
//    public Call getHomeBanner(){
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL+"/home/data")
//                .build();
//        return mOkHttpClient.newCall(request);
//    }
//
//    // 首页：分类和推荐的商品
//    public Call getHomeCategory(){
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL+"/home/category")
//                .build();
//        return mOkHttpClient.newCall(request);
//    }
//
//    // 搜索：搜索商品
//    public Call getSearch(SearchReq searchReq){
//
//        // 构建请求体
//        String param = new Gson().toJson(searchReq);
//        RequestBody body = new FormBody.Builder()
//                .add("json",param)
//                .build();
//
//        Request request = new Request.Builder()
//                .post(body)
//                .url(BASE_URL+"/search")
//                .build();
//
//        return mOkHttpClient.newCall(request);
//    }

    // 在单元测试的时候是同步请求直接拿到结果的，代码是做异步回调的方式。
    // 为了方便，我们把同步和异步都提供出来。

    // 同步：直接拿到response里面的实体类数据
    public <T extends ResponseEntity>T execute(ApiInterface apiInterface) throws IOException {

        // 把请求的构建写到一个方法里面
        Response response = newApiCall(apiInterface,null).execute();

        // 异步里面会不会也用到呢？所以写到一个方法里去
        Class<T> clazz = (Class<T>) apiInterface.getResponseEntity();
        return getResponseEntity(response,clazz);
    }

    // 异步回调：最后要创建UICallBack
    public Call enqueue(ApiInterface apiInterface,
                        UICallback uiCallback,
                        String tag){

        // 构建call模型
        Call call = newApiCall(apiInterface,tag);
        // 告诉uicallback里面的数据要转换的类型
        uiCallback.setResponseType(apiInterface.getResponseEntity());
        // 为了规范，我们在方法里面直接执行异步方法，就需要一个UiCallback，所以通过参数传递
        call.enqueue(uiCallback);
        return call;
    }

    // 根据响应Response，将响应体转换成响应的实体类
    public  <T extends ResponseEntity>T getResponseEntity(Response response, Class<T> clazz) throws IOException {
        // 没有成功
        if (!response.isSuccessful()){
            throw new IOException("Response code is"+response.code());
        }
        // 成功，转换成相应的实体类了
        return mGson.fromJson(response.body().string(),clazz);
    }

    // 根据参数构建请求
    private Call newApiCall(ApiInterface apiInterface,String tag) {

        // 拆开写
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL+apiInterface.getPath());

        // 有请求体的话，是Post请求
        if (apiInterface.getRequestParam()!=null){
            String json = mGson.toJson(apiInterface.getRequestParam());
            RequestBody requestBody = new FormBody.Builder()
                    .add("json",json)
                    .build();
            builder.post(requestBody);
        }
        builder.tag(tag);// 给请求设置tag，为了方便取消
        Request request = builder.build();
        return mOkHttpClient.newCall(request);
    }

    /** 通过给请求设置Tag，然后取消的时候根据判断Tag来取消:tag，构建请求的时候给请求设置的。
     * 1. 给请求设置tag
     * 2. 取消的方法中根据tag来取消
    */
     public void CancelByTag(String tag){
         // 1. 在调度器中等待执行的---> 2. 调度器中正在执行的
         for (Call call:mOkHttpClient.dispatcher().queuedCalls()){
             if (call.request().tag().equals(tag)){
                 call.cancel();
             }
         }
         for (Call call:mOkHttpClient.dispatcher().runningCalls()){
             if (call.request().tag().equals(tag)){
                 call.cancel();
             }
         }

    }

}

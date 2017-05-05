package com.feicuiedu.okhttpdemo_0223.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gqq on 2017/2/23.
 */

// 单例类：OkHttpClient做单例化
public class NetClient {

    private static NetClient mNetClient;
    private final OkHttpClient mOkHttpClient;

    // 私有的构造方法
    private NetClient() {
        // 完成OkHttpClient的初始化

        // ctrl+alt+F 提取成全局变量
        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // 默认是NONE，没有信息，所以我们自己给他设置打印级别
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

    }

    // 公有的创建方法
    public static synchronized NetClient getInstance(){
        if (mNetClient==null){
            mNetClient = new NetClient();
        }
        return mNetClient;
    }
}

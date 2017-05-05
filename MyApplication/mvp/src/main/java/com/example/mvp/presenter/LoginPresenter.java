package com.example.mvp.presenter;

import android.util.Log;

import com.example.mvp.LoginView;
import com.example.mvp.model.AsyncHttp;
import com.example.mvp.utils.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HY on 2016/12/21.
 */

public class LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    protected AsyncHttp mAsyncHttp;
    protected LoginView mLoginView;
    protected AsyncHttp.OnResponseListener listener;

    public LoginPresenter(LoginView loginView, AsyncHttp.OnResponseListener listener) {
        mAsyncHttp = new AsyncHttp();
        this.mLoginView = loginView;
        this.listener = listener;
    }

    /**
     * login
     */
    public void login() {
        mLoginView.showDialog();
        Map<String, String> data = new HashMap<>();
        data.put("username", mLoginView.getUsrName());
        data.put("pwd", mLoginView.getPassword());
        Log.e(TAG, data.toString());
        mAsyncHttp.executeHttp(Constant.PATH_LOGIN, AsyncHttp.METHOD.METHOD_POST, data, listener);
    }
}

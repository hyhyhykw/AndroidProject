package com.example.mvp.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvp.R;
import com.example.mvp.model.AsyncHttp;
import com.example.mvp.utils.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HY on 2016/12/21.
 * initialize Login
 */

public class LoginView implements View.OnClickListener {
    private static final String TAG = LoginView.class.getSimpleName();

    protected EditText mEdtUser;
    protected EditText mEdtPwd;
    protected Button mBtnLogin;


    protected AsyncHttp mAsyncHttp1;


    /**
     * bind widget adt set click event
     *
     * @param activity
     */
    public LoginView(Activity activity) {
        mEdtUser = (EditText) activity.findViewById(R.id.edt_user);
        mEdtPwd = (EditText) activity.findViewById(R.id.edt_pwd);
        mBtnLogin = (Button) activity.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    public void setAsyncHttp1(AsyncHttp asyncHttp1) {
        this.mAsyncHttp1 = asyncHttp1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Map<String, String> data = new HashMap<>();
                data.put("username", mEdtUser.getText().toString());
                data.put("pwd", mEdtPwd.getText().toString());
                mAsyncHttp1.executeHttp(Constant.PATH_LOGIN, AsyncHttp.METHOD.METHOD_GET, data, new AsyncHttp.OnResponseListener() {
                    @Override
                    public void onResponseSuccess(String result) {
                        Log.e(TAG, result);
                    }

                    @Override
                    public void onResponseFail(String error) {
                        Log.e(TAG, error);
                    }
                });
                break;
        }
    }
}

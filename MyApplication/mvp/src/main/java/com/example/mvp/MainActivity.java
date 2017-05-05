package com.example.mvp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvp.model.AsyncHttp;
import com.example.mvp.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private static final String TAG = MainActivity.class.getSimpleName();

    protected EditText mEdtUser;
    protected EditText mEdtPwd;
    protected Button mBtnLogin;
    protected Button mBtnClear;
    protected ProgressDialog mPgd;
    protected AsyncHttp.OnResponseListener listener = new AsyncHttp.OnResponseListener() {
        @Override
        public void onResponseSuccess(String result) {
            Toast.makeText(MainActivity.this, "result=" + result, Toast.LENGTH_SHORT).show();
            cancelDialog();
        }

        @Override
        public void onResponseFail(String error) {
            Log.e(TAG, error);
            cancelDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEdtUser = (EditText) findViewById(R.id.edt_user);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mBtnLogin.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LoginPresenter presenter = new LoginPresenter(this, listener);
                presenter.login();
                break;
            case R.id.btn_clear:
                clear();
                break;
        }
    }

    @Override
    public String getUsrName() {
        return mEdtUser.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEdtPwd.getText().toString();
    }


    @Override
    public void showDialog() {
        mPgd = ProgressDialog.show(this, "正在加载", "请稍后");
    }

    @Override
    public void cancelDialog() {
        mPgd.cancel();
    }

    @Override
    public void clear() {
        mEdtUser.setText("");
        mEdtPwd.setText("");
    }
}

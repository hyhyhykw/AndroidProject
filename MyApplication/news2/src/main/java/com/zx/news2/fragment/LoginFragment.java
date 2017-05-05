package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.utils.LoginManager;
import com.zx.news2.utils.constant.Constant;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HY on 2016/12/26.
 * login page
 *
 * @author HY
 */
public class LoginFragment extends Fragment {

    @Bind(R.id.edt_username)
    protected EditText mEdtUsername;
    @Bind(R.id.edt_pwd)
    protected EditText mEdtPwd;

    protected NewsView mNewsView;

    protected LoginManager mLoginManager;

    public void setmNewsView(NewsView mNewsView) {
        this.mNewsView = mNewsView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        mLoginManager = new LoginManager(getContext(), mNewsView);
    }

    /**
     * get data
     *
     * @return data
     */
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put(Constant.MAP_KEY_UID, mEdtUsername.getText().toString());
        data.put(Constant.MAP_KEY_PWD, mEdtPwd.getText().toString());
        data.put(Constant.MAP_KEY_DEVICE, "0");
        return data;
    }


    @OnClick({R.id.btn_regist, R.id.btn_forget, R.id.btn_login})
    public void loginClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regist:
                mNewsView.showRegist();
                break;
            case R.id.btn_forget:
                mNewsView.showForget();
                break;
            case R.id.btn_login:
                if (mEdtUsername.getText().toString().equals("")) {
                    Toast.makeText(getContext(), R.string.username_empty, Toast.LENGTH_SHORT).show();
                } else if (mEdtPwd.getText().toString().equals("")) {
                    Toast.makeText(getContext(), R.string.password_empty, Toast.LENGTH_SHORT).show();
                } else {
                    mLoginManager.login(getData());
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

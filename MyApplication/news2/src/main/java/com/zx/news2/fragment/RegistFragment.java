package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.entity.News;
import com.zx.news2.entity.User;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.LogWrapper;
import com.zx.news2.utils.CommonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HY on 2016/12/26.
 * regist page
 *
 * @author HY
 */
public class RegistFragment extends Fragment {
    private static final String TAG = RegistFragment.class.getSimpleName();

    @Bind(R.id.edt_regist_mail)
    protected EditText mEdtMail;
    @Bind(R.id.edt_regist_user)
    protected EditText mEdtName;
    @Bind(R.id.edt_regist_pwd)
    protected EditText mEdtPwd;
    @Bind(R.id.chb_agree)
    protected CheckBox mChbAgree;

    /*access network*/
    protected HttpPresenter mPresenter;
    /*ui controller*/
    protected NewsView mNewsView;


    /**
     * @param mNewsView ui controller
     */
    public void setmNewsView(NewsView mNewsView) {
        this.mNewsView = mNewsView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        mPresenter = new HttpPresenter(getContext(), mNewsView, mListener, mErrorListener);

    }

    protected Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            News<User> news = gson.fromJson(jsonObject.toString(), new TypeToken<News<User>>() {
            }.getType());
            if (news.status == 0 && news.message.equals("OK")) {
                User user = news.data;
                switch (user.result) {
                    case Constant.RESULT_SUCCESS:
                        Toast.makeText(getContext(), R.string.regist_success, Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.RESULT_USER_FILLED:
                        Toast.makeText(getContext(), R.string.regist_user_filed, Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.RESULT_USER_EXIST:
                        Toast.makeText(getContext(), R.string.regist_user_exist, Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.RESULT_EMAIL_EXIST:
                        Toast.makeText(getContext(), R.string.regist_email_exist, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            mNewsView.cancelDialog();
        }
    };

    protected Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogWrapper.e(TAG, volleyError.toString());
            mNewsView.cancelDialog();
        }
    };


    @OnClick({R.id.btn_regist_now, R.id.layout_clause})
    public void registClick(View view) {
        String email = mEdtMail.getText().toString();
        String username = mEdtName.getText().toString();
        String password = mEdtPwd.getText().toString();
        switch (view.getId()) {
            case R.id.btn_regist_now:
                if (!mChbAgree.isChecked()) {
                    Toast.makeText(getContext(), R.string.not_agree_clause, Toast.LENGTH_SHORT).show();
                } else if (!CommonUtils.isEmail(email)) {
                    Toast.makeText(getContext(), R.string.email_format_error, Toast.LENGTH_SHORT).show();
                } else if (username.equals("")) {
                    Toast.makeText(getContext(), R.string.regist_name_error, Toast.LENGTH_SHORT).show();
                } else if (!CommonUtils.isLength(password.length())) {
                    Toast.makeText(getContext(), R.string.pwd_length_error, Toast.LENGTH_SHORT).show();
                } else {
                    mPresenter.requestHttpForPost(Constant.PATH_REGIST, getData());
                }
                break;
            case R.id.layout_clause:
                mChbAgree.setChecked(!mChbAgree.isChecked());
                break;
        }
    }

    /**
     * request data
     *
     * @return data
     */
    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put(Constant.MAP_KEY_UID, mEdtName.getText().toString());
        data.put(Constant.MAP_KEY_EMAIL, mEdtMail.getText().toString());
        data.put(Constant.MAP_KEY_PWD, mEdtPwd.getText().toString());
        return data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

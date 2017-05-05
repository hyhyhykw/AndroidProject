package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zx.news2.utils.CommonUtils;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.LogWrapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HY on 2016/12/26.
 * forget password page
 *
 * @author HY
 */
public class ForgetFragment extends Fragment {

    private static final String TAG = ForgetFragment.class.getSimpleName();
    protected NewsView mNewsView;
    @Bind(R.id.edt_forget_email)
    protected EditText mEdtEmail;

    protected HttpPresenter mPresenter;

    public void setmNewsView(NewsView mNewsView) {
        this.mNewsView = mNewsView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_forget, container, false);
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
                    case 0:
                        Toast.makeText(getContext(), R.string.send_to_email, Toast.LENGTH_SHORT).show();
                        mNewsView.toUserPage(1);
                        break;
                    default:
                        Toast.makeText(getContext(), R.string.email_not_exist, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    protected Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogWrapper.e(TAG, volleyError.toString());
        }
    };


    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put(Constant.MAP_KEY_EMAIL, mEdtEmail.getText().toString());
        return data;
    }


    @OnClick(R.id.btn_ok)
    public void forgetClick(View view) {
        if (!CommonUtils.isEmail(mEdtEmail.getText().toString())) {
            Toast.makeText(getContext(), R.string.email_format_error, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.requestHttpForPost(Constant.PATH_FORGET, getData());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

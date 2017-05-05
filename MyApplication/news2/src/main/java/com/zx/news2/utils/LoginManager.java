package com.zx.news2.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.news2.R;
import com.zx.news2.entity.News;
import com.zx.news2.entity.User;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.constant.Constant;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HY on 2017/1/5.
 * login
 *
 * @author HY
 */
public class LoginManager {

    private static final String TAG = LoginManager.class.getSimpleName();
    private HttpPresenter mPresenter;
    private NewsView mNewsView;
    private Context mContext;
    private Map<String, String> data;

    public LoginManager(Context context, NewsView newsView) {
        mNewsView = newsView;
        mContext = context;
        mPresenter = new HttpPresenter(context, null, mListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogWrapper.e(TAG, volleyError.toString());
                if (null != mNewsView)
                    mNewsView.cancelDialog();
            }
        });
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
                    case Constant.RESULT_LOGIN_SUCCESS:
                        Toast.makeText(mContext, R.string.login_success, Toast.LENGTH_SHORT).show();
                        /*write login state*/
                        if (null != mNewsView) {
                            SharedPreUtils.writeLogin(mContext, news, data);
                            mNewsView.toUserPage(user.result);
                        }
                        break;
                    case Constant.RESULT_PWD_ERROR:
                        Toast.makeText(mContext, R.string.login_name_pwd_error, Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.RESULT_IP_LIMIT:
                        Toast.makeText(mContext, R.string.ip_limit, Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.RESULT_LONG_DISTANCE:
                        Toast.makeText(mContext, R.string.text_long_distance, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            if (null != mNewsView)
                mNewsView.cancelDialog();
        }
    };


    /**
     * login operate
     *
     * @param data data
     */
    public void login(Map<String, String> data) {
        this.data = data;
        mPresenter.requestHttpForPost(Constant.PATH_LOGIN, data);
    }
}

package com.zx.news2.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.zx.news2.utils.NewsView;
import com.zx.news2.model.NetNews;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HY on 2016/12/26.
 * this class help connection and get news
 *
 * @author HY
 */
public class HttpPresenter {

    private Context mContext;
    private Response.Listener<JSONObject> mListener;
    private Response.ErrorListener mErrorListener;
    private NewsView mNewsView;
    private NetNews mNetNews;

    public HttpPresenter(Context context, NewsView newsView, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this.mContext = context;
        this.mNewsView = newsView;
        this.mErrorListener = errorListener;
        this.mListener = listener;
        mNetNews = new NetNews();
    }

    /**
     * request http for get
     *
     * @param path url path
     */
    public void requestHttpForGet(String path) {
        if (null != mNewsView)
            mNewsView.showDialog();
        mNetNews.requestHttpForGet(mContext, path, mListener, mErrorListener);
    }

    /**
     * request http for post
     *
     * @param path url path
     * @param data request data
     */
    public void requestHttpForPost(String path, Map<String, String> data) {
        mNetNews.requestHtpForPost(mContext, path, mListener, mErrorListener, data);
    }

}

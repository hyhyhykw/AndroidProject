package com.zx.news2.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HY on 2016/12/26.
 * connection task
 */

public class NetNews {
    /**
     * connection url for get
     *
     * @param context       context object
     * @param path          url path
     * @param listener      response success listener
     * @param errorListener response fail listener
     */
    public void requestHttpForGet(Context context, String path, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, path, null, listener, errorListener);
        queue.add(request);
    }

    /**
     * connection url for post
     *
     * @param context       context object
     * @param url           url path
     * @param listener      response success listener
     * @param errorListener response fail listener
     * @param data          request data
     */
    public void requestHtpForPost(Context context, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Map<String, String> data) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonPostRequest request = new JsonPostRequest(url, listener, errorListener, data);
        queue.add(request);
    }

}

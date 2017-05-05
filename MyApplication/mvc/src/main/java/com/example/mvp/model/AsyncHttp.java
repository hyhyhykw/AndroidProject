package com.example.mvp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mvp.utils.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by HY on 2016/12/21.
 */

public class AsyncHttp {

    private static final String TAG = AsyncHttp.class.getSimpleName();

    protected Map<String, String> data;
    protected String method;
    protected  OnResponseListener mOnResponseListener;


    /**
     *
     * @param path
     * @param method
     * @param data
     * @param onResponseListener
     */
    public void executeHttp(String path, String method, Map<String, String> data,OnResponseListener onResponseListener) {
        this.data = data;
        this.method = method;
        this.mOnResponseListener=onResponseListener;
        StringBuffer sbf = new StringBuffer(path);
        if (method.equals(METHOD.METHOD_GET)) {
            sbf.append("?").append(DataUtils.map2String(data));
        }
        new NetTask().execute(sbf.toString());
    }


    class NetTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(method);
                if (method.equals(METHOD.METHOD_POST)) {
                    OutputStream os = conn.getOutputStream();
                    os.write(DataUtils.map2String(data).getBytes());
                    os.close();
                }
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    StringBuffer sbf = new StringBuffer();
                    byte[] bs = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bs)) != -1) {
                        sbf.append(new String(bs, 0, len));
                    }
                    return sbf.toString();
                }
                conn.disconnect();

            } catch (MalformedURLException e) {
                Log.e(TAG, "Malformed URL");
            } catch (IOException e) {
                Log.e(TAG, "IO ERROR");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (null != s) {
                mOnResponseListener.onResponseSuccess(s);
            } else {
                mOnResponseListener.onResponseFail("NET ERROR");
            }
        }
    }

    /**
     * connection method
     */
    public static class METHOD {
        public static final String METHOD_POST = "POST";
        public static final String METHOD_GET = "GET";
    }

    /**
     * receive response message
     */
    public interface OnResponseListener {
        void onResponseSuccess(String result);

        void onResponseFail(String error);
    }
}

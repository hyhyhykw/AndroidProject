package com.zx.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zx.contact.utils.LogWrapper;


/**
 * Created by HY on 2017/1/4.
 * provide common content
 *
 * @author HY
 */
public class BaseActivity extends AppCompatActivity {
    private String TAG;
    private static final String BUNDLE_KEY = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        LogWrapper.d(TAG, "onCreate");
    }

    /**
     * skip to other activity
     *
     * @param cls activity class where you want skip
     */
    public void toActivity(Class<?> cls) {
        toActivity(cls, null, null);
    }

    /**
     * skip to other activity and take extra data
     *
     * @param cls    activity class where you want skip
     * @param bundle bundle extra
     */
    public void toActivity(Class<?> cls, Bundle bundle) {
        toActivity(cls, bundle, null);
    }

    /**
     * skip to other activity and take extra data and uri data
     *
     * @param cls    activity class where you want skip
     * @param bundle bundle extra
     * @param data   extra uri data
     */
    public void toActivity(Class<?> cls, Bundle bundle, Uri data) {
        Intent intent = new Intent(this, cls);
        if (null != bundle)
            intent.putExtra(BUNDLE_KEY, bundle);
        if (null != data)
            intent.setData(data);
        startActivity(intent);
    }

    /**
     * skip to other activity and uri data
     *
     * @param action action
     * @param data   uri data
     */
    public void toActivity(String action, Uri data) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * get bundle extra from last activity
     *
     * @return bundle extra
     */
    public Bundle getBundle() {
        return getIntent().getBundleExtra(BUNDLE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogWrapper.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogWrapper.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogWrapper.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogWrapper.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogWrapper.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogWrapper.d(TAG, "onRestarte");
    }
}

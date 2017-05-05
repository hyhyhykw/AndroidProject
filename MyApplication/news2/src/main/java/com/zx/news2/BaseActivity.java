package com.zx.news2;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.zx.news2.utils.LogWrapper;

/**
 * this class provide the common part
 *
 * @author HY
 */
public class BaseActivity extends FragmentActivity {

    private String TAG;

    private static final String EXTRA_KEY_STRING = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        LogWrapper.e(TAG, "onCreate");
    }

    /**
     * skip to other activity
     *
     * @param cla class object  where you want skip
     */
    public void toActivity(Class<?> cla) {
        toActivity(cla, null, null);
    }

    /**
     * skip to other activity and take extra data
     *
     * @param cla    class object  where you want skip
     * @param bundle extra bundle data
     */
    public void toActivity(Class<?> cla, Bundle bundle) {
        toActivity(cla, bundle, null);
    }

    /**
     * skip to other activity and take extra data and uri data
     *
     * @param cla    class object  where you want skip
     * @param bundle extra bundle data
     * @param data   uri data
     */
    public void toActivity(Class<?> cla, Bundle bundle, Uri data) {
        Intent intent = new Intent(this, cla);
        if (null != bundle)
            intent.putExtra(EXTRA_KEY_STRING, bundle);
        if (null != data)
            intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogWrapper.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogWrapper.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogWrapper.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogWrapper.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogWrapper.e(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogWrapper.e(TAG, "onRestart");
    }

    /**
     * get bundle extra
     *
     * @return bundle extra
     */
    public Bundle getBundle() {
        return this.getIntent().getBundleExtra(EXTRA_KEY_STRING);
    }
}

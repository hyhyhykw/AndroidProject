package com.hy.patrol_leader.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created Time: 2017/2/24 16:39.
 *
 * @author HY
 */

public abstract class BaseFragment extends Fragment {
    private static final String EXTRA_KEY_STRING = "bundle";
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 获取内容视图
     *
     * @return 视图id
     */
    @LayoutRes
    protected abstract int getContentLayout();


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
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
        Intent intent = new Intent(getContext(), cla);
        if (null != bundle)
            intent.putExtra(EXTRA_KEY_STRING, bundle);
        if (null != data)
            intent.setData(data);
        startActivity(intent);
    }

}

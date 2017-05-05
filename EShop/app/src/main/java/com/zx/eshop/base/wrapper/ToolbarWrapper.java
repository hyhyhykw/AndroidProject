package com.zx.eshop.base.wrapper;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * <p>
 * 1.根据id找到toolbar控件，如果包含文本，也要绑定
 * 2.设置Toolbar为ActionBar
 * 3.设置标题，隐藏默认的标题，展示自己的TextView标题
 * 4.返回箭头的显示或隐藏
 * </p>
 * Created Time: 2017/2/28 15:34.
 *
 * @author HY
 *         Toolbar的封装类
 */
public class ToolbarWrapper {
    private BaseActivity mBaseActivity;
    private TextView mTvTitle;

    /**
     * 在Activity中使用
     *
     * @param activity activity
     */
    public ToolbarWrapper(BaseActivity activity) {
        mBaseActivity = activity;
        Toolbar toolbar = ButterKnife.findById(activity, R.id.standard_toolbar);
        init(toolbar);

        //标题设置不显示（TextView展示），
        setShowBack(true);//设置显示返回箭头
        setShowTitle(false);//设置显示默认标题
    }

    /**
     * 在Fragment中使用
     *
     * @param fragment
     */
    public ToolbarWrapper(BaseFragment fragment) {
        mBaseActivity = (BaseActivity) fragment.getActivity();
        Toolbar toolbar = ButterKnife.findById(fragment.getView(), R.id.standard_toolbar);
        init(toolbar);

        // Fragment显示选项菜单
        fragment.setHasOptionsMenu(true);

        // 标题不设置(TextView展示)、返回箭头没有
        setShowBack(false);
        setShowTitle(false);
    }

    /**
     * 绑定和设置ActionBar
     *
     * @param toolbar toolbar
     */
    private void init(Toolbar toolbar) {
        //找到标题的控件，
        mTvTitle = ButterKnife.findById(toolbar, R.id.standard_toolbar_title);
        mBaseActivity.setSupportActionBar(toolbar);


    }




    /**
     * @param isShowBack 是否显示返回箭头
     * @return 为了方便调用，设置返回值为自己
     */
    public ToolbarWrapper setShowBack(boolean isShowBack) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack);
        return this;
    }

    /**
     * @param isShowTitle 是否显示默认标题
     * @return 为了方便调用，设置返回值为自己
     */
    public ToolbarWrapper setShowTitle(boolean isShowTitle) {
        getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
        return this;
    }

    /**
     * 设置自定义标题
     *
     * @param resId 资源id
     * @return 为了方便调用，设置返回值为自己
     */
    public ToolbarWrapper setCustomTitle(int resId) {
        if (null == mTvTitle) throw new UnsupportedOperationException("cannot set title ");
        mTvTitle.setText(resId);
        return this;
    }


    /**
     * 获取ActionBar
     *
     * @return ActionBar
     */
    private ActionBar getSupportActionBar() {
        return mBaseActivity.getSupportActionBar();
    }

}

package com.zx.eshop.feature;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.base.TestFragment;
import com.zx.eshop.feature.category.CategoryFragment;
import com.zx.eshop.feature.home.HomeFragment;
import com.zx.eshop.network.core.ResponseEntity;

import butterknife.BindView;

public class EShopMainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar)
    protected BottomBar mBottomBar;

    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private TestFragment mCartFragment;
    private TestFragment mMineFragment;

    private Fragment mCurrentFragment;//当前显示的Fragment

    //初始化视图
    @Override
    protected void initView() {
        //看一下系统中是否已存在这些Fragment
        retrieveFragment();

        //设置导航选择的监听事件
        mBottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_eshop_main;
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            default:
                throw new UnsupportedOperationException("unsupport");
            case R.id.tab_home://首页
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                // 切换Fragment
                switchFragment(mHomeFragment);
                break;
            case R.id.tab_category://分类
                if (mCategoryFragment == null) {
                    mCategoryFragment = CategoryFragment.newInstance();
                }
                //切换Fragment
                switchFragment(mCategoryFragment);
                break;
            case R.id.tab_cart://购物车
                if (mCartFragment == null) {
                    mCartFragment = TestFragment.newInstance("CartFragment");
                }
                //切换Fragment
                switchFragment(mCartFragment);
                break;
            case R.id.tab_mine://我的
                if (mMineFragment == null) {
                    mMineFragment = TestFragment.newInstance("MineFragment");
                }
                //切换Fragment
                switchFragment(mMineFragment);
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param target 目标Fragment
     */
    private void switchFragment(Fragment target) {
        if (mCurrentFragment == target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()) {
            //需要添加到FargmentManager里，才能显示
            transaction.show(target);
        } else {
            //为了便于找到Fragment，设置一个tag
            String tag;
            if (target instanceof TestFragment) {
                tag = ((TestFragment) target).getArgumentText();
            } else {
                tag = target.getClass().getName();
            }


            //通知Fragment设置tag
            transaction.add(R.id.layout_container, target, tag);
        }
        transaction.commit();

        mCurrentFragment = target;
    }

    //恢复由于系统设置改变时的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mCategoryFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCartFragment = (TestFragment) manager.findFragmentByTag("CartFragment");
        mMineFragment = (TestFragment) manager.findFragmentByTag("MineFragment");
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != mHomeFragment) {
            //不是首页，退回首页
            mBottomBar.selectTabWithId(R.id.tab_home);
        } else {
            //是首页不关闭,推倒后台运行
            moveTaskToBack(true);
        }

    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        //处理网络请求结果的方法
    }
}

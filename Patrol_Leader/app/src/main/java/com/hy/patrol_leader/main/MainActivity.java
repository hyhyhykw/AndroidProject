package com.hy.patrol_leader.main;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseActivity;
import com.hy.patrol_leader.main.approval.ApprovalFragment;
import com.hy.patrol_leader.main.home.HomeFragment;
import com.hy.patrol_leader.main.message.MessageFragment;
import com.hy.patrol_leader.main.setting.SettingFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.main_title)
    protected TextView mMainTitle;
    @BindView(R.id.main_toobar)
    protected Toolbar mToobar;
    @BindView(R.id.bottom_bar)
    protected BottomBar mBottomBar;
    private HomeFragment mHomeFragment;
    private Fragment mCurrentFragment;
    private ApprovalFragment mApprovalFragment;
    private MessageFragment mMessageFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void init() {
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        retrieveFragment();
        switchFragment(mHomeFragment);
        mBottomBar.setOnTabSelectListener(this);
    }

    //看一下系统中是否已存在这些Fragment,没有就创建
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mApprovalFragment = (ApprovalFragment) manager.findFragmentByTag(ApprovalFragment.class.getName());
        mMessageFragment = (MessageFragment) manager.findFragmentByTag(MessageFragment.class.getName());
        mSettingFragment = (SettingFragment) manager.findFragmentByTag(SettingFragment.class.getName());

        //##########################  分界线    #########################
        if (null == mHomeFragment) mHomeFragment = new HomeFragment();
        if (null == mApprovalFragment) mApprovalFragment = new ApprovalFragment();
        if (null == mMessageFragment) mMessageFragment = new MessageFragment();
        if (null == mSettingFragment) mSettingFragment = new SettingFragment();

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
            String tag = target.getClass().getName();

            //通知Fragment设置tag
            transaction.add(R.id.main_container, target, tag);
        }
        transaction.commit();

        mCurrentFragment = target;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            default:
                throw new UnsupportedOperationException("unsupport");
            case R.id.tab_home://首页
                mMainTitle.setText(R.string.patrol);
                switchFragment(mHomeFragment);
                break;
            case R.id.tab_approval://审批
                mMainTitle.setText(R.string.audit_scheme);
                switchFragment(mApprovalFragment);
                break;
            case R.id.tab_message://消息
                mMainTitle.setText(R.string.main_message);
                switchFragment(mMessageFragment);
                break;
            case R.id.tab_setting://设置
                mMainTitle.setText(R.string.main_setting);
                switchFragment(mSettingFragment);
                break;
        }
    }
}

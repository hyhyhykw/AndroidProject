package com.hy.patrol_leader.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseActivity;
import com.hy.patrol_leader.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.cursor1)
    protected ImageView mCursor1;
    @BindView(R.id.cursor2)
    protected ImageView mCursor2;
    @BindView(R.id.btn_login)
    protected Button mBtnLogin;

    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;
    private Fragment mCurrentFragment;//当前显示的Fragment

    private boolean isLogin = true;//判断是否点击的是登录

    @Override
    protected void init() {
        retrieveFragment();
        switchFragment(mLoginFragment);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                isLogin = true;

                mCursor1.setVisibility(View.VISIBLE);
                mCursor2.setVisibility(View.INVISIBLE);

                mBtnLogin.setText(R.string.text_login);

                switchFragment(mLoginFragment);
                break;
            case R.id.tv_register:
                isLogin = false;
                mCursor1.setVisibility(View.INVISIBLE);
                mCursor2.setVisibility(View.VISIBLE);

                mBtnLogin.setText(R.string.text_register);

                switchFragment(mRegisterFragment);
                break;
            case R.id.btn_login:
                if (isLogin) {
                    // TODO: 2017/4/27 登录逻辑
                    toActivity(MainActivity.class);
                } else {
                    // TODO: 2017/4/27 注册逻辑
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupport Operation");
        }
    }

    //恢复由于系统设置改变时的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mLoginFragment = (LoginFragment) manager.findFragmentByTag(LoginFragment.class.getName());
        mRegisterFragment = (RegisterFragment) manager.findFragmentByTag(RegisterFragment.class.getName());
        if (null == mLoginFragment)
            mLoginFragment = new LoginFragment();
        if (null == mRegisterFragment)
            mRegisterFragment = new RegisterFragment();
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
            transaction.add(R.id.login_container, target, tag);
        }
        transaction.commit();

        mCurrentFragment = target;
    }
}

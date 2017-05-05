package com.zx.easyshop.user.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.components.ProgressDialogFragment;
import com.zx.easyshop.main.MainActivity;
import com.zx.easyshop.user.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author HY
 *         登陆页面
 */
@SuppressWarnings("ConstantConditions")
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.et_username)
    protected EditText mEtUsername;
    @BindView(R.id.et_pwd)
    protected EditText mEtPwd;
    @BindView(R.id.btn_login)
    protected Button mBtnLogin;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private Unbinder mUnbinder;
    private ActivityUtils mActivityUtils;
    private String username;
    private String password;
    private ProgressDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        init();
    }

    //初始化视图
    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEtUsername.addTextChangedListener(mTextWatcher);
        mEtPwd.addTextChangedListener(mTextWatcher);
    }


    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                mActivityUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login:
                presenter.login(username, password);
                break;
        }
    }

    //编辑框文字改变事件监听对象
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = mEtUsername.getText().toString();
            password = mEtPwd.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));

            mBtnLogin.setEnabled(canLogin);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    //#######################      视图接口         #######################################
    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showPrb() {
        //关闭键盘
        mActivityUtils.hideSoftKeyboard();
        //判断是否为空，初始化
        if (null == mDialogFragment) mDialogFragment = new ProgressDialogFragment();
        if (mDialogFragment.isVisible()) return;
        //显示弹窗
        mDialogFragment.show(getSupportFragmentManager(), "login_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        mDialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void loginSuccess() {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void loginFailed() {
        mEtPwd.setText("");
    }
}
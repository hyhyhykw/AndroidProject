package com.zx.easyshop.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.commons.RegexUtils;
import com.zx.easyshop.components.AlertDialogFragment;
import com.zx.easyshop.components.ProgressDialogFragment;
import com.zx.easyshop.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressWarnings("ConstantConditions")
public class RegisterActivity extends
        MvpActivity<RegisterView, RegisterPresenter> implements RegisterView {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.et_username)
    protected EditText mEtUsername;
    @BindView(R.id.et_pwd)
    protected EditText mEtPwd;
    @BindView(R.id.et_pwdAgain)
    protected EditText mEtPwdAgain;
    @BindView(R.id.btn_register)
    protected Button mBtnRegister;


    private ActivityUtils mActivityUtils;
    private Unbinder mUnbinder;
    private String username;
    private String password;
    private String pwdAgain;
    private ProgressDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUnbinder = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        init();
    }

    //初始化视图
    private void init() {
        //添加左上角返回按钮
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEtUsername.addTextChangedListener(mTextWatcher);
        mEtPwd.addTextChangedListener(mTextWatcher);
        mEtPwdAgain.addTextChangedListener(mTextWatcher);
    }

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
            pwdAgain = mEtPwdAgain.getText().toString();

            boolean canLogin = !(TextUtils.isEmpty(username) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain));
            mBtnRegister.setEnabled(canLogin);
        }
    };

    @OnClick(R.id.btn_register)
    public void onClick() {
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            showUserPwdError(getString(R.string.username_rules));
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            showUserPwdError(getString(R.string.password_rules));
            return;
        } else if (!TextUtils.equals(password, pwdAgain)) {
            showUserPwdError(getString(R.string.username_equal_pwd));
            return;
        }
        presenter.register(username, password);
    }

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

    //####################################       视图接口    ######################
    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void showPrb() {
        //关闭键盘
        mActivityUtils.hideSoftKeyboard();
        //初始化
        if (null == mDialogFragment) mDialogFragment = new ProgressDialogFragment();
        if (mDialogFragment.isVisible()) return;
        //显示弹窗
        mDialogFragment.show(getSupportFragmentManager(), "register_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        mDialogFragment.dismiss();
    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void registerFailed() {
        mEtUsername.setText("");
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void showUserPwdError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_rules));
    }

}

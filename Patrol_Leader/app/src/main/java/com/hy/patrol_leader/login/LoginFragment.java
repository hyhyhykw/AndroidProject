package com.hy.patrol_leader.login;

import android.widget.EditText;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseFragment;

import butterknife.BindView;


/**
 * Created time : 2017/4/26 13:16.
 *
 * @author HY
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.edt_login_username)
    protected EditText mEdtUsername;
    @BindView(R.id.edt_login_password)
    protected EditText mEdtPwd;

    @Override
    protected void initView() {
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_login;
    }

    public String getUsername() {
        return mEdtUsername.getText().toString();
    }

    public String getPassword() {
        return mEdtPwd.getText().toString();
    }
}

package com.zx.easyshop.main.me.personinfo;

import java.io.IOException;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import com.google.gson.Gson;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.commons.RegexUtils;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.User;
import com.zx.easyshop.model.UserResult;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

public class NicknameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.et_nickname)
    protected EditText mEtNickname;
    @BindView(R.id.btn_save)
    protected Button mBtnSave;

    private ActivityUtils mActivityUtils;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //修改昵称
    private void init() {
        //从本地数据中获取用户类
        User user = CachePreferences.getUser();
        //设置新的用户昵称
        user.setNickname(nickname);
        Call call = EasyShopClient.newInstance().unloadUser(user);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                mActivityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                UserResult result = new Gson().fromJson(json, UserResult.class);
                if (result.getCode() != 1) {
                    mActivityUtils.showToast(result.getMsg());
                } else {
                    CachePreferences.setUser(result.getData());
                    mActivityUtils.showToast("修改成功");
                    onBackPressed();
                }
            }
        });
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        nickname = mEtNickname.getText().toString();
        if (RegexUtils.verifyNickname(nickname) != RegexUtils.VERIFY_SUCCESS) {
            mActivityUtils.showToast(R.string.nickname_rules);
        } else {
            mActivityUtils.hideSoftKeyboard();
            init();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}

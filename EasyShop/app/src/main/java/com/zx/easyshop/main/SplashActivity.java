package com.zx.easyshop.main;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.hyphenate.easeui.domain.EaseUser;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.commons.CurrentUser;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends AppCompatActivity {

    private Timer mTimer;
    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EventBus.getDefault().register(this);
        mTimer = new Timer();
        mActivityUtils = new ActivityUtils(this);

        /**
         * 账号冲突后自动退出
         * 由appphx模块HxMainService发出
         */
        if (getIntent().getBooleanExtra("AUTO_LOGIN", false)) {
            //清除本地缓存信息
            CachePreferences.clearAllData();
            //退出环信
            HxUserManager.getInstance().asyncLogout();
        }
        //如果用户已经登陆过，且未登出的，再次进入需要自动登陆
        if (CachePreferences.getUser().getName() != null &&
                !HxUserManager.getInstance().isLogin()) {
            User user = CachePreferences.getUser();
            EaseUser easeUser = CurrentUser.convert(user);
            HxUserManager.getInstance().asyncLogin(easeUser, user.getPassword());
            return;
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivityUtils.startActivity(MainActivity.class);
                if (null != mTimer) mTimer.cancel();
                finish();
            }
        }, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event) {
        //判断是否登陆成功事件
        if (event.type == HxEventType.LOGIN) {
            mActivityUtils.startActivity(MainActivity.class);
        }
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event) {
        if (event.type == HxEventType.LOGIN)
            throw new RuntimeException("login error");
    }

}

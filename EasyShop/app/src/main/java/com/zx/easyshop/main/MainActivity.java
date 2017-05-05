package com.zx.easyshop.main;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.feicuiedu.apphx.presentation.contact.list.HxContactListFragment;
import com.feicuiedu.apphx.presentation.conversation.HxConversationListFragment;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.main.me.MeFragment;
import com.zx.easyshop.main.shop.ShopFragment;
import com.zx.easyshop.model.CachePreferences;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_title)
    protected TextView mTxtTitle;
    @BindView(R.id.viewpager)
    protected ViewPager mViewpager;

    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    protected TextView[] mTxts;

    private ActivityUtils mActivityUtils;
    private Timer mTimer;
    private boolean isExit = false;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        init();
    }


    /**
     * 初始化视图及点击事件
     */
    private void init() {
        mActivityUtils = new ActivityUtils(this);
        mTimer = new Timer();
        mTxts[0].setSelected(true);
        if (null == CachePreferences.getUser().getName()) {
            mViewpager.setAdapter(unloginAdapter);
        } else {
            mViewpager.setAdapter(loginAdapter);
        }
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mTxts.length; i++) {
                    mTxts[i].setSelected(position == i);
                }
                mTxtTitle.setText(mTxts[position].getText());
                mViewpager.setCurrentItem(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //TextView 点击事件
        ButterKnife.apply(mTxts, new ButterKnife.Action<TextView>() {
            @Override
            public void apply(@NonNull TextView view, final int index) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpager.setCurrentItem(index, false);
                    }
                });
            }
        });
    }

    private FragmentStatePagerAdapter unloginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShopFragment();
                case 1:
                    return new UnLoginFragment();
                case 2:
                    return new UnLoginFragment();
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return null != mTxts ? mTxts.length : 0;
        }
    };

    //登陆后的适配器
    private FragmentStatePagerAdapter loginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShopFragment();
                case 1:  //消息
                    return new HxConversationListFragment();
                case 2: //通讯录
                    return new HxContactListFragment();
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        //点击两次退出
        if (!isExit) {
            isExit = true;
            mActivityUtils.showToast(R.string.press_again_exit);
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            if (null != mTimer) mTimer.cancel();
            finish();
        }
    }
}

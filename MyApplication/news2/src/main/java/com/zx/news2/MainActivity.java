package com.zx.news2;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zx.slidingmenu.SlidingMenu;

import com.tandong.swichlayout.SwitchLayout;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.fragment.FavoriteFragment;
import com.zx.news2.fragment.ForgetFragment;
import com.zx.news2.fragment.LeftMenuFragment;
import com.zx.news2.fragment.LoginFragment;
import com.zx.news2.fragment.NewsFragment;
import com.zx.news2.fragment.RegistFragment;
import com.zx.news2.fragment.RightMenuFragment;
import com.zx.news2.utils.NewsView;
import com.zx.news2.utils.constant.Constant;

import java.util.Timer;
import java.util.TimerTask;


/**
 * main page
 */
@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity implements NewsView {

    @Bind(R.id.txt_title)
    protected TextView mTxttitle;

    protected NewsFragment mNewsFragment;
    protected LoginFragment mLoginFragment;
    protected FavoriteFragment mFavoriteFragment;
    protected ForgetFragment mForgetFragment;
    protected RegistFragment mRegistFragment;
    protected LeftMenuFragment mLeftMenuFragment;
    protected RightMenuFragment mRightMenuFragment;

    protected Dialog mDialog;
    protected SlidingMenu mSlidingMenu;

    protected String[] titles;
    private boolean isFirstExit = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchLayout.ScaleBigLeftTop(this, false, null);
        ButterKnife.bind(this);
        initFragment();
        titles = this.getResources().getStringArray(R.array.text_title);
        add(R.id.main_container, mNewsFragment);
        initSlideMenu();
    }

    /**
     * initialize dialog
     */
    private void initView() {
        mDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_news_loading, null);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lyt_load);
        ImageView imgLoad = (ImageView) view.findViewById(R.id.img_load);
        imgLoad.setAnimation(AnimationUtils.loadAnimation(this, R.anim.load_animation));
        mDialog.setContentView(layout);
    }


    /**
     * initialize fragment
     */
    private void initFragment() {
        mNewsFragment = new NewsFragment();
        mNewsFragment.setmNewsView(this);
        mLoginFragment = new LoginFragment();
        mLoginFragment.setmNewsView(this);
        mFavoriteFragment = new FavoriteFragment();
        mFavoriteFragment.setmNewsView(this);
        mForgetFragment = new ForgetFragment();
        mForgetFragment.setmNewsView(this);
        mRegistFragment = new RegistFragment();
        mRegistFragment.setmNewsView(this);
        mLeftMenuFragment = new LeftMenuFragment();
        mLeftMenuFragment.setNewsView(this);
        mRightMenuFragment = new RightMenuFragment();
        mRightMenuFragment.setNewsView(this);
    }

    /**
     * initialize slide menu
     */
    private void initSlideMenu() {
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setBehindOffset(250);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);

        mSlidingMenu.setMenu(R.layout.menu_left);
        add(R.id.left_menu_container, mLeftMenuFragment);

        mSlidingMenu.setSecondaryMenu(R.layout.menu_right);
        add(R.id.right_menu_container, mRightMenuFragment);
    }


    /**
     * add fragment
     *
     * @param container parent view
     * @param fragment  fragment
     */
    public void add(int container, Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .add(container, fragment).commit();
    }

    /**
     * replace the fragment
     *
     * @param fragment fragment
     */
    public void replace(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment).commit();
    }

    @OnClick({R.id.img_home, R.id.img_share})
    public void imgClick(View view) {
        switch (view.getId()) {
            case R.id.img_home:
                mSlidingMenu.showMenu();
                break;
            case R.id.img_share:
                mSlidingMenu.showSecondaryMenu();
                break;
        }
    }

    /**
     * exit by 2 click
     */
    private void exitTwice() {
        if (isFirstExit) {
            isFirstExit = false;
            Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isFirstExit = true;
                }
            }, 3000);
        } else finish();
    }

    @Override
    public void onBackPressed() {
        if (mSlidingMenu.isMenuShowing()) {
            mSlidingMenu.showContent();
        } else {
            exitTwice();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void showDialog() {
        initView();
        mDialog.show();
        mDialog.getWindow().setBackgroundDrawableResource(R.mipmap.ic_transparent);
    }

    @Override
    public void cancelDialog() {
        if (null != mDialog)
            mDialog.cancel();
    }

    @Override
    public void toUserPage(int state) {
        switch (state) {
            case 0:
                toActivity(UserCenterActivity.class);
                replace(mNewsFragment);
                if (mSlidingMenu.isMenuShowing())
                    mSlidingMenu.showContent();
                break;
            default:
                replace(mLoginFragment);
                mTxttitle.setText(R.string.text_login_title);
                mSlidingMenu.showContent();
                break;
        }

    }

    @Override
    public void showRegist() {
        replace(mRegistFragment);
        mTxttitle.setText(R.string.text_regist_title);
    }

    @Override
    public void showForget() {
        replace(mForgetFragment);
        mTxttitle.setText(R.string.text_forget_title);
    }

    @Override
    public void showView(int position) {
        initState(position);
//        SharedPreUtils.writePos(this, position);
        mSlidingMenu.showContent();
    }

    /**
     * initialize main state
     *
     * @param position last page state
     */
    private void initState(int position) {
        switch (position) {
            case Constant.ITEM_NEWS:
                replace(mNewsFragment);
                break;
            case Constant.ITEM_FAVORITE:
                replace(mFavoriteFragment);
                break;
            case Constant.ITEM_LOCAL:
                break;
            case Constant.ITEM_COMMENT:
                break;
            case Constant.ITEM_PHOTO:
                break;
        }
        mTxttitle.setText(titles[position]);
    }

    @Override
    public void skip(NewsDetail newsDetail) {
        Bundle bundle = new Bundle();
        if (null != newsDetail)
            bundle.putSerializable("newsDetail", newsDetail);
        toActivity(NewsDetailActivity.class, bundle);
    }

    @Override
    public void download(String link) {
        if (mSlidingMenu.isMenuShowing())
            mSlidingMenu.showContent();
        Bundle bundle = new Bundle();
        bundle.putString("link", link);
        toActivity(DownloadActivity.class, bundle);
    }
}
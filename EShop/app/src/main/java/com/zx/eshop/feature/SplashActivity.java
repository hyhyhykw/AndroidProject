package com.zx.eshop.feature;

import android.animation.Animator;
import android.content.Intent;
import android.widget.ImageView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.network.core.ResponseEntity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {

    @BindView(R.id.image_splash)
    protected ImageView mImageSplash;


    //初始化视图
    @Override
    protected void initView() {
        mImageSplash.setAlpha(0.3f);
        mImageSplash.animate()
                .alpha(1.0f)//透明度
                .setDuration(2000)//时间
                .setListener(this)//监听
                .start();//开始动画
    }


    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        //处理网络请求的方法
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // 跳转到下一个页面
        Intent intent = new Intent(this, EShopMainActivity.class);
        startActivity(intent);
        finishWithDefault();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


}

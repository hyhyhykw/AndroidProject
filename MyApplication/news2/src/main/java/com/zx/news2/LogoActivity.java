package com.zx.news2;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * logo page
 *
 * @author HY
 */
public class LogoActivity extends BaseActivity implements Animation.AnimationListener {

    @Bind(R.id.img_logo)
    protected ImageView mImgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logo_animation_alpha);
        anim.setDuration(2000);
        anim.setAnimationListener(this);
        mImgLogo.setAnimation(anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        toActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

package com.hy.view;


import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import com.hy.view.view.TaiJiView;

public class TaiJiActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taiji);
        TaiJiView taiJi = (TaiJiView) this.findViewById(R.id.view_taiji);

//        RotateAnimation rotate = new RotateAnimation(0, 360,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        rotate.setDuration(2000);
//        rotate.setRepeatCount(Animation.INFINITE);
//        rotate.setRepeatMode(Animation.RESTART);
//        rotate.setStartOffset(-1);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.load_animation);

        taiJi.setAnimation(anim);

    }

}

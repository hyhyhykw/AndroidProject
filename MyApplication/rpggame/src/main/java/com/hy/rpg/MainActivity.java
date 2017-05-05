package com.hy.rpg;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.txt_about, R.id.txt_new_game,
            R.id.txt_continue_game, R.id.txt_setting})
    public void myClick(View view) {
        switch (view.getId()){
            case R.id.txt_about:
                toActivity(AboutActivity.class);
                break;
            case R.id.txt_new_game:
                break;
            case R.id.txt_continue_game:
                break;
            case R.id.txt_setting:
                break;
        }
    }

}

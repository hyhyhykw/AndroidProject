package com.zx.skipanim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.tandong.swichlayout.BaseEffects;
import com.tandong.swichlayout.SwichLayoutInterFace;
import com.tandong.swichlayout.SwitchLayout;

/**
 * SwitchLayout
 * <p>
 * QQ 852041173
 * <p>
 * 为Android提供IOS平台自有的界面视图切换动画而开发此库，工作量也不小，感谢支持SwitchLayout
 *
 * @author Tan Dong（谭东） 2014.12.28
 */
public class SecondActivity extends Activity implements SwichLayoutInterFace {
    private int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initIntent();
        // 设置进入Activity的Activity特效动画，同理可拓展为布局动画
        setEnterSwichLayout();
    }

    private void initIntent() {
        Intent intent = getIntent();
        key = intent.getExtras().getInt("key");
        ImageView iv_back = (ImageView) this.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setExitSwichLayout();
            }
        });
    }

    // 按返回键时退出Activity的Activity特效动画
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setExitSwichLayout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setEnterSwichLayout() {
        switch (key) {
            case 0:
                SwitchLayout.get3DRotateFromLeft(this, false, null);
                // 三个参数分别为（Activity/View，是否关闭Activity，特效（可为空））
                break;
            case 1:
                SwitchLayout.getSlideFromBottom(this, false,
                        BaseEffects.getMoreSlowEffect());
                break;
            case 2:
                SwitchLayout.getSlideFromTop(this, false,
                        BaseEffects.getReScrollEffect());
                break;
            case 3:
                SwitchLayout.getSlideFromLeft(this, false,
                        BaseEffects.getLinearInterEffect());
                break;
            case 4:
                SwitchLayout.getSlideFromRight(this, false, null);
                break;
            case 5:
                SwitchLayout.getFadingIn(this);
                break;
            case 6:
                SwitchLayout.ScaleBig(this, false, null);
                break;
            case 7:
                SwitchLayout.FlipUpDown(this, false,
                        BaseEffects.getQuickToSlowEffect());
                break;
            case 8:
                SwitchLayout.ScaleBigLeftTop(this, false, null);
                break;
            case 9:
                SwitchLayout.getShakeMode(this, false, null);
                break;
            case 10:
                SwitchLayout.RotateLeftCenterIn(this, false, null);
                break;
            case 11:
                SwitchLayout.RotateLeftTopIn(this, false, null);
                break;
            case 12:
                SwitchLayout.RotateCenterIn(this, false, null);
                break;
            case 13:
                SwitchLayout.ScaleToBigHorizontalIn(this, false, null);
                break;
            case 14:
                SwitchLayout.ScaleToBigVerticalIn(this, false, null);
                break;
            default:
                break;
        }

    }

    @Override
    public void setExitSwichLayout() {
        switch (key) {
            case 0:
                //3D 翻转
                SwitchLayout.get3DRotateFromRight(this, true, null);
                break;
            case 1:
                //底部滑入
                SwitchLayout.getSlideToBottom(this, true,
                        BaseEffects.getMoreSlowEffect());
                break;
            case 2:
                //顶部划入
                SwitchLayout.getSlideToTop(this, true,
                        BaseEffects.getReScrollEffect());
                break;
            case 3:
                //左滑
                SwitchLayout.getSlideToLeft(this, true,
                        BaseEffects.getLinearInterEffect());
                break;
            case 4:
                //右滑
                SwitchLayout.getSlideToRight(this, true, null);
                break;
            case 5:
                //淡入淡出
                SwitchLayout.getFadingOut(this, true);
                break;
            case 6:
                //中心缩放
                SwitchLayout.ScaleSmall(this, true, null);
                break;
            case 7:
                //上下翻转
                SwitchLayout.FlipUpDown(this, true,
                        BaseEffects.getQuickToSlowEffect());
                break;
            case 8:
                //左上角缩放
                SwitchLayout.ScaleSmallLeftTop(this, true, null);
                break;
            case 9:
                //震动
                SwitchLayout.getShakeMode(this, true, null);
                break;
            case 10:
                //左侧中心旋转
                SwitchLayout.RotateLeftCenterOut(this, true, null);
                break;
            case 11:
                //左上角旋转
                SwitchLayout.RotateLeftTopOut(this, true, null);
                break;
            case 12:
                //中心旋转
                SwitchLayout.RotateCenterOut(this, true, null);
                break;
            case 13:
                //横向展开
                SwitchLayout.ScaleToBigHorizontalOut(this, true, null);
                break;
            case 14:
                //纵向展开
                SwitchLayout.ScaleToBigVerticalOut(this, true, null);
                break;
            default:
                break;
        }
    }
}
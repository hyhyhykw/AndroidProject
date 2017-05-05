package com.zx.eshop.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zx.eshop.R;

/**
 * Created Time: 2017/2/24 19:30.
 *
 * @author HY
 *         转场动画
 */

public class TransitionActivity extends AppCompatActivity {
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setTransitionAnimation(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        setTransitionAnimation(true);
    }

    @Override
    public void finish() {
        super.finish();
        setTransitionAnimation(false);
    }

    /**
     * 无动画效果销毁视图
     */
    public void finishWithDefault() {
        super.finish();
    }

    /**
     * 转场动画
     *
     * @param isNewActivity 是否跳转新页面
     */
    private void setTransitionAnimation(boolean isNewActivity) {
        if (isNewActivity) overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        else overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}

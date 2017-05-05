package com.hy.view;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    protected Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) this.findViewById(R.id.btn3);
        mBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        FiveStarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void myClick(View view) {
        Intent intent = new Intent(MainActivity.this, TaiJiActivity.class);
        startActivity(intent);
    }

    public void myClick2(View view) {
        Intent intent = new Intent(MainActivity.this, CircleRingActivity.class);
        startActivity(intent);
    }

    public void myClick4(View view) {
        Intent intent = new Intent(MainActivity.this, MoveBallActivity.class);
        startActivity(intent);
    }

    public void myClick5(View view) {
        Intent intent = new Intent(MainActivity.this, RingBallActivity.class);
        startActivity(intent);
    }

    public void myClick6(View view) {
        Intent intent = new Intent(MainActivity.this, TestListActivity.class);
        startActivity(intent);
    }

    public void myClick7(View view) {
        Intent intent = new Intent(MainActivity.this, BallActivity.class);
        startActivity(intent);
    }

    public void myClick8(View view) {
        Intent intent = new Intent(MainActivity.this, CacheActivity.class);
        startActivity(intent);
    }

    public void myClick9(View view) {
        Intent intent = new Intent(MainActivity.this, TimeActivity.class);
        startActivity(intent);
    }
    public void myClick10(View view) {
        Intent intent = new Intent(MainActivity.this, TouchMoveActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

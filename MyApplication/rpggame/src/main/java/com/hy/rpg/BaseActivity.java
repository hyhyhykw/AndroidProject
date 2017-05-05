package com.hy.rpg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toActivity(Class<?> cla) {
        this.toActivity(cla, null,null);
    }

    public void toActivity(Class<?> cla, Bundle bundle) {
        toActivity(cla, bundle, null);
    }

    public void toActivity(Class<?> cla, Bundle bundle, Uri data) {
        Intent intent = new Intent(this, cla);
        if (null != bundle)
            intent.putExtra("bundle", bundle);
        if (null != data)
            intent.setData(data);
        startActivity(intent);
    }

}

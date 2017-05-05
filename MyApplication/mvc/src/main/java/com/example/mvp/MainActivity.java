package com.example.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mvp.model.AsyncHttp;
import com.example.mvp.view.LoginView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginView login = new LoginView(this);
        AsyncHttp asyncHttp1 = new AsyncHttp();
        login.setAsyncHttp1(asyncHttp1);
    }
}

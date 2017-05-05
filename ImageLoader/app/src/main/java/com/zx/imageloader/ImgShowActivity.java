package com.zx.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zx.imageloader.loader.ImageLoader;

public class ImgShowActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_show);
        mImageView = (ImageView) findViewById(R.id.img_show);

        String path = getIntent().getStringExtra("path");
        ImageLoader.getInstance().loadImage(path, mImageView, true);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

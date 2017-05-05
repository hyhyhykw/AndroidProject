package com.zx.imageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zx.imageloader.loader.Images;

public class MainActivity extends AppCompatActivity {

    GridView grd;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grd = (GridView) findViewById(R.id.grd);
        mAdapter = new ImageAdapter();
        grd.setAdapter(mAdapter);
        mAdapter.setData(Images.imageThumbUrls);
        grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ImgShowActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }
}

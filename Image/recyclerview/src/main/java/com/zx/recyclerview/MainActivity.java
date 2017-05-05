package com.zx.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.zx.recyclerview.WaterfallAdapter.OnItemClickListener;
import com.zx.recyclerview.WaterfallAdapter.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int[] icons = new int[]{
            R.drawable.icon_file_apk,
            R.drawable.icon_file_archive,
            R.drawable.icon_file_audio,
            R.drawable.icon_file_video,
            R.drawable.icon_file_database,
            R.drawable.icon_file_html,
            R.drawable.icon_file_word,
            R.drawable.icon_file_excel,
            R.drawable.icon_file_text,
            R.drawable.icon_file_unknown,
            R.drawable.icon_file_image,
            R.drawable.icon_file_pdf,
            R.drawable.icon_file_powerpoint,
    };

    private RecyclerView mRecyclerView;
    private WaterfallAdapter mAdapter;
    private List<Item> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        initDatas();
        mAdapter = new WaterfallAdapter(mItems);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int index = position >= icons.length ? icons.length - 1 : position;
                mAdapter.add(position, new Item(icons[index]));
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                mAdapter.remove(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper touchHelper=new ItemTouchHelper(new MyItemTouchCallback(mAdapter));
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initDatas() {
        for (int i = 0; i < icons.length; i++) {
            mItems.add(new Item(icons[i]));
        }
    }
}

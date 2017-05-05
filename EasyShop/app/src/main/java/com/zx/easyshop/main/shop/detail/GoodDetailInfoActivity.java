package com.zx.easyshop.main.shop.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zx.easyshop.R;
import com.zx.easyshop.components.AvatarLoadOptions;
import com.zx.easyshop.network.EasyShopApi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class GoodDetailInfoActivity extends AppCompatActivity {

    public static final String IMAGES = "images";
    public static final String CURRENT_INT = "currentInt";
    @BindView(R.id.viewpager)
    protected ViewPager mViewpager;
    @BindView(R.id.indicator)
    protected CircleIndicator mIndicator;

    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_info);
        mList = new ArrayList<>();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mList = getIntent().getStringArrayListExtra(IMAGES);
        //适配器
        GoodDetailAdapter adapter = new GoodDetailAdapter(getImage(mList));
        adapter.setOnItemClickListener(new GoodDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onBackPressed();
            }
        });
        //当前所在的位置
        int currentInt = getIntent().getIntExtra(CURRENT_INT, 0);
        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(currentInt, false);
        mIndicator.setViewPager(mViewpager);
    }

    private ArrayList<ImageView> getImage(ArrayList<String> list) {
        ArrayList<ImageView> list_image = new ArrayList<>();
        for (String path : list) {
            ImageView imageView = new ImageView(this);
            ImageLoader.getInstance()
                    .displayImage(EasyShopApi.IMAGE_URL + path,
                            imageView, AvatarLoadOptions.build_item());
            list_image.add(imageView);
        }
        return list_image;
    }


    public static Intent getStartIntent(Context context, ArrayList<String> imgUri, int currentInt) {
        Intent intent = new Intent(context, GoodDetailInfoActivity.class);
        intent.putExtra(IMAGES, imgUri);
        intent.putExtra(CURRENT_INT, currentInt);
        return intent;
    }

}

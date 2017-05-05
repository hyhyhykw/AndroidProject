package com.zx.news2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.zx.news2.adapter.LeadAdapter;
import com.zx.news2.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeadActivity extends BaseActivity {

    @Bind(R.id.img_point1)
    protected ImageView mImgPoint1;
    @Bind(R.id.img_point2)
    protected ImageView mImgPoint2;
    @Bind(R.id.img_point3)
    protected ImageView mImgPoint3;
    @Bind(R.id.img_point4)
    protected ImageView mImgPoint4;
    @Bind(R.id.vpg_lead)
    protected ViewPager mVpgLead;

    protected List<ImageView> icons;

    protected ImageView[] points ;


    protected int[] images = {R.mipmap.welcome, R.mipmap.wy, R.mipmap.bd, R.mipmap.small};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        initVIew();
    }

    /**
     * initialize view
     */
    private void initVIew() {
        boolean isFirst = SharedPreUtils.getIsFirst(this);
        if (isFirst) {
            SharedPreUtils.writeIsFirst(this);
            points=initPoints();
            icons = initData();
            LeadAdapter adapter = new LeadAdapter(icons);
            mVpgLead.setAdapter(adapter);
            initEvent();
        } else {
            toActivity(LogoActivity.class);
            finish();
        }

    }

    /**
     * bind points
     * @return botom points
     */
    private ImageView [] initPoints() {
        ImageView [] points= {mImgPoint1, mImgPoint2, mImgPoint3, mImgPoint4};
        for (int i = 0; i < points.length; i++) {
            points[i].setImageAlpha(i == 0 ? 255 : 100);
        }
        return points;
    }

    /**
     * initialize click event
     */
    private void initEvent() {
        mVpgLead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    toActivity(LogoActivity.class);
                    finish();
                }
                for (int i = 0; i < points.length; i++) {
                    points[i].setImageAlpha(i == position ? 255 : 100);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * initialize data
     * @return adapter source
     */
    private List<ImageView> initData() {
        List<ImageView> icons = new ArrayList<>();
        for (int image : images) {
            ImageView img = new ImageView(this);
            img.setImageResource(image);
            icons.add(img);
        }
        return icons;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

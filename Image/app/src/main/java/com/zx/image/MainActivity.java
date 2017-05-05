package com.zx.image;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private MyAdapter mAdapter;

    private List<ImageView> mImageViews;

    private ScheduledExecutorService mScheduledExecutorService;//线程池，用于开启后台线程
    private int currentItem;//当前的item，用于图片的轮播
    private int oldPosition;//旧的item，用于改变小圆点的背景
    private List<View> dots;//存放小圆点的集合

    private TextView mTxtTitle;//图片标题

    private String[] mTitles = new String[]{//图片标题文字
            "黄垚", "吴鑫", "叫我索大人", "德芙", "我曾经很瘦"
    };
    private int[] images = new int[]{//要显示的图片id
            R.drawable.aaa,
            R.drawable.bb,
            R.drawable.cc,
            R.drawable.dd,
            R.drawable.ee
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.vp);
        //初始化图片集合
        mImageViews = new ArrayList<>();
        for (int i : images) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews.add(imageView);
        }

        //初始化小圆点
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        dots.get(0).setBackgroundResource(R.drawable.dot_focusl);
        mTxtTitle = (TextView) findViewById(R.id.tv);

        mTxtTitle.setText(mTitles[0]);

        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTxtTitle.setText(mTitles[position]);

                dots.get(position).setBackgroundResource(R.drawable.dot_focusl);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                oldPosition = position;

                currentItem = position;//用于当用户手动滑动时轮播图的处理
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启单独的后台线程
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //给线程添加一个定时的调度任务
        /**
         * Runnable command, //需要周期性执行的任务
         * long initialDelay,//第一次执行的时间
         * long delay,   //之后每次跳转的时间
         * TimeUnit unit  时间单位：s
         */
        mScheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            //确定ViewPager要跳转到哪个页面
            //使用取余的方式确定
            currentItem = (currentItem + 1) % mImageViews.size();
            mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };


    /**
     * 适配器
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}

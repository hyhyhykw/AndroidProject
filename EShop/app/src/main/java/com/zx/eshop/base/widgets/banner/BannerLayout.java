package com.zx.eshop.base.widgets.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.zx.eshop.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * <p>自动轮播控件</p>
 * <p>
 * 1.自定义轮播控件
 * 2.数据可随意设置
 * 3.自动和手动的冲突:先获得触屏的时间+轮播的时间
 * </p>
 * Created Time: 2017/2/27 11:35.
 *
 * @author HY
 */
public class BannerLayout extends RelativeLayout {

    @BindView(R.id.pager_banner)
    protected ViewPager mPagerBanner;
    @BindView(R.id.indicator)
    protected CircleIndicator mIndicator;//圆形指示器

    private static final long DURATION = 4000;
    private CycleHandler mCycleHandler;//消息处理

    private Timer mTimer;//定时器
    private TimerTask mCycleTask;//定时任务

    private long mResumeCycle;//触摸到的时间

    public BannerLayout(Context context) {
        super(context);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 视图的填充和初始化相关
     */
    private void init(Context context) {
        //merge标签一定要设置ViewGroup和attachRoot为true
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        ButterKnife.bind(this);

        mCycleHandler = new CycleHandler(this);
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(BannerAdapter adapter) {
        mPagerBanner.setAdapter(adapter);
        //将ViewPager设置给圆点指示器
        mIndicator.setViewPager(mPagerBanner);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //布局展示出来时调用的方法
        mTimer = new Timer();
        //定时的发送一些事件
        mCycleTask = new TimerTask() {
            @Override
            public void run() {
                //定时的发送一些事件
                mCycleHandler.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(mCycleTask, DURATION, DURATION);
    }

    //布局从屏幕上消失的时候调用
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //取消开启的及时任务
        mTimer.cancel();
        mCycleTask.cancel();
        mTimer = null;
        mCycleTask = null;
    }

    //切换到下一页
    private void moveToNextPosition() {
        //判断有没有设置适配器
        if (null == mPagerBanner.getAdapter()) {
            throw new IllegalStateException("You should set a banner adapter?");
        }
        //判断适配器中是否有数据
        int count = mPagerBanner.getAdapter().getCount();
        if (count == 0) return;

        int nextItem = (mPagerBanner.getCurrentItem() + 1) % count;

        mPagerBanner.setCurrentItem(nextItem, nextItem != 0);
    }

    //获取到触摸的时间
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mResumeCycle = System.currentTimeMillis() + DURATION;
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 自定义Handler，使用静态修饰符和弱引用防止内存泄漏
     */
    private static class CycleHandler extends Handler {

        WeakReference<BannerLayout> mWeakReference;

        CycleHandler(BannerLayout bannerLayout) {
            mWeakReference = new WeakReference<>(bannerLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接收和处理消息，轮播图切换到下一页
            if (null == mWeakReference) return;

            BannerLayout bannerLayout = mWeakReference.get();
            if (null == bannerLayout) return;

            //触摸时间不到4秒，不进行自动切换
            if (System.currentTimeMillis() < bannerLayout.mResumeCycle) return;

            //切换到下一页
            bannerLayout.moveToNextPosition();
        }
    }
}

package com.zx.eshop.feature.goods;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.base.widgets.GoodsPopupWindow;
import com.zx.eshop.base.wrapper.ToastWrapper;
import com.zx.eshop.base.wrapper.ToolbarWrapper;
import com.zx.eshop.network.api.ApiGoogsInfo;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.GoodsInfo;
import com.zx.eshop.network.entity.GoodsInfoRsp;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 商品页面
 * 主要切换三个fragment
 * 1.商品
 * 2.详情
 * 3.评价
 * Create Time: 2017/3/6 14:20.
 *
 * @author HY
 */
public class GoodsActivity extends BaseActivity {

    private static final String EXTRA_GOODS = "EXTRA_GOODS";

    @BindViews({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    protected List<TextView> mTvTabList;//标签

    @BindView(R.id.pager_goods)
    protected ViewPager mPagerGoods;//商品图片
    private GoodsInfo mGoodsInfo;
    private GoodsPopupWindow mGoodsPopupWindow;


    public static Intent getStartIntent(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS, goodsId);
        return intent;
    }


    @Override
    protected void initView() {
        //初始化Toolbar
        new ToolbarWrapper(this)
                .setShowBack(true)
                .setShowTitle(false);
        chooseTab(0);

        //ViewPager 拿到数据后，再设置适配器
        mPagerGoods.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                chooseTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int goodsId = getIntent().getIntExtra(EXTRA_GOODS, 0);

        enqueue(new ApiGoogsInfo(goodsId));

    }

    //设置标签TextView的样式：颜色和字体大小
    private void chooseTab(int position) {
        //页面选择时，改变标签TextView的显示
        for (int i = 0; i < mTvTabList.size(); i++) {
            mTvTabList.get(i).setSelected(i == position);
            float textSize = i == position ?
                    getResources().getDimension(R.dimen.font_large) //选中时设置为大字体
                    : getResources().getDimension(R.dimen.font_normal);//未选中的设置为普通字体
            mTvTabList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_goods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            ToastWrapper.show(R.string.action_share);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_goods;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        if (!ApiPath.GOODS_INFO.equals(path))
            throw new UnsupportedOperationException(path);
        if (!isSuccess) return;
        //获取请求到的数据
        GoodsInfoRsp goodsInfoRsp = (GoodsInfoRsp) responseEntity;
        //获取商品详情数据
        mGoodsInfo = goodsInfoRsp.getData();

        //拿到数据设置适配器
        mPagerGoods.setAdapter(new GoodsPagerAdapter(getSupportFragmentManager(), mGoodsInfo));

    }

    //Tab标签点击
    @OnClick({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    public void onClickTab(TextView view) {
        int position = mTvTabList.indexOf(view);
        chooseTab(position);
        mPagerGoods.setCurrentItem(position, false);
    }


    //按钮点击
    @OnClick({R.id.button_show_cart, R.id.button_add_cart, R.id.button_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_cart://购物车
                break;
            case R.id.button_add_cart://添加到购物车
                showGoodsPopupWindow();
                break;
            case R.id.button_buy://立即购买
                showGoodsPopupWindow();
                break;
            default:
                throw new UnsupportedOperationException("Unsupport View");
        }
    }

    //显示商品弹窗
    private void showGoodsPopupWindow() {
        if (null == mGoodsInfo) return;
        if (null == mGoodsPopupWindow)
            mGoodsPopupWindow = new GoodsPopupWindow(this, mGoodsInfo);

        mGoodsPopupWindow.show(new GoodsPopupWindow.OnConfirmListener() {
            @Override
            public void onConfirm(int number) {
                // TODO: 2017/3/7
            }
        });
    }

    //切换页面
    public void selectPage(int position){
       chooseTab(position);
        mPagerGoods.setCurrentItem(position,false);
    }

}

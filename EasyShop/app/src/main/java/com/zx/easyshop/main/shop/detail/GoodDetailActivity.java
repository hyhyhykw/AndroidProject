package com.zx.easyshop.main.shop.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.apphx.model.repository.DefaultLocalUsersRepo;
import com.feicuiedu.apphx.presentation.chat.HxChatActivity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.commons.CurrentUser;
import com.zx.easyshop.components.AvatarLoadOptions;
import com.zx.easyshop.components.ProgressDialogFragment;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.GoodDetail;
import com.zx.easyshop.model.User;
import com.zx.easyshop.network.EasyShopApi;
import com.zx.easyshop.user.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

@SuppressWarnings("ConstantConditions")
public class GoodDetailActivity extends MvpActivity<GoodDetailView, GoodDetailPresenter> implements GoodDetailView {

    private static final String UUID = "uuid";

    private static final String STATE = "state";

    @BindView(R.id.tv_goods_delete)//删除
    protected TextView mTvGoodsDelete;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.viewpager)
    protected ViewPager mViewpager;
    /*使用开源库CircleIndicator来实现ViewPager的圆点指示器。*/
    @BindView(R.id.indicator)
    protected CircleIndicator mIndicator;
    @BindView(R.id.tv_detail_name)
    protected TextView mTvDetailName;//商品名称
    @BindView(R.id.tv_detail_price)
    protected TextView mTvDetailPrice;//商品价格
    @BindView(R.id.tv_detail_master)
    protected TextView mTvDetailMaster;//发布者
    @BindView(R.id.tv_detail_describe)
    protected TextView mTvDetailDescribe;//商品描述
    @BindView(R.id.btn_detail_message)
    protected Button mBtnDetailMessage;//发消息
    @BindView(R.id.tv_goods_error)
    protected TextView mTvGoodsError;//错误信息

    private ActivityUtils mActivityUtils;
    private Unbinder mUnbinder;
    private String uuid;
    private User goods_user;
    private GoodDetailAdapter mAdapter;//适配器
    private ArrayList<ImageView> mImageViews;
    private ArrayList<String> mListUri;//存放图片路径的集合
    private AlertDialog.Builder mBuilder;


    private ProgressDialogFragment mProgressDialogFragment;//加载动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        mUnbinder = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        //左上角返回按钮
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageViews = new ArrayList<>();
        mListUri = new ArrayList<>();
        mAdapter = new GoodDetailAdapter(mImageViews);
        mAdapter.setOnItemClickListener(new GoodDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //图片详情页
                Intent intent = GoodDetailInfoActivity.getStartIntent(GoodDetailActivity.this, mListUri, position);
                startActivity(intent);
            }
        });
        mViewpager.setAdapter(mAdapter);
        init();
    }

    /**
     * 初始化视图
     */
    private void init() {
        //拿到uuid
        uuid = getIntent().getStringExtra(UUID);
        //拿到state  0商品详情页 1我的商品页面
        int btn_show = getIntent().getIntExtra(STATE, 0);
        //如果来自我的页面 显示删除TextView
        mTvGoodsDelete.setVisibility(btn_show == 1 ? View.VISIBLE : View.GONE);
        mBtnDetailMessage.setVisibility(btn_show == 1 ? View.GONE : View.VISIBLE);

        //获取详情
        presenter.getData(uuid);

        //初始化Dialog
        mBuilder = new AlertDialog.Builder(this);
        //设置标题
        mBuilder.setTitle(R.string.goods_title_delete);
        //设置信息
        mBuilder.setMessage(R.string.goods_info_delete);
        //设置按钮和点击事件
        mBuilder.setPositiveButton(R.string.goods_delete, mOnClickListener);
        mBuilder.setNegativeButton(R.string.popu_cancle, mOnClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    //提示窗点击事件
    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    presenter.delete(uuid);
                    break;
                default:
                    break;
            }
        }
    };

    @NonNull
    @Override
    public GoodDetailPresenter createPresenter() {
        return new GoodDetailPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //获取跳转意图 0商品详情页 1我的商品页面
    public static Intent getStartIntent(Context context, String uuid, int state) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra(UUID, uuid);
        intent.putExtra(STATE, state);
        return intent;
    }

    @OnClick({R.id.tv_goods_delete, R.id.btn_detail_message})
    public void onClick(View view) {
        if (CachePreferences.getUser().getName() == null) {
            mActivityUtils.startActivity(LoginActivity.class);
        } else {
            switch (view.getId()) {
                case R.id.tv_goods_delete://删除商品
                    //删除商品
                    mBuilder.show();
                    break;
                case R.id.btn_detail_message://发消息
                    //环信相关发消息
                    if (CachePreferences.getUser().getName().equals(goods_user.getName())) {
                        mActivityUtils.showToast(R.string.send_message_error);
                    } else {
                        // 发消息页面
                        DefaultLocalUsersRepo.getInstance(this).save(CurrentUser.convert(goods_user));
                        startActivity(HxChatActivity.getStartIntent(this, goods_user.getHxId()));
                    }
                    break;
            }
        }
    }

    //#############################      视图实现      #################################
    @Override
    public void showPrb() {
        if (null == mProgressDialogFragment) mProgressDialogFragment = new ProgressDialogFragment();
        if (!mProgressDialogFragment.isVisible())
            mProgressDialogFragment.show(getSupportFragmentManager(), "dialog_good_detail");
    }

    @Override
    public void hidePrb() {
        mProgressDialogFragment.dismiss();
    }

    @Override
    public void setImageData(ArrayList<String> paths) {
        mListUri = paths;
        //加载图片
        for (String path : mListUri) {
            ImageView imageView = new ImageView(this);
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + path, imageView,
                    AvatarLoadOptions.build_item());
            mImageViews.add(imageView);
        }
        mAdapter.notifyDataSetChanged();
        //圆点指示器
        mIndicator.setViewPager(mViewpager);
    }

    @Override
    public void setData(GoodDetail detail, User goodUser) {
        this.goods_user = goodUser;
        mTvDetailName.setText(detail.getName());
        mTvDetailPrice.setText(String.format("%s%s", getString(R.string.goods_price), getString(R.string.goods_money, detail.getPrice())));
        mTvDetailDescribe.setText(detail.getDescription());
        String master = null != goods_user.getNickname() ? goods_user.getNickname() : detail.getMaster();
        mTvDetailMaster.setText(getString(R.string.goods_detail_master, master));
    }

    @Override
    public void showError() {
        mTvGoodsError.setVisibility(View.VISIBLE);
        mToolbar.setTitle(R.string.goods_overdue);
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void deleteEnd() {
        // 删除结束
        mActivityUtils.showToast("删除成功");
        onBackPressed();
    }

}

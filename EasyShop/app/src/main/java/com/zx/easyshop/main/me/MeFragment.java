package com.zx.easyshop.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.components.AvatarLoadOptions;
import com.zx.easyshop.main.me.goodsupload.GoodsUploadActivity;
import com.zx.easyshop.main.me.persongoods.PersonGoodsActivity;
import com.zx.easyshop.main.me.personinfo.PersonActivity;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.network.EasyShopApi;
import com.zx.easyshop.user.login.LoginActivity;

/**
 * Created Time: 2017/2/7 18:48.
 *
 * @author HY
 */

public class MeFragment extends Fragment {

    @BindView(R.id.iv_user_head)
    protected CircularImageView mIvUserHead;
    @BindView(R.id.tv_login)
    protected TextView mTvLogin;
    @BindView(R.id.tv_person_info)
    protected TextView mTvPersonInfo;
    @BindView(R.id.tv_person_goods)
    protected TextView mTvPersonGoods;
    @BindView(R.id.tv_goods_upload)
    protected TextView mTvGoodsUpload;

    private Unbinder mUnbinder;
    private ActivityUtils mActivityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //更新用户名
        String name = CachePreferences.getUser().getName();
        String nickname = CachePreferences.getUser().getNickname();
        //如果昵称为空 显示用户名，否则显示昵称
        if (name != null) mTvLogin.setText(null != nickname ? nickname : name);
        //用户头像地址
        String url = CachePreferences.getUser().getHeadIcon();
        //更新用户头像
        if (null != url)
            ImageLoader.getInstance().
                    displayImage(EasyShopApi.IMAGE_URL + url, mIvUserHead,
                            AvatarLoadOptions.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.iv_user_head, R.id.tv_login, R.id.tv_person_info, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        if (CachePreferences.getUser().getName() == null) {
            mActivityUtils.startActivity(LoginActivity.class);
        } else {
            switch (view.getId()) {
                case R.id.iv_user_head://用户头像
                case R.id.tv_login://登陆
                case R.id.tv_person_info://个人信息
                    mActivityUtils.startActivity(PersonActivity.class);
                    break;
                case R.id.tv_person_goods://我的商品
                    // 跳转到我的商品页面
                    mActivityUtils.startActivity(PersonGoodsActivity.class);
                    break;
                case R.id.tv_goods_upload://上传商品
                    //跳转到上传商品页面
                    mActivityUtils.startActivity(GoodsUploadActivity.class);
                    break;
            }
        }
    }
}

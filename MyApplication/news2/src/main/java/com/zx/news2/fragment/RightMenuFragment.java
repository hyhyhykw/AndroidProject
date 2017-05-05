package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.utils.ShareManager;
import com.zx.news2.utils.SharedPreUtils;
import com.zx.news2.utils.UpdateManager;
import com.zx.news2.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by HY on 2016/12/26.
 * right menu
 */

public class RightMenuFragment extends Fragment {

    protected NewsView mNewsView;
    @Bind(R.id.img_user)
    protected CircleImageView mImgUser;
    @Bind(R.id.txt_login)
    protected TextView mTxtLogin;
    private int result;
    protected UpdateManager mUpdateManager;

    public void setNewsView(NewsView newsView) {
        mNewsView = newsView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        result = SharedPreUtils.getResult(getContext());
        initView();
    }

    /**
     * initialize view
     */
    private void initView() {
        mUpdateManager = new UpdateManager(getContext(), mNewsView);
        String portrait = SharedPreUtils.getPortrait(getContext());
        String uid = SharedPreUtils.getUid(getContext());
        mImgUser.setImageResource(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
        switch (result) {
            case 0:
                RequestCreator request = null;
                if (!portrait.equals(""))
                    request = Picasso.with(getContext()).load(portrait);
                if (null != request) {
                    request.into(mImgUser);
                    request.error(R.mipmap.sorry);
                }
                mTxtLogin.setText(uid);
                break;
            default:
                mTxtLogin.setText(R.string.text_now_login);
                break;
        }
    }

    @OnClick({R.id.img_user, R.id.txt_login, R.id.txt_update
            , R.id.img_share_wechat, R.id.img_share_qq,
            R.id.img_share_friends, R.id.img_share_sina})
    public void rightClick(View view) {
        switch (view.getId()) {
            case R.id.img_user:
            case R.id.txt_login://login or skip to user center
                mNewsView.toUserPage(result);
                break;
            case R.id.txt_update://version updata
                mUpdateManager.update();
                break;
            case R.id.img_share_wechat:
                ShareManager.share(Wechat.NAME, "微信分享", new Wechat.ShareParams());
                break;
            case R.id.img_share_qq:
                ShareManager.share(QQ.NAME, "QQ分享", new QQ.ShareParams());
                break;
            case R.id.img_share_friends:
                ShareManager.share(WechatMoments.NAME, "微信分享", new WechatMoments.ShareParams());
                break;
            case R.id.img_share_sina:
                ShareManager.share(SinaWeibo.NAME, "微博分享", new SinaWeibo.ShareParams());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
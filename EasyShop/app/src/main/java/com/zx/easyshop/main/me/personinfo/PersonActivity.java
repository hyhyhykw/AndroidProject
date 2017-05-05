package com.zx.easyshop.main.me.personinfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.feicuiedu.apphx.model.HxUserManager;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.components.AvatarLoadOptions;
import com.zx.easyshop.components.PicWindow;
import com.zx.easyshop.components.ProgressDialogFragment;
import com.zx.easyshop.main.MainActivity;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.ItemShow;
import com.zx.easyshop.model.User;
import com.zx.easyshop.network.EasyShopApi;

@SuppressWarnings("ConstantConditions")
public class PersonActivity extends MvpActivity<PersonView, PersonPresenter> implements PersonView {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.iv_user_head)
    protected CircularImageView mIvUserHead;
    @BindView(R.id.listView)
    protected ListView mListView;

    private ActivityUtils mActivityUtils;
    private List<ItemShow> mList = new ArrayList<>();
    private PersonAdapter mAdapter;
    private PicWindow mPicWindow;
    private ProgressDialogFragment mProgressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        //左上角返回按钮
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ListView视图
        mAdapter = new PersonAdapter(mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(onItemClickListener);
        mPicWindow = new PicWindow(this, mListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mList.clear();
        //初始化数据
        init();
        //更新头像用户
        String url = CachePreferences.getUser().getHeadIcon();
        if (null != url) updateAvater(url);
        //更新适配器
        mAdapter.notifyDataSetChanged();
    }

    //初始化数据
    private void init() {
        User user = CachePreferences.getUser();
        mList.add(new ItemShow(getResources().getString(R.string.username), user.getName()));
        mList.add(new ItemShow(getResources().getString(R.string.nickname), user.getNickname()));
        mList.add(new ItemShow(getResources().getString(R.string.hx_id), user.getHxId()));
    }


    //listview点击事件
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: //用户名
                    mActivityUtils.showToast(getResources().getString(R.string.username_update));
                    break;
                case 1://昵称
                    mActivityUtils.startActivity(NicknameActivity.class);
                    break;
                case 2://环信ID
                    mActivityUtils.showToast(getResources().getString(R.string.id_update));
                    break;
            }
        }
    };

    //弹窗监听
    private PicWindow.Listener mListener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //从相册选择
            CropHelper.clearCachedCropFile(mHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(mHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            //从相机选择 请求权限
            request();
        }
    };

    //相机权限请求码
    private static final int REQUEST_CODE_CAMERA = 666;

    //通过SDK版本判断是否需要动态请求相机权限
    public void request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            } else {
                toCamera();
            }
        } else {
            toCamera();
        }
    }

    private static final String SCHEME = "package";

    //权限请求失败调用此方法
    private void openPression() {
        Intent intent = new Intent();
        //ACTION_APPLICATION_DETAILS_SETTINGS 跳转到指定包名的应用详情界面
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(SCHEME, getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


    //从相机选择
    private void toCamera() {
        CropHelper.clearCachedCropFile(mHandler.getCropParams().uri);
        Intent intent = CropHelper.buildCaptureIntent(mHandler.getCropParams().uri);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }

    //裁剪结果处理
    private CropHandler mHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            File file = new File(uri.getPath());
            // 头像上传
            presenter.updateAvater(file);
        }

        @Override
        public void onCropCancel() {
            //裁剪取消
        }

        @Override
        public void onCropFailed(String message) {
            //裁剪失败
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 300;
            cropParams.aspectY = 300;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonActivity.this;
        }
    };


    //左上角按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //带结果跳转处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(mHandler, requestCode, resultCode, data);
    }

    //####################       权限请求返回结果处理         ########################
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toCamera();
                } else {
                    mActivityUtils.showToast("相机打开失败,请手动开启相机权限");
                    openPression();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //#########################           视图实现          ##############################
    @NonNull
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @OnClick({R.id.iv_user_head, R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击头像
            case R.id.iv_user_head:
                // 头像来源选择（相册，拍照）
                if (mPicWindow.isShowing()) mPicWindow.dismiss();
                else mPicWindow.show();
                break;
            //点击退出登录
            case R.id.btn_login_out:
                //清空本地配置
                CachePreferences.clearAllData();
                //清除所有旧的Activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //退出环信相关
                HxUserManager.getInstance().asyncLogout();
                EaseUI.getInstance().getNotifier().reset();
                break;
        }
    }

    @Override
    public void showPrb() {
        if (null == mProgressDialogFragment) mProgressDialogFragment = new ProgressDialogFragment();
        if (!mProgressDialogFragment.isVisible()) {
            mProgressDialogFragment.show(getSupportFragmentManager(), "fragment_upload_dialog");
        }
    }

    @Override
    public void hidePrb() {
        mProgressDialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void updateAvater(String url) {
        // 更新头像
        ImageLoader.getInstance().
                displayImage(EasyShopApi.IMAGE_URL + url, mIvUserHead,
                        AvatarLoadOptions.build());
    }
}

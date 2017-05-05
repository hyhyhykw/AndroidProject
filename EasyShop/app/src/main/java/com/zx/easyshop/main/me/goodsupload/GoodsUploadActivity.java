package com.zx.easyshop.main.me.goodsupload;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.commons.MyFileUtils;
import com.zx.easyshop.components.PicWindow;
import com.zx.easyshop.components.ProgressDialogFragment;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.GoodsUpload;
import com.zx.easyshop.model.ImageItem;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressWarnings("ConstantConditions")
public class GoodsUploadActivity extends MvpActivity<GoodsUploadView, GoodsUploadPresenter> implements GoodsUploadView {

    @BindView(R.id.tv_goods_delete)
    protected TextView mTvGoodsDelete;//删除图片按钮
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;//图片显示
    @BindView(R.id.et_goods_name)
    protected EditText mEtGoodsName;//商品名
    @BindView(R.id.et_goods_price)
    protected EditText mEtGoodsPrice;//商品价格
    @BindView(R.id.tv_goods_type)
    protected TextView mTvGoodsType;//商品类型显示
    @BindView(R.id.et_goods_describe)
    protected EditText mEtGoodsDescribe;//商品描述
    @BindView(R.id.btn_goods_load)
    protected Button mBtnGoodsLoad;//商品上传按钮

    private ActivityUtils mActivityUtils;
    private Unbinder mUnbinder;
    private PicWindow mPicWindow;
    //商品类型
    private final String[] goodsTypes = {"家用", "电子", "服饰", "玩具", "图书", "礼品", "其它"};
    /*商品种类为自定义*/
    private final String[] goodsTypeNum = {"household", "electron", "dress", "toy", "book", "gift", "other"};
    private String goodsName;//商品名
    private String goodsPrice;//商品价格
    //    private String goodsType = goodsTypes[0];//商品类型,默认为家用类型
    private String goodsTypeEn = goodsTypeNum[0];
    private String goodsDescrpition;//商品描述
    private ProgressDialogFragment mProgressDialogFragment;

    private GoodsUploadAdapter mUploadAdapter;//适配器
    private List<ImageItem> mItems = new ArrayList<>();
    private AlertDialog.Builder mBuilder;

    private static final String CACHE_FILE_NAME_SUFFIX = "crop_cache_file_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_up_load);
        mUnbinder = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        //左上角返回按钮
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        initDialog();
    }

    //初始化弹窗
    private void initDialog() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(R.string.goods_type_title);
        mBuilder.setItems(goodsTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                goodsType = goodsTypes[which];
                goodsTypeEn = goodsTypeNum[which];
                mTvGoodsType.setText(goodsTypes[which]);//改变类型显示
            }
        });
    }


    //初始化
    private void init() {
        //图片来源选择弹窗初始化
        mPicWindow = new PicWindow(this, mListener);
        //设置编辑框监听
        mEtGoodsName.addTextChangedListener(mTextWatcher);
        mEtGoodsDescribe.addTextChangedListener(mTextWatcher);
        mEtGoodsPrice.addTextChangedListener(mTextWatcher);
        //初始化适配器
        mUploadAdapter = new GoodsUploadAdapter(this, mItems);
        //图片点击事件
        mUploadAdapter.setOnItemClickListener(new GoodsUploadAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageItem imageItem) {
                //跳转到图片显示页面
                Bitmap image = imageItem.getBitmap();
                Intent intent = new Intent(GoodsUploadActivity.this, GoodsUploadImageShowActivity.class);
                intent.putExtra("image", image);
                intent.putExtra("width", image.getWidth());
                intent.putExtra("height", image.getHeight());
                startActivity(intent);
            }

            @Override
            public void onLongClick() {
                //显示删除
                mTvGoodsDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAddClick() {
                //添加图片
                mPicWindow.show();
            }
        });
        //设置布局管理器
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //设置item增删动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置固定大小
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mUploadAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mUploadAdapter.getMode() == GoodsUploadAdapter.MODE_MULT_SELECT) {
            //改变模式
            mUploadAdapter.changeMode(GoodsUploadAdapter.MODE_NORMAL);
            //隐藏删除
            mTvGoodsDelete.setVisibility(View.GONE);
            //改为未选择状态
            for (ImageItem imageItem : mUploadAdapter.getItems()) {
                imageItem.setIsCheck(false);
            }
        } else {
            MyFileUtils.deleteDir();
            super.onBackPressed();
        }
    }

    //弹窗监听
    private PicWindow.Listener mListener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //清除缓存
            CropHelper.clearCachedCropFile(mCropHandler.getCropParams().uri);
            //获取跳转意图
            Intent intent = CropHelper.buildCropFromGalleryIntent(mCropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            //权限请求
            request();
        }
    };

    //图片裁剪回调
    private CropHandler mCropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
//            File file = new File(uri.getPath());
            // 创建image实体
            Bitmap photo = BitmapFactory.decodeFile(uri.getPath());
            String path = CACHE_FILE_NAME_SUFFIX + System.currentTimeMillis();
            //保存图片
            MyFileUtils.saveBitmap(photo, path);

            ImageItem imageItem = new ImageItem();
            imageItem.setImagePath(path + ".jpeg");
            imageItem.setBitmap(photo);
            imageItem.setIsCheck(false);
            //添加图片
            mUploadAdapter.add(imageItem);
            mUploadAdapter.update();
        }

        @Override
        public void onCropCancel() {
            //取消裁剪
        }

        @Override
        public void onCropFailed(String message) {
            //裁剪失败
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return GoodsUploadActivity.this;
        }
    };


    //调用相机
    private void toCamera() {
        // 相机
        CropHelper.clearCachedCropFile(mCropHandler.getCropParams().uri);
        Intent intent = CropHelper.buildCaptureIntent(mCropHandler.getCropParams().uri);
        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
    }

    //处理图片裁剪结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(mCropHandler, requestCode, resultCode, data);

    }

    //点击事件
    @OnClick({R.id.tv_goods_delete, R.id.btn_goods_type, R.id.btn_goods_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                mTvGoodsDelete.setVisibility(View.GONE);
                mItems = mUploadAdapter.getItems();
                //从集合中移除
                for (int i = mItems.size() - 1; i >= 0; i--) {
                    if (mItems.get(i).isCheck())
                        mUploadAdapter.getItems().remove(i);
                }
                //切换模式
                mUploadAdapter.changeMode(GoodsUploadAdapter.MODE_NORMAL);
                mUploadAdapter.update();
                break;
            case R.id.btn_goods_type:
                mBuilder.show();
                break;
            case R.id.btn_goods_load://上传商品
                mItems = mUploadAdapter.getItems();
                if (mItems.isEmpty()) mActivityUtils.showToast("最少有一张商品图片");
                else {
                    //上传的商品实体
                    GoodsUpload goodsUpload = new GoodsUpload();
                    goodsUpload.setName(goodsName);
                    goodsUpload.setDescription(goodsDescrpition);
                    goodsUpload.setMaster(CachePreferences.getUser().getName());
                    goodsUpload.setPrice(goodsPrice);
                    goodsUpload.setType(goodsTypeEn);
                    //执行操作
                    presenter.upload(goodsUpload, mItems);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyFileUtils.deleteDir();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //编辑框文本改变监听
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //文本改变后
            goodsName = mEtGoodsName.getText().toString();
            goodsPrice = mEtGoodsPrice.getText().toString();
            goodsDescrpition = mEtGoodsDescribe.getText().toString();
            //如果编辑框为空，设置按钮不可点击
            boolean canUpload = !(TextUtils.isEmpty(goodsName) ||
                    TextUtils.isEmpty(goodsPrice) ||
                    TextUtils.isEmpty(goodsDescrpition));
            mBtnGoodsLoad.setEnabled(canUpload);
        }
    };


    //##################################     视图操作相关          ##########################################    
    @NonNull
    @Override
    public GoodsUploadPresenter createPresenter() {
        return new GoodsUploadPresenter();
    }

    @Override
    public void showPrb() {
        if (null == mProgressDialogFragment) mProgressDialogFragment = new ProgressDialogFragment();
        if (!mProgressDialogFragment.isVisible()) {
            mProgressDialogFragment.show(getSupportFragmentManager(), "fragment_goods_upload");
        }
    }

    @Override
    public void hidePrb() {
        mProgressDialogFragment.dismiss();
    }

    @Override
    public void uploadSuccess() {
        mActivityUtils.showToast("上传成功");
        MyFileUtils.deleteDir();
        onBackPressed();
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    //##################################     动态请求权限       ###################################

    //相机权限请求码
    private static final int REQUEST_CODE_CAMERA = 777;

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

}
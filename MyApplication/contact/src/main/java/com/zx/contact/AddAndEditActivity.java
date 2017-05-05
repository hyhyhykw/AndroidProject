package com.zx.contact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.contact.entity.User;
import com.zx.contact.utils.ContactWrapper;
import com.zx.contact.utils.CrapUtils;
import com.zx.contact.utils.LogWrapper;
import com.zx.contact.utils.constant.Constant;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author HY
 *         添加和编辑联系人页面
 */
@SuppressWarnings("deprecation")
public class AddAndEditActivity extends BaseActivity {

    private static final int REQUEST_CODE_ASK_WRITE_CONTACT = 666;

    @Bind(R.id.txt_title_left)
    protected TextView mTxtTitleLeft;

    @Bind(R.id.txt_title)
    protected TextView mTxtTitle;

    @Bind(R.id.txt_title_right)
    protected TextView mTxtTitleRight;
    @Bind(R.id.edt_name)
    protected EditText mEdtName;
    @Bind(R.id.edt_num)
    protected EditText mEdtNum;
    @Bind(R.id.edt_email)
    protected EditText mEdtEmail;
    @Bind(R.id.edt_address)
    protected EditText mEdtAddress;
    @Bind(R.id.img_add_icon)
    protected ImageView mImgAddIcon;

    private String bundleStr;
    private User mUser;

    private ContactWrapper mWrapper;

    protected PopupWindow mPopup;
    private Uri tempUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        ButterKnife.bind(this);
        initTitle();
        initPopup();
        bundleStr = getBundle().getString("operate");
        mWrapper = new ContactWrapper(this);
        if (bundleStr != null) {
            switch (bundleStr) {
                case Constant.ADD_USER:
                    add();
                    break;
                case Constant.EDIT_USER:
                    mUser = getBundle().getParcelable("user");
                    Bitmap defaultIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.default_contact_head_icon);
                    Bitmap photo=mUser.getPhoto();
                    if (null!=photo)
                        defaultIcon=CrapUtils.toRoundBitmap(photo);
                    mImgAddIcon.setImageBitmap(defaultIcon);
                    edit();
                    break;
            }
        }
    }

    /**
     * initialize popup window
     */
    private void initPopup() {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_icon, null);
        mPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopup.setTouchable(true);
        mPopup.setOutsideTouchable(true);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * edit user
     */
    private void edit() {
        request();
    }

    /**
     * add user
     */
    private void add() {
        request();
    }

    private void initTitle() {
        mImgAddIcon.setDrawingCacheEnabled(true);
        mImgAddIcon.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mTxtTitle.setVisibility(View.INVISIBLE);
        mTxtTitleLeft.setText(R.string.text_cancel);
        mTxtTitleRight.setText(R.string.text_save);
    }

    private void request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int locPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if (locPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_WRITE_CONTACT);
            } else {
                initView();
            }
        } else {
            initView();
        }
    }

    private void initView() {
        switch (bundleStr) {
            case Constant.ADD_USER:
                break;
            case Constant.EDIT_USER:
                mEdtName.setText(mUser.getName());
                mEdtNum.setText(mUser.getPhone());
                mEdtEmail.setText(mUser.getEmail());
                mEdtAddress.setText("");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_WRITE_CONTACT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    Toast.makeText(AddAndEditActivity.this, "权限请求失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @OnClick({R.id.txt_title_left, R.id.txt_title_right,
            R.id.img_add_icon})
    public void onClick(View view) {
        User user = new User();
        switch (view.getId()) {
            case R.id.img_add_icon://add icon
                if (null != mPopup && !mPopup.isShowing()) {
                    mPopup.showAsDropDown(view, 0, 10);
                }
                break;
            case R.id.txt_take_photo://take photo
                Toast.makeText(this, "take photo", Toast.LENGTH_SHORT).show();
                if (null != mPopup && mPopup.isShowing()) {
                    tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "icon.jpg"));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(intent, MODE.TAKE_PHOTO);
                    mPopup.dismiss();
                }
                break;
            case R.id.txt_gallery://select from gallery
                Toast.makeText(this, "select from gallery", Toast.LENGTH_SHORT).show();
                if (null != mPopup && mPopup.isShowing()) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, MODE.OPEN_ALBUM);
                    mPopup.dismiss();
                }
                break;
            case R.id.txt_title_left:
                finish();
                break;
            case R.id.txt_title_right:
                switch (bundleStr) {
                    case Constant.ADD_USER:
                        user.setName(mEdtName.getText().toString());
                        user.setPhone(mEdtNum.getText().toString());
                        user.setEmail(mEdtEmail.getText().toString());
                        user.setPhoto(isAddImgSuccess ? mImgAddIcon.getDrawingCache() : null);
                        boolean isAddSuccess = mWrapper.insert(user);
                        if (isAddSuccess) {
                            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Constant.EDIT_USER:
                        user.setRaw_contact_id(mUser.getRaw_contact_id());
                        user.setName(mEdtName.getText().toString());
                        user.setPhone(mEdtNum.getText().toString());
                        user.setEmail(mEdtEmail.getText().toString());
                        user.setPhoto(mImgAddIcon.getDrawingCache());
                        mWrapper.update(user);
                        break;
                }
                finish();
                break;
        }
    }

    private boolean isAddImgSuccess;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MODE.TAKE_PHOTO://take photo
                cutImage(tempUri);
                break;
            case MODE.OPEN_ALBUM://from album
                if (null != data)
                    cutImage(data.getData());
                else
                    Toast.makeText(this, R.string.not_select_pic, Toast.LENGTH_SHORT).show();
                break;
            case MODE.PHOTO_CROP://photo crop
                if (null != data) {
                    Bitmap photo = data.getParcelableExtra("data");
                    if (null != photo) {
                        Bitmap headIcon = CrapUtils.toRoundBitmap(photo);
                        mImgAddIcon.setImageBitmap(headIcon);
                        isAddImgSuccess = true;
                    }
                } else {
                    Toast.makeText(this, R.string.not_select_pic, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * cut image
     *
     * @param uri image data
     */
    private void cutImage(Uri uri) {
        if (uri == null) {
            LogWrapper.e("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //this action used to cut image
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, MODE.PHOTO_CROP);
    }

    /**
     * get image file mode
     *
     * @author HY
     */
    static class MODE {
        static final int OPEN_ALBUM = 0;
        static final int TAKE_PHOTO = 1;
        static final int PHOTO_CROP = 2;
    }
}

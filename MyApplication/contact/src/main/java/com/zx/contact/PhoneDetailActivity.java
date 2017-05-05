package com.zx.contact;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.contact.encoding.EncodingUtils;
import com.zx.contact.entity.User;
import com.zx.contact.utils.ContactWrapper;
import com.zx.contact.utils.CrapUtils;
import com.zx.contact.utils.constant.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneDetailActivity extends BaseActivity implements DialogInterface.OnClickListener {

    private static final int REQUEST_CODE_ASK_WRITE_CONTACT = 888;
    @Bind(R.id.txt_title_left)
    protected TextView mTxtTitleLeft;
    @Bind(R.id.txt_title)
    protected TextView mTxtTitle;
    @Bind(R.id.txt_title_right)
    protected TextView mTxtTitleRight;
    @Bind(R.id.txt_number)
    protected TextView mTxtNumber;
    @Bind(R.id.txt_email)
    protected TextView mTxtEmail;
    @Bind(R.id.txt_address)
    protected TextView mTxtAddress;
    @Bind(R.id.txt_detail_name)
    protected TextView mTxtName;
    @Bind(R.id.img_detail_icon)
    protected ImageView mImgIcon;

    private User mUser;

    private ContactWrapper mWrapper;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail);
        ButterKnife.bind(this);
        mUser = getBundle().getParcelable("user");
        mWrapper = new ContactWrapper(this);
        initDialog();
        initTitle();
        initView();
    }

    private void initDialog() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(R.string.dialod_title_delete);
        mBuilder.setMessage(R.string.sure_delete);
        mBuilder.setPositiveButton("是", this);
        mBuilder.setNegativeButton("否", this);
    }

    private void initView() {
        mTxtName.setText(mUser.getName());
        mTxtNumber.setText(mUser.getPhone());
        mTxtEmail.setText(mUser.getEmail());
        Bitmap defaultIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.default_contact_head_icon);
        Bitmap photo = mUser.getPhoto();
        if (null != photo)
            defaultIcon = CrapUtils.toRoundBitmap(photo);
        mImgIcon.setImageBitmap(defaultIcon);
        mTxtAddress.setText("");
    }


    private void initTitle() {
        mTxtTitle.setVisibility(View.INVISIBLE);
        mTxtTitleLeft.setText(R.string.text_back);
        mTxtTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.txt_title_left, R.id.img_msg, R.id.img_qrcode,
            R.id.btn_edit, R.id.btn_delete, R.id.btn_send_desktop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_title_left:
                setResult(resultCode);
                finish();
                break;
            case R.id.img_msg:
                toActivity(Intent.ACTION_SENDTO, Uri.parse("smsto://" + mUser.getPhone()));
                break;
            case R.id.btn_edit:
                Bundle bundle = new Bundle();
                bundle.putString("operate", Constant.EDIT_USER);
                bundle.putParcelable("user", mUser);
                toActivity(AddAndEditActivity.class, bundle);
                finish();
                break;
            case R.id.btn_delete:
                mBuilder.show();
                break;
            case R.id.btn_send_desktop:
                break;
            case R.id.img_qrcode://二维码
                Bitmap photo = EncodingUtils.createQRCode(mUser.toString(), 300, 300, null);
                Bundle imgBundle = new Bundle();
                imgBundle.putString("title", mUser.getName());
                imgBundle.putParcelable("photo", photo);
                toActivity(QrCodeActivity.class, imgBundle);
                break;
        }
    }


    private void request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int locPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if (locPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_WRITE_CONTACT);
            } else {
                delete();
            }
        } else {
            delete();
        }
    }

    protected int resultCode;

    private void delete() {
        boolean isSuccess = mWrapper.delete(mUser);
        if (isSuccess) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            resultCode = Constant.DELETE_SUCCESS;
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            resultCode = Constant.DELETE_FAILED;
        }
        setResult(resultCode);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_WRITE_CONTACT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    delete();
                } else {
                    // Permission Denied
                    Toast.makeText(PhoneDetailActivity.this, "权限请求失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                request();
                break;
            default:
                break;
        }
    }
}

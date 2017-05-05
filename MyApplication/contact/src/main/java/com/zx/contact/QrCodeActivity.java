package com.zx.contact;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/1/24 19:06.
 *
 * @author HY
 */

public class QrCodeActivity extends BaseActivity {

    @Bind(R.id.img_qrcode_user)
    protected ImageView mImgQrcode;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        LayoutParams params = getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        params.height = metrics.heightPixels / 2;
        params.width = metrics.widthPixels * 3 / 4;
        getWindow().setAttributes(params);
        Bundle bundle = getBundle();
        if (null != bundle) {
            photo = bundle.getParcelable("photo");
            if (null != photo) {
                mImgQrcode.setImageBitmap(photo);
            }
            String title = bundle.getString("title", "联系人");
            setTitle(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != photo)
            photo.recycle();
    }
}

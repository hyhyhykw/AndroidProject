package com.hy.filemanager;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.hy.filemanager.view.TouchImageView;

public class ImgViewActivity extends BaseActivity {

	protected TouchImageView mImg;
	protected File mImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_view);
		mImg = (TouchImageView) this.findViewById(R.id.img_image_view);
		mImage = (File) this.getIntent().getBundleExtra("bundle")
				.getSerializable("image");
		Log.e("", mImage.toString());
		mImg.setImageURI(Uri.fromFile(mImage));
		mImg.setMaxZoom(4f);
	}

}

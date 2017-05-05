package com.zx.news2;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import com.zx.news2.adapter.LoginLogAdapter;
import com.zx.news2.entity.LoginLog;
import com.zx.news2.entity.News;
import com.zx.news2.entity.UserDetail;
import com.zx.news2.model.UploadTask;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.CommonUtils;
import com.zx.news2.utils.LoginManager;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.LogWrapper;
import com.zx.news2.utils.SharedPreUtils;
import com.zx.news2.view.CircleImageView;


/**
 * user center
 *
 * @author HY
 */
@SuppressWarnings("ALL")
public class UserCenterActivity extends BaseActivity {

    private static final String TAG = UserCenterActivity.class.getSimpleName();
    @Bind(R.id.txt_title2)
    protected TextView mTxtTitle;//title
    @Bind(R.id.img_collect_news)
    protected ImageView mImgCollect;//image collected
    @Bind(R.id.img_user_head)
    protected CircleImageView mImgUserHead;//image user head
    @Bind(R.id.txt_user_name)
    protected TextView mTxtUserName;//display user name
    @Bind(R.id.txt_user_point)
    protected TextView mTxtUserPoint;//display user integration
    @Bind(R.id.txt_comment_count)
    protected TextView mTxtCommentCount; //title comment count
    @Bind(R.id.lst_login_log)
    protected ListView mLstLoginLog;//login log list

    @Bind(R.id.txt_comnum)
    protected TextView mTxtComnum;//display the number of comment

    protected HttpPresenter mPresenter;
    protected List<LoginLog> loginLogs;//login log
    protected LoginLogAdapter mAdapter;//login log adapter

    protected String token;//user token

    protected PopupWindow mPopup;
    private Uri tempUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);
        /*get user token*/
        token = SharedPreUtils.getToken(this);
        new LoginManager(this, null).login(SharedPreUtils.getLogin(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogWrapper.e(TAG, token);
        mPresenter = new HttpPresenter(this, null, mListener, mErrorListener);
        mPresenter.requestHttpForPost(Constant.PATH_USER_CENTER, getData());
        mAdapter = new LoginLogAdapter(this);
        mLstLoginLog.setAdapter(mAdapter);
        initTitle();
        initPopup();
    }

    /**
     * initialize popup window
     */
    private void initPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.upload_menu, null);
        mPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopup.setTouchable(true);
        mPopup.setOutsideTouchable(true);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
        mPopup.setAnimationStyle(R.style.anim_menu_bottombar);
    }

    /**
     * initialize view
     */
    private void initTitle() {
        mTxtTitle.setText(R.string.text_title_user_center);
        mImgCollect.setVisibility(View.INVISIBLE);
        mTxtComnum.setVisibility(View.INVISIBLE);
    }

    /**
     * get data
     *
     * @return data
     */
    Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put(Constant.MAP_KEY_IMEI, CommonUtils.getImei(this));
        data.put(Constant.MAP_KEY_TOKEN, token);
        return data;
    }

    //response success listener
    protected Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            News<UserDetail<LoginLog>> news = gson.fromJson(jsonObject.toString(), new TypeToken<News<UserDetail<LoginLog>>>() {
            }.getType());
            LogWrapper.e(TAG, news.message);
            if (news.status == 0 && news.message.equals("OK")) {
                UserDetail<LoginLog> userDetail = news.data;
                initView(userDetail);
            }
        }
    };
    //response failed listener
    protected Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogWrapper.e(TAG, volleyError.toString());
        }
    };

    /**
     * initialize view
     *
     * @param userDetail user detail
     */
    void initView(UserDetail<LoginLog> userDetail) {
        loginLogs = userDetail.loginlog;
        mAdapter.addData(loginLogs);
        mLstLoginLog.setAdapter(mAdapter);
        mAdapter.update();
        mTxtUserName.setText(userDetail.uid);
        LogWrapper.e(TAG, userDetail.portrait);
        RequestCreator request = Picasso.with(UserCenterActivity.this).load(userDetail.portrait);
        request.error(R.mipmap.sorry);
        request.into(mImgUserHead);
        mTxtCommentCount.setText("跟帖数统计:" + userDetail.comnum);
        mTxtUserPoint.setText("积分：" + userDetail.integration);
        /*write user detail*/
        SharedPreUtils.writeUser(this, userDetail);
    }

    @OnClick({R.id.img_back, R.id.btn_logoff, R.id.img_user_head})
    public void userClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logoff://log off
                SharedPreUtils.clear(this);
            case R.id.img_back://back image
                finish();
                break;
            case R.id.img_user_head://user head
                if (null != mPopup && !mPopup.isShowing()) {
                    mPopup.showAtLocation(findViewById(R.id.activity_user_center), Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.lyt_camera://use system camera
                Toast.makeText(this, R.string.text_camera, Toast.LENGTH_SHORT).show();
                if (null != mPopup && mPopup.isShowing()) {
                    tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(intent, MODE.TAKE_PHOTO);
                    mPopup.dismiss();
                }
                break;
            case R.id.lyt_gallery://use gallery
                Toast.makeText(this, R.string.text_gallery, Toast.LENGTH_SHORT).show();
                if (null != mPopup && mPopup.isShowing()) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, MODE.OPEN_ALBUM);
                    mPopup.dismiss();
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
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, MODE.PHOTO_CROP);
    }

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
                    Bitmap headIcon = data.getParcelableExtra("data");
                    if (null != headIcon) {
                        new UploadTask(headIcon, getUploadData()).execute(Constant.PATH_UPLOAD);
                    }
                } else
                    Toast.makeText(this, R.string.not_select_pic, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * get upload request data
     *
     * @return upload request data
     */
    private Map<String, String> getUploadData() {
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return data;
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
package com.zx.contact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.RawContacts;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.contact.adapter.ContactAdapter;
import com.zx.contact.entity.User;
import com.zx.contact.model.ContactContentObservers;
import com.zx.contact.utils.ContactWrapper;
import com.zx.contact.utils.DBWrapper;
import com.zx.contact.utils.LogWrapper;
import com.zx.contact.utils.PaserUtils;
import com.zx.contact.utils.constant.Constant;
import com.zx.contact.view.LetterDecoration;
import com.zx.contact.view.SearchEditText;
import com.zx.contact.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity implements ContactAdapter.OnItemClickListener,
        SearchEditText.OnSearchClickListener, SearchEditText.OnClearClickListener, View.OnTouchListener {

    //访问通讯录权限请求码
    private final static int REQUEST_CODE_ASK_READ_CONTACT = 1;
    //通讯录变更消息
    private static final int CONTACT_CHANGE = 1;

    private static final int REQUEST_CODE_SCAN_QRCODE = 123;

    //通讯录操作的对象
    private ContactWrapper mWrapper;
    //查询出的通讯录列表
    private List<User> mUsers;

    //RecyclerView 布局管理器
    private LinearLayoutManager mManager;
    @Bind(R.id.edt_search)
    protected SearchEditText mEdtSearch;

    @Bind(R.id.sdb)
    protected SideBar mSideBar;

    @Bind(R.id.txt)
    protected TextView mTxt;

    @Bind(R.id.rcv)
    protected RecyclerView mRcv;

    @Bind(R.id.txt_title_left)
    protected TextView mTxtTitleLeft;

    @Bind(R.id.txt_title)
    protected TextView mTxtTitle;

    @Bind(R.id.txt_title_right)
    protected TextView mTxtTitleRight;

    @Bind(R.id.activity_main)
    protected RelativeLayout mLayoutMain;

    @Bind(R.id.img_load)
    protected ImageView mImgLoad;
    //通讯录列表适配器
    private ContactAdapter mAdapter;
    //通讯录列表分割线，分组
    protected RecyclerView.ItemDecoration mDecoration;

    //通讯录变更监听对象
    protected ContactContentObservers mObservers;
    //输入法管理
    private InputMethodManager mInputManager;

    private DBWrapper mDBWrapper;

    private PopupWindow mPopup;

    //处理通讯录的变更消息
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONTACT_CHANGE:
                    updateList();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initTitle();
        initPopup();
        request();
        initDialog();
    }


    /**
     * 添加联系人方式
     */
    private void initPopup() {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_icon, null);
        TextView txtQrCode = (TextView) view.findViewById(R.id.txt_take_photo);
        TextView txtAdd = (TextView) view.findViewById(R.id.txt_gallery);
        txtQrCode.setText(R.string.scan_qrcode);
        txtAdd.setText(R.string.add_by_hand);

        mPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopup.setTouchable(true);
        mPopup.setOutsideTouchable(true);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 初始化
     */
    private void init() {
        mWrapper = new ContactWrapper(this);
        mDBWrapper = DBWrapper.newInstance(this);
        mObservers = new ContactContentObservers(mHandler);
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //注册一个监听数据库的监听器
        getContentResolver().registerContentObserver(RawContacts.CONTENT_URI, true, mObservers);

    }

    /**
     * Android 6.0 以上动态请求权限
     */
    private void request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int locPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if (locPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_READ_CONTACT);
            } else {
                initView();
            }
        } else {
            initView();
        }
    }

    @OnClick({R.id.txt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_take_photo://扫码
                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE_SCAN_QRCODE);
                if (null != mPopup && mPopup.isShowing()) {
                    mPopup.dismiss();
                }
                break;
            case R.id.txt_gallery://直接添加
                Bundle bundle = new Bundle();
                bundle.putString("operate", Constant.ADD_USER);
                toActivity(AddAndEditActivity.class, bundle);
                if (null != mPopup && mPopup.isShowing()) {
                    mPopup.dismiss();
                }
                break;
            case R.id.txt_add://添加按钮
                if (null != mPopup && !mPopup.isShowing()) {
                    mPopup.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);
                }
                break;
        }

    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        mTxtTitle.setText(R.string.text_contact);
        mTxtTitleLeft.setVisibility(View.INVISIBLE);
        mTxtTitleRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化列表
     */
    private void initView() {
        mManager = new LinearLayoutManager(this);
        mRcv.setLayoutManager(mManager);
        mUsers = mDBWrapper.query();

        mDecoration = new LetterDecoration(MainActivity.this, mUsers);
        mRcv.addItemDecoration(mDecoration);
        mAdapter = new ContactAdapter(MainActivity.this, mUsers);
        mAdapter.setOnItemClickListener(MainActivity.this);
        mRcv.setAdapter(mAdapter);

        if (mUsers.isEmpty() || mUsers.size() == 0) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.load_animation);
            mImgLoad.setAnimation(anim);
            mImgLoad.setVisibility(View.VISIBLE);
            updateList();
        }
        initEvent();
    }

    /**
     * 初始化点击和触摸事件
     */
    private void initEvent() {
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mTxt.setText(s);
                mSideBar.setTextView(mTxt);
                skipToPos(s);
            }
        });
        mEdtSearch.setOnSearchClickListener(this);
        mEdtSearch.setOnClearClickListener(this);
        mRcv.setOnTouchListener(this);
        mLayoutMain.setOnTouchListener(this);
    }

    /**
     * skip to position
     *
     * @param s letter
     */
    private void skipToPos(String s) {
        List<User> users = mAdapter.getUsers();
        if (null != users && !users.isEmpty())
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLable().equals(s)) {
                    mManager.scrollToPosition(i);
                    mManager.setStackFromEnd(true);
                    break;
                }
            }
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        List<User> users = mAdapter.getUsers();
        LogWrapper.e("TAG", users.get(position).toString());
        bundle.putParcelable("user", users.get(position));
        toActivity(PhoneDetailActivity.class, bundle);
    }

    /**
     * 更新列表
     */
    private void updateList() {
        new Thread() {
            @Override
            public void run() {
                mUsers = mWrapper.query();
                Collections.sort(mUsers);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listChanged(mUsers);
                        mImgLoad.setAnimation(null);
                        mImgLoad.setVisibility(View.INVISIBLE);
                        mDBWrapper.insert(mUsers);
                    }
                });
            }
        }.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_CONTACT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    Toast.makeText(MainActivity.this, "权限请求失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /*搜索列表*/
    private List<User> mSearchUsers = new ArrayList<>();

    /**
     * 判断是否在搜索列表，如果是返回到全部列表
     */
    @Override
    public void onBackPressed() {
        if (mAdapter.getUsers() != mUsers) {
            listChanged(mUsers);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSearchClick(CharSequence ch) {
        mSearchUsers.clear();
        String str = ch.toString();
        for (User user : mUsers) {
            if (str.toUpperCase().equals(user.getLable()) || user.getPhone().contains(str) ||
                    user.getName().contains(str))
                mSearchUsers.add(user);
        }
        hideInput();
        listChanged(mSearchUsers);
    }

    @Override
    public void onClearClick() {
        hideInput();
        listChanged(mUsers);
    }

    /**
     * 列表改变
     */
    private void listChanged(List<User> users) {
        mRcv.removeItemDecoration(mDecoration);
        mDecoration = new LetterDecoration(MainActivity.this, users);
        mRcv.addItemDecoration(mDecoration);
        mAdapter.update(users);
    }

    /**
     * 当点击其他位置时，editText的焦点，并隐藏输入法
     *
     * @param v     触摸的view
     * @param event 触摸事件
     * @return false
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideInput();
        return false;
    }

    /**
     * 隐藏输入法
     */
    private void hideInput() {
        mEdtSearch.clearFocus();
        mLayoutMain.requestFocus();
        if (mInputManager.isActive()) {
            mInputManager.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
        }
    }


    private AlertDialog.Builder mBuilder;

    private void initDialog() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("扫描到新联系人");
        mBuilder.setMessage("是否添加?");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN_QRCODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        String result = bundle.getString(Constant.SCAN_RESULT_KEY, null);
                        if (null != result && !result.equals("")) {
                            LogWrapper.e("TAG", result);
                            final User user = PaserUtils.str2User(result);
                            if (null != user) {
                                mBuilder.setNegativeButton("否", null);
                                mBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mWrapper.insert(user);
                                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mBuilder.show();
                            } else {
                                Toast.makeText(this, "未扫描到用户信息", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                break;
            default:
                break;
        }
    }
}
package com.zx.news2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.news2.adapter.CommentAdapter;
import com.zx.news2.entity.Comment;
import com.zx.news2.entity.News;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.entity.User;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.CommonUtils;
import com.zx.news2.utils.SharedPreUtils;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.LogWrapper;
import com.zx.news2.view.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity {
    private static final String TAG = CommentActivity.class.getSimpleName();
    @Bind(R.id.txt_title2)
    protected TextView mTxtTitle;
    @Bind(R.id.txt_comnum)
    protected TextView mTxtComnum;
    @Bind(R.id.img_collect_news)
    protected ImageView mImgCollect;
    @Bind(R.id.xlst_comment)
    protected XListView mXLstComment;
    @Bind(R.id.edt_comment)
    protected EditText mEdtComment;

    protected HttpPresenter mPresenter;
    protected NewsDetail mNewsDetail;
    protected String token;

    protected CommentAdapter mAdapter;
    protected List<Comment> comments = new ArrayList<>();

    private Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            switch (mode) {
                case MODE.SEND_COMMENT:
                    News<User> news = gson.fromJson(jsonObject.toString(), new TypeToken<News<User>>() {
                    }.getType());
                    if (news.status == 0 && news.message.equals("OK")) {
                        User user = news.data;
                        if (user.result == 0) {
                            Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            mEdtComment.setText("");
//                            mode = MODE.LOAD_LAST;
                            loadComment();
                        } else
                            Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MODE.LOAD_MORE://load more
                    News<List<Comment>> newsCom = gson.fromJson(jsonObject.toString(), new TypeToken<News<List<Comment>>>() {
                    }.getType());
                    if (newsCom.status == 0 && newsCom.message.equals("OK")) {
                        comments.addAll(newsCom.data);
                        mAdapter.appendData(comments);
                        mAdapter.update();
                    }
                    break;
                case MODE.LOAD_LAST:
                    News<List<Comment>> comList = gson.fromJson(jsonObject.toString(), new TypeToken<News<List<Comment>>>() {
                    }.getType());
                    if (comList.status == 0 && comList.message.equals("OK")) {
                        comments = comList.data;
                        mAdapter.appendData(comments);
                        mAdapter.update();
                    }
                    break;
            }
        }
    };
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogWrapper.e(TAG, volleyError.toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        mNewsDetail = (NewsDetail) getBundle().getSerializable("newsDetail");
        initTitle();
        initView();
        initEvent();
    }

    private void initEvent() {
        mXLstComment.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadComment();
                mXLstComment.setRefreshTime(CommonUtils.getDateTime());
                mXLstComment.stopLoadMore();
                mXLstComment.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                loadNextComment();
                mXLstComment.stopLoadMore();
                mXLstComment.stopRefresh();
            }
        });
    }

    /**
     * initialize title
     */
    private void initTitle() {
        mTxtTitle.setText(R.string.text_title_comment);
        mTxtComnum.setVisibility(View.INVISIBLE);
        mImgCollect.setVisibility(View.INVISIBLE);
    }

    /**
     * initializable
     */
    private void initView() {
        token = SharedPreUtils.getToken(this);

        mPresenter = new HttpPresenter(this, null, mListener, mErrorListener);
        mAdapter = new CommentAdapter(this);
        mAdapter.addData(comments);
        mXLstComment.setAdapter(mAdapter);
        mXLstComment.setPullRefreshEnable(true);
        mXLstComment.setPullLoadEnable(true);
        loadComment();
    }

    int mode;

    /**
     * load new comment
     */
    private void loadComment() {
        mode = MODE.LOAD_LAST;
        mPresenter.requestHttpForGet(Constant.PATH_COMMENT_DETAIL + CommonUtils.map2String(getCommentData()));
    }

    /**
     * load last comment
     */
    private void loadNextComment() {
        mode = MODE.LOAD_MORE;
        Map<String, String> data = getCommentData();
        Comment comment = mAdapter.getItem(mXLstComment.getLastVisiblePosition() - 2);
        data.put("cid", "" + (null != comment ? comment.cid : 1));
        data.put("dir", "2");
        mPresenter.requestHttpForGet(Constant.PATH_COMMENT_DETAIL + CommonUtils.map2String(data));
    }

    @OnClick({R.id.img_back, R.id.img_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_send:
                if (mEdtComment.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.edt_comment_empty, Toast.LENGTH_SHORT).show();
                } else if (token.equals("")) {
                    Toast.makeText(this, R.string.not_login, Toast.LENGTH_SHORT).show();
                } else {
                    mode = MODE.SEND_COMMENT;
                    mPresenter.requestHttpForPost(Constant.PATH_COMMENT_SEND, getSendData());
                }
                break;
        }
    }

    /**
     * get request data
     *
     * @return request data
     */
    private Map<String, String> getCommentData() {
        Map<String, String> data = new HashMap<>();
        data.put("nid", mNewsDetail.nid);
        data.put("type", "" + mNewsDetail.type);
        data.put("stamp", "20161229");
        data.put("cid", "1");
        data.put("dir", "1");
        data.put("cnt", "20");
        return data;
    }


    /**
     * get request send data
     *
     * @return data
     */
    private Map<String, String> getSendData() {
        Map<String, String> data = new HashMap<>();
        data.put("nid", mNewsDetail.nid);
        data.put(Constant.MAP_KEY_TOKEN, token);
        data.put(Constant.MAP_KEY_IMEI, CommonUtils.getImei(this));
        data.put("ctx", mEdtComment.getText().toString());
        return data;
    }

    /**
     * comment mode
     *
     * @author HY
     */
    static class MODE {
        static final int SEND_COMMENT = 0;
        static final int LOAD_LAST = 1;
        static final int LOAD_MORE = 2;
    }
}
package com.zx.news2;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tandong.swichlayout.SwichLayoutInterFace;
import com.tandong.swichlayout.SwitchLayout;
import com.zx.news2.entity.News;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.db.DBWrapper;
import com.zx.news2.utils.LogWrapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("deprecation")
public class NewsDetailActivity extends BaseActivity implements SwichLayoutInterFace {

    private final static String TAG = NewsDetailActivity.class.getSimpleName();
    @Bind(R.id.web_news)
    protected WebView mWebNews;
    @Bind(R.id.txt_comnum)
    protected TextView mTxtComnum;
    @Bind(R.id.pgb_web_load)
    protected ProgressBar mBar;
    protected PopupWindow mPopup;
    protected NewsDetail newsDetail;
    protected HttpPresenter mPresenter;
    protected DBWrapper dbWrapper;


    private Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            News<Integer> news = gson.fromJson(jsonObject.toString(), new TypeToken<News<Integer>>() {
            }.getType());
            if (news.status == 0 && news.message.equals("OK")) {
                mTxtComnum.setText(news.data + " 跟帖");
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
        setContentView(R.layout.activity_news_detail);
        setEnterSwichLayout();
        ButterKnife.bind(this);
        newsDetail = (NewsDetail) getBundle().getSerializable("newsDetail");
        dbWrapper = DBWrapper.getInstance(NewsDetailActivity.this);
        mPresenter = new HttpPresenter(this, null, mListener, mErrorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestHttpForPost(Constant.PATH_COMNUM, getData());
        initPopup();
        initView();
    }

    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("nid", newsDetail.nid);
        return data;
    }

    /**
     * initialize view
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebNews.loadUrl(newsDetail.link);
        WebSettings settings = mWebNews.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebNews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                mBar.setVisibility(View.VISIBLE);
                return true;
            }
        });
        mWebNews.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mBar.setProgress(newProgress);
                if (newProgress >= 100)
                    mBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * initialize popup
     */
    void initPopup() {
        View popueView = LayoutInflater.from(this).inflate(R.layout.collect_menu, null);
        TextView txt = (TextView) popueView.findViewById(R.id.txt_collect);
        txt.setText(dbWrapper.newsExist(newsDetail.nid) ?
                getString(R.string.text_cancel_collect) : getString(R.string.text_add_collect));
        mPopup = new PopupWindow(popueView, ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopup.setTouchable(true);
        mPopup.setOutsideTouchable(true);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebNews.canGoBack()) {
                mWebNews.goBack();//back last page
                return true;
            } else {
                setExitSwichLayout();
//                finish();//finish
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.img_back, R.id.img_collect_news, R.id.txt_comnum})
    public void webClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (mWebNews.canGoBack()) {
                    mWebNews.goBack();//back last page
                } else {
                    setExitSwichLayout();
                }
                break;
            case R.id.img_collect_news:
                mPopup.showAsDropDown(view, 0, 20);
                break;
            case R.id.txt_comnum:
                Bundle bundle = new Bundle();
                bundle.putSerializable("newsDetail", newsDetail);
                toActivity(CommentActivity.class, bundle);
                break;
            case R.id.txt_collect:
                TextView txt = (TextView) view;
                if (dbWrapper.newsExist(newsDetail.nid)) {
                    txt.setText(R.string.text_add_collect);
                    dbWrapper.delete(Constant.DB_TABLE_FAVORITE, newsDetail);
                } else {
                    txt.setText(R.string.text_cancel_collect);
                    dbWrapper.instert(Constant.DB_TABLE_FAVORITE, newsDetail);
                }
                if (null != mPopup && mPopup.isShowing())
                    mPopup.dismiss();
                break;
        }
    }

    @Override
    public void setEnterSwichLayout() {
        SwitchLayout.ScaleBig(this, false, null);
    }

    @Override
    public void setExitSwichLayout() {
        SwitchLayout.ScaleSmall(this, true, null);
    }
}

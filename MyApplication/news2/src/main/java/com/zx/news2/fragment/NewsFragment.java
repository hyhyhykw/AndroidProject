package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.adapter.NewsAdapter;
import com.zx.news2.entity.News;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.CommonUtils;
import com.zx.news2.utils.LogWrapper;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.view.XListView;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/26.
 * news fragment
 *
 * @author HY
 */
public class NewsFragment extends Fragment {

    private static final String TAG = NewsFragment.class.getSimpleName();
    @Bind(R.id.rgp)
    protected RadioGroup mRgp;
    @Bind(R.id.rbtn1)
    protected RadioButton mRbtn1;
    @Bind(R.id.rbtn2)
    protected RadioButton mRbtn2;
    @Bind(R.id.rbtn3)
    protected RadioButton mRbtn3;
    @Bind(R.id.rbtn4)
    protected RadioButton mRbtn4;
    @Bind(R.id.rbtn5)
    protected RadioButton mRbtn5;
    @Bind(R.id.xlst_news_list)
    protected XListView mXLstNews;
    /*news list source*/
    protected List<NewsDetail> newsDetails;
    protected NewsAdapter mAdapter;
    protected HttpPresenter mPresenter;
    /*ui contorller*/
    protected NewsView mNewsView;

    /*radio buttons*/
    protected RadioButton[] mRbtns;

    public void setmNewsView(NewsView mNewsView) {
        this.mNewsView = mNewsView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    protected Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            TypeToken<News<List<NewsDetail>>> token = new TypeToken<News<List<NewsDetail>>>() {
            };
            News<List<NewsDetail>> news = gson.fromJson(jsonObject.toString(), token.getType());
            if (news.status == 0 && news.message.equals("OK")) {
                newsDetails = news.data;
                for (NewsDetail newsDetail : newsDetails) {
                    newsDetail.setStamp(newsDetail.stamp);
                }
                mAdapter.addData(newsDetails);
                mAdapter.update();
            }
            if (null != mNewsView)
                mNewsView.cancelDialog();
        }
    };

    protected Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogWrapper.e(TAG, volleyError.toString());
            if (null != mNewsView)
                mNewsView.cancelDialog();
        }
    };

    /**
     * initialize view and bind widget
     */
    private void initView() {
        mXLstNews.setPullLoadEnable(true);// alow pull up load
        mXLstNews.setPullRefreshEnable(true);
        mAdapter = new NewsAdapter(this.getContext());
        mXLstNews.setAdapter(mAdapter);
        mPresenter = new HttpPresenter(this.getContext(), mNewsView, mListener, mErrorListener);
    }


    /**
     * initialize connection
     *
     * @param checkedId checked id
     */
    private void initNet(int checkedId) {
        String path = "";
        switch (checkedId) {
            case R.id.rbtn1:
                path = Constant.PATH_NEWS_MILITARY;
                break;
            case R.id.rbtn2:
                path = Constant.PATH_NEWS_SOCIETY;
                break;
            case R.id.rbtn3:
                path = Constant.PATH_NEWS_ECONOMY;
                break;
            case R.id.rbtn4:
                path = Constant.PATH_NEWS_FINANCE;
                break;
            case R.id.rbtn5:
                path = Constant.PATH_NEWS_SCIENCE;
                break;
        }
        mPresenter.requestHttpForGet(path);
    }

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.bind(this, getView());
        initView();
        mRbtns = bindRbtn();
        for (RadioButton radio : mRbtns) {
            if (radio.isChecked()) {
                initNet(radio.getId());
                break;
            }
        }
        initEvent();
    }

    /**
     * initialize event
     */
    private void initEvent() {
        mRgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                initNet(checkedId);
                mAdapter.addData(newsDetails);
                mAdapter.update();
            }
        });
        //item
        mXLstNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsDetail newsDetail = newsDetails.get(position - 1);
                mNewsView.skip(newsDetail);
            }
        });
        //set pull up and pull down listener
        mXLstNews.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (null != newsDetails)
                    mAdapter.addNewData(newsDetails);
                mXLstNews.stopRefresh();
                mXLstNews.stopLoadMore();
                mXLstNews.setRefreshTime(CommonUtils.getDateTime());
            }

            @Override
            public void onLoadMore() {
                if (null != newsDetails)
                    mAdapter.appendData(newsDetails);
                mXLstNews.stopLoadMore();
                mXLstNews.stopRefresh();
            }
        });
    }


    /**
     * all radio buttons
     *
     * @return radio buttons
     */
    private RadioButton[] bindRbtn() {
        return new RadioButton[]{mRbtn1, mRbtn2, mRbtn3, mRbtn4, mRbtn5};
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

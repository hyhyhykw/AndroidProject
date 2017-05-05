package com.zx.news2.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.adapter.NewsAdapter;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.utils.constant.Constant;
import com.zx.news2.utils.db.DBWrapper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/27.
 * favorite
 *
 * @author HY
 */
public class FavoriteFragment extends Fragment {
    /*ui controller*/
    protected NewsView mNewsView;

    /*favorite news list*/
    @Bind(R.id.lst_news_favorite)
    protected ListView mLstFavorite;

    /*news adapter*/
    protected NewsAdapter mAdapter;
    /*adapter source*/
    protected List<NewsDetail> newsDetails;
    protected AlertDialog.Builder mBuilder;
    /*database operate*/
    protected DBWrapper dbWrapper;

    public void setmNewsView(NewsView mNewsView) {
        this.mNewsView = mNewsView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
        initEvent();
    }

    /**
     * initialize view
     */
    private void initView() {
        mAdapter = new NewsAdapter(getContext());
        dbWrapper = DBWrapper.getInstance(getContext());
        initData();
        mAdapter.addData(newsDetails);
        mLstFavorite.setAdapter(mAdapter);
        mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setIcon(R.mipmap.ic_launcher);
        mBuilder.setMessage(R.string.is_or_not_delete);
    }

    /**
     * read database file
     */
    private void initData() {
        newsDetails = dbWrapper.selete(Constant.DB_TABLE_FAVORITE);
    }

    /**
     * news list item click event
     */
    private void initEvent() {
        mLstFavorite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final NewsDetail newsDetail = newsDetails.get(position);
                mBuilder.setNegativeButton("否", null);
                mBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbWrapper.delete(Constant.DB_TABLE_FAVORITE, newsDetail);
                        initData();
                        mAdapter.addData(newsDetails);
                        mAdapter.update();
                    }
                });
                mBuilder.show();
                return true;
            }
        });
        mLstFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mNewsView.skip(newsDetails.get(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

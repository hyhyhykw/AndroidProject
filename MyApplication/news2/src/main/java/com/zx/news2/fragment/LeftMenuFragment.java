package com.zx.news2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zx.news2.utils.NewsView;
import com.zx.news2.R;
import com.zx.news2.adapter.LeftMenuAdapter;
import com.zx.news2.entity.LeftMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/26.
 * left menu
 */

public class LeftMenuFragment extends Fragment {

    @Bind(R.id.lst_left_menu)
    protected ListView mLstMenu;
    protected LeftMenuAdapter mAdapter;

    protected List<LeftMenu> leftMenus;
    protected NewsView mNewsView;

    public void setNewsView(NewsView newsView) {
        mNewsView = newsView;
    }

    protected int[] icons = {R.mipmap.biz_navigation_tab_news,
            R.mipmap.biz_navigation_tab_read,
            R.mipmap.biz_navigation_tab_local_news,
            R.mipmap.biz_navigation_tab_ties,
            R.mipmap.biz_navigation_tab_pics};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        mAdapter = new LeftMenuAdapter(getContext());
        initData();
        mAdapter.addData(leftMenus);
        mAdapter.update();
        mLstMenu.setAdapter(mAdapter);
        initEvent();
    }

    private void initEvent() {
        mLstMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < leftMenus.size(); i++) {
                    leftMenus.get(i).isChecked = (i == position);
                }
                mNewsView.showView(position);
                mAdapter.addData(leftMenus);
                mAdapter.update();
            }
        });
    }


    void initData() {
        leftMenus = new ArrayList<>();
        String[] titleCNs = this.getResources().getStringArray(R.array.left_menu_texts_cn);
        String[] titleENs = this.getResources().getStringArray(R.array.left_menu_texts_en);
        for (int i = 0; i < icons.length; i++) {
            boolean isChecked = (i == 0);
            LeftMenu leftMenu = new LeftMenu(icons[i], titleCNs[i], titleENs[i], isChecked);
            leftMenus.add(leftMenu);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

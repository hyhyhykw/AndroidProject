package com.hy.patrol_leader.main.home;

import android.widget.ListView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseFragment;
import com.hy.patrol_leader.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created time : 2017/4/27 11:27.
 * 首页
 *
 * @author HY
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.lst_main)
    protected ListView mLstMain;
    @BindArray(R.array.item_main_title)
    protected String[] mTitles;

    private List<HomeItem> mDatas;
    private HomeListAdapter mAdapter;

    protected int[] mResIds = {
            R.drawable.ic_check,
            R.drawable.ic_wait,
            R.drawable.ic_approval
    };

    @Override
    protected void initView() {
        initData();
        mAdapter =new HomeListAdapter();
        mAdapter.reset(mDatas);

        mLstMain.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HomeItem item = new HomeItem();
            item.setIcon(mResIds[i]);
            item.setTitle(mTitles[i]);
            item.setCount(String.valueOf(i + 1));
            mDatas.add(item);
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

}

package com.hy.patrol_leader.main.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseListAdapter;
import com.hy.patrol_leader.model.HomeItem;

import butterknife.BindView;


/**
 * Created time : 2017/4/27 13:36.
 *
 * @author HY
 */

public class HomeListAdapter extends
        BaseListAdapter<HomeItem, HomeListAdapter.HomeViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_lst_home;
    }

    @Override
    protected HomeViewHolder getItemViewHolder(View view) {
        return new HomeViewHolder(view);
    }

    class HomeViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.iv_item_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_home_title)
        TextView mTvTitle;
        @BindView(R.id.tv_home_count)
        TextView mTvCount;

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            HomeItem item = getItem(position);
            mIvIcon.setImageResource(item.getIcon());
            mTvCount.setText(item.getCount() + "条");
            mTvTitle.setText(item.getTitle());
        }
    }
}

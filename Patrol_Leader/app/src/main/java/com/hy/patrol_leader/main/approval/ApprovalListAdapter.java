package com.hy.patrol_leader.main.approval;

import android.view.View;
import android.widget.TextView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseListAdapter;

import butterknife.BindView;

/**
 * Created time : 2017/4/27 14:25.
 *
 * @author HY
 */
public class ApprovalListAdapter extends
        BaseListAdapter<String, ApprovalListAdapter.ApprovalViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_lst_approval;
    }

    @Override
    protected ApprovalViewHolder getItemViewHolder(View view) {
        return new ApprovalViewHolder(view);
    }

    class ApprovalViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.tv_approval_title)
        TextView mTvTitle;

        public ApprovalViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            mTvTitle.setText(getItem(position));
        }
    }
}

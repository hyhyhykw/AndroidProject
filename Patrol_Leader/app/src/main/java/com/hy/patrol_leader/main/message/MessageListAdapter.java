package com.hy.patrol_leader.main.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.patrol_leader.R;
import com.hy.patrol_leader.base.BaseListAdapter;
import com.hy.patrol_leader.model.MessageInfo;

import butterknife.BindView;

/**
 * Created time : 2017/4/28 15:01.
 *
 * @author HY
 */

public class MessageListAdapter extends BaseListAdapter<MessageInfo, MessageListAdapter.MessageViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_lst_msg;
    }

    @Override
    protected MessageViewHolder getItemViewHolder(View view) {
        return new MessageViewHolder(view);
    }

    class MessageViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.tv_msg_title)
        TextView mTvTitle;
        @BindView(R.id.iv_status)
        ImageView mIvStatus;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_explain)
        TextView mTvExplain;
        @BindView(R.id.tv_charge)
        TextView mTvCharge;
        @BindView(R.id.tv_stamp)
        TextView mTvStamp;


        public MessageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            MessageInfo info = getItem(position);

            mTvTitle.setText(info.getTitle());
            int status = info.getStatus();

            mIvStatus.setImageResource(status == 0 ? R.drawable.ic_dot_gray
                    : (status == 1 ? R.drawable.ic_dot_orange : R.drawable.ic_dot_green));

            mTvStatus.setText(status == 0 ? "已查看"
                    : (status == 1 ? "待审批" : "新消息"));

            mTvExplain.setText("说明：" + info.getExplain());

            mTvCharge.setText("负责人：" + info.getCharge());

            mTvStamp.setText(info.getStamp());
        }
    }
}

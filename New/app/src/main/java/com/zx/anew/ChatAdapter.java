package com.zx.anew;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created time : 2017/3/8 19:31.
 *
 * @author HY
 */

public class ChatAdapter extends RecyclerView.Adapter {

    private List<MessageInfo> mInfos = new ArrayList<>();


//    public void setDatas(List<MessageInfo> infos) {
//        mInfos = infos;
//        notifyDataSetChanged();
//    }

    public void addMseeage(MessageInfo msg) {
        mInfos.add(msg);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == MsgType.MSG_A.ordinal()) {
            return new AViewHolder(inflater.inflate(R.layout.item_msg_send, parent, false));
        }
        return new BViewHolder(inflater.inflate(R.layout.item_msg_receive, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AViewHolder) {
            AViewHolder aViewHolder = (AViewHolder) holder;
            aViewHolder.mTxtMsgA.setText(mInfos.get(position).getMsg());
        } else if (holder instanceof BViewHolder) {
            BViewHolder aViewHolder = (BViewHolder) holder;
            aViewHolder.mTxtMsgB.setText(mInfos.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return null != mInfos ? mInfos.size() : 0;
    }


    @Override
    public int getItemViewType(int position) {
        return mInfos.get(position).getMsgType().ordinal();
    }

    class AViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_msg_a)
        TextView mTxtMsgA;

        public AViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_msg_b)
        TextView mTxtMsgB;

        public BViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

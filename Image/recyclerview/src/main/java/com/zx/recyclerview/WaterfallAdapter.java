package com.zx.recyclerview;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.zx.recyclerview.WaterfallAdapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created Time: 2017/3/1 20:27.
 *
 * @author HY
 */

public class WaterfallAdapter extends Adapter<MyViewHolder> {

    //    private int[] icons;
    private List<Item> mItems;
    private List<Integer> heights = new ArrayList<>();

//    public WaterfallAdapter(int[] icons) {
//        this.icons = icons;
//        initHeights();
//    }

    public WaterfallAdapter(List<Item> items) {
        mItems = items;
        initHeights();
    }

    private void initHeights() {
        for (int i = 0; i < mItems.size(); i++) {
            heights.add(100 + (int) (Math.random() * 300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        LayoutParams params = holder.mView.getLayoutParams();
        params.width = params.height = heights.get(position);
        holder.mView.setLayoutParams(params);
        holder.mView.setImageResource(mItems.get(position).getResId());
        holder.mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mOnItemClickListener)
//                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        holder.mView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (null != mOnItemLongClickListener)
//                    mOnItemLongClickListener.onItemLongClick(holder.getAdapterPosition());
                return true;
            }
        });

    }

    public void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        heights.remove(position);
    }

    public void add(int position, Item item) {
        mItems.add(position, item);
        heights.add(100 + (int) (Math.random() * 300));
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<Item> getDataList() {
        return mItems;
    }

    class MyViewHolder extends ViewHolder {
        ImageView mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = (ImageView) itemView.findViewById(R.id.img);
        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

}

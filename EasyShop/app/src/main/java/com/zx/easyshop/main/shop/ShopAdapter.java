package com.zx.easyshop.main.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zx.easyshop.R;
import com.zx.easyshop.components.AvatarLoadOptions;
import com.zx.easyshop.model.GoodsInfo;
import com.zx.easyshop.network.EasyShopApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/16 11:44.
 *
 * @author HY
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {


    private List<GoodsInfo> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ShopAdapter(List<GoodsInfo> datas) {
        mDatas = datas;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, final int position) {
        final GoodsInfo goodsInfo = mDatas.get(position);
        //商品名称
        holder.mTvItemName.setText(goodsInfo.getName());
        //商品价格
        String price = mContext.getString(R.string.goods_money, goodsInfo.getPrice());
        holder.mTvItemPrice.setText(price);
        //商品图片
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + goodsInfo.getPage(),
                holder.mIvItemRecycler, AvatarLoadOptions.build_item());

        //如果已经设置了监听器，就设置图片的点击事件
        if (null != mOnItemClickListener) {
            holder.mIvItemRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(goodsInfo);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    //添加数据
    public void addData(List<GoodsInfo> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    //清空数据
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    //自定义ViewHolder
    class ShopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_recycler)
        ImageView mIvItemRecycler;
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_item_price)
        TextView mTvItemPrice;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //子条目点击事件
    public interface OnItemClickListener {
        void onItemClick(GoodsInfo goodsInfo);
    }

}

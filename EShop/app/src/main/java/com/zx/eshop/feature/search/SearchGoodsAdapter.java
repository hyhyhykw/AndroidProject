package com.zx.eshop.feature.search;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseListAdapter;
import com.zx.eshop.feature.search.SearchGoodsAdapter.ViewHolder;
import com.zx.eshop.network.entity.SimpleGoods;

import butterknife.BindView;

/**
 * Created Time: 2017/2/28 20:46.
 *
 * @author HY
 */

public class SearchGoodsAdapter extends BaseListAdapter<SimpleGoods, ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_search_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.image_goods)
        ImageView mImageGoods;
        @BindView(R.id.text_goods_name)
        TextView mTextGoodsName;
        @BindView(R.id.text_goods_price)
        TextView mTextGoodsPrice;
        @BindView(R.id.text_market_price)
        TextView mTextMarketPrice;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            SimpleGoods simpleGoods = getItem(position);
            mTextGoodsName.setText(simpleGoods.getName());
            mTextGoodsPrice.setText(simpleGoods.getShopPrice());
            //加一条线的字符串
            SpannableString marketPrice=new SpannableString(simpleGoods.getMarketPrice());
            marketPrice.setSpan(new StrikethroughSpan(),0,marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTextMarketPrice.setText(marketPrice);
            Picasso.with(getContext()).
                    load(simpleGoods.getImg().getLarge())
                    .into(mImageGoods);
        }
    }
}

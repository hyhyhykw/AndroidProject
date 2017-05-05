package com.feicuiedu.eshop_0221.feature.search;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.eshop_0221.R;
import com.feicuiedu.eshop_0221.base.BaseListAdapter;
import com.feicuiedu.eshop_0221.network.entity.Picture;
import com.feicuiedu.eshop_0221.network.entity.SimpleGoods;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by gqq on 2017/3/2.
 */
// 搜索商品列表的适配器
public class SearchGoodsAdapter extends BaseListAdapter<SimpleGoods,SearchGoodsAdapter.ViewHolder>{

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_search_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder{

        @BindView(R.id.image_goods)
        ImageView mIvGoods;
        @BindView(R.id.text_goods_name)
        TextView mTvName;
        @BindView(R.id.text_goods_price)
        TextView mTvPrice;
        @BindView(R.id.text_market_price)
        TextView mTvMarketPrice;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            SimpleGoods simpleGoods = getItem(position);
            mTvName.setText(simpleGoods.getName());
            mTvPrice.setText(simpleGoods.getShopPrice());

            // 设置商场价格：在原有的字符串上面画了一个横线
            String marketPrice = simpleGoods.getMarketPrice();

            // 对文本的处理类：形成一个组合的文本
            SpannableString spannableString = new SpannableString(marketPrice);
            // StrikethroughSpan设置删除的线
            spannableString.setSpan(new StrikethroughSpan(),0,marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 将处理好的文本设置给商场价格的TextView
            mTvMarketPrice.setText(spannableString);

            // 商品图片的加载
            Picture picture = simpleGoods.getImg();
            Picasso.with(getContext()).load(picture.getLarge()).into(mIvGoods);
        }
    }
}

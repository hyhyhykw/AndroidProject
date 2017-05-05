package com.zx.eshop.feature.home;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseListAdapter;
import com.zx.eshop.base.wrapper.ToastWrapper;
import com.zx.eshop.feature.goods.GoodsActivity;
import com.zx.eshop.feature.home.HomeGoodsAdapter.ViewHolder;
import com.zx.eshop.network.entity.CategoryHome;
import com.zx.eshop.network.entity.Picture;
import com.zx.eshop.network.entity.SimpleGoods;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created Time: 2017/2/28 10:56.
 *
 * @author HY
 */

@SuppressWarnings("WeakerAccess")
public class HomeGoodsAdapter extends BaseListAdapter<CategoryHome, ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_home_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.text_category)
        TextView mTextCategory;
        @BindViews({
                R.id.image_goods_01,
                R.id.image_goods_02,
                R.id.image_goods_03,
                R.id.image_goods_04})
        ImageView[] mImageGoods;
        private CategoryHome mCategoryHome;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            mCategoryHome = getItem(position);
            mTextCategory.setText(mCategoryHome.getName());
            final List<SimpleGoods> hotGoodsList = mCategoryHome.getHotGoodsList();
            for (int i = 0; i < mImageGoods.length; i++) {
                //取出商品list里的商品图片
                Picture picture = hotGoodsList.get(i).getImg();

                //Picasso加载图片
                Picasso.with(getContext())
                        .load(picture.getLarge())
                        .into(mImageGoods[i]);
                final int index = i;
                mImageGoods[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleGoods simpleGoods = hotGoodsList.get(index);
                        // 点击跳转
                        Intent intent = GoodsActivity.getStartIntent(getContext(), simpleGoods.getId());
                        getContext().startActivity(intent);
                    }
                });

            }
        }

        @OnClick(R.id.text_category)
        public void onClick() {
            ToastWrapper.show(mCategoryHome.getName());
        }
    }
}

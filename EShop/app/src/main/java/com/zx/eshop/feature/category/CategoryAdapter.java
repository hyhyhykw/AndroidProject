package com.zx.eshop.feature.category;

import android.view.View;
import android.widget.TextView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseListAdapter;
import com.zx.eshop.network.entity.CategoryPrimary;

import butterknife.BindView;

/**
 * Created Time: 2017/2/23 19:52.
 *
 * @author HY
 */
@SuppressWarnings("WeakerAccess")
public class CategoryAdapter extends BaseListAdapter<CategoryPrimary, CategoryAdapter.ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_primary_category;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.text_category)
        TextView mTextCategory;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            mTextCategory.setText(getItem(position).getName());
        }
    }
}

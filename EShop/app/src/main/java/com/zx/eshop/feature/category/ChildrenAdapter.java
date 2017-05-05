package com.zx.eshop.feature.category;

import android.view.View;
import android.widget.TextView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseListAdapter;
import com.zx.eshop.network.entity.CategoryBase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/23 19:53.
 *
 * @author HY
 */
@SuppressWarnings("WeakerAccess")
public class ChildrenAdapter extends BaseListAdapter<CategoryBase, ChildrenAdapter.ViewHolder> {

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_children_category;
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        protected void bind(int position) {
            mTextCategory.setText(getItem(position).getName());
        }
    }
}

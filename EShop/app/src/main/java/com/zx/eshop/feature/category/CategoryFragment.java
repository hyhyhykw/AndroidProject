package com.zx.eshop.feature.category;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.eshop.R;
import com.zx.eshop.base.BaseFragment;
import com.zx.eshop.base.wrapper.ToolbarWrapper;
import com.zx.eshop.feature.search.SearchGoodsActivity;
import com.zx.eshop.network.api.ApiCategory;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.CategoryPrimary;
import com.zx.eshop.network.entity.CategoryRsp;
import com.zx.eshop.network.entity.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;


/**
 * Created Time: 2017/2/23 17:21.
 *
 * @author HY
 *         分类页面
 */
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar_title)
    protected TextView mTitle;//标题
    @BindView(R.id.list_category)
    protected ListView mListCategory;//一级分组
    @BindView(R.id.list_children)
    protected ListView mListChildren;//二级分组

    private CategoryAdapter mPrimaryAdapter;//一级分组适配器
    private ChildrenAdapter mChildrenAdapter;//二级分组适配器
    private List<CategoryPrimary> mData;

    //获取当前类的实例
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected void initView() {
        //ToolBar的相关操作
        new ToolbarWrapper(this)
                .setShowBack(true)
                .setShowTitle(false)
                .setCustomTitle(R.string.category_title);
        //一级分组适配器初始化
        mPrimaryAdapter = new CategoryAdapter();
        mListCategory.setAdapter(mPrimaryAdapter);
        //二级分组适配器初始化
        mChildrenAdapter = new ChildrenAdapter();
        mListChildren.setAdapter(mChildrenAdapter);

        if (null == mData) {
            enqueue(new ApiCategory());
        } else updateCategory();
    }

    @OnItemClick(R.id.list_category)
    public void onItemClick(int position) {
        chooseCategory(position);
    }

    @OnItemClick(R.id.list_children)
    public void onChildItemClick(int position) {
//        ToastWrapper.show(mChildrenAdapter.getItem(position).getName());
        navigateToSearch(mChildrenAdapter.getItem(position).getId());
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {

        if (!ApiPath.CATEGORY.equals(path))
            throw new UnsupportedOperationException(path);

        if (isSuccess) {
            //添加一级分组数据
            CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
            mData = categoryRsp.getData();
            updateCategory();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            int position = mListCategory.getCheckedItemPosition();
            int id = mPrimaryAdapter.getItem(position).getId();
            navigateToSearch(id);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新一级菜单数据
     */
    private void updateCategory() {
        mPrimaryAdapter.reset(mData);
        chooseCategory(0);
    }

    /**
     * 更新二级菜单数据
     *
     * @param position 位置
     */
    private void chooseCategory(int position) {
        mListCategory.setItemChecked(position, true);
        mChildrenAdapter.reset(mPrimaryAdapter.getDatas().get(position).getChildren());
    }

    //跳转到搜索页面
    private void navigateToSearch(int categoryId) {
        Filter filter = new Filter();
        filter.setCategoryId(categoryId);
        Intent intent = SearchGoodsActivity.getStartIntent(getContext(), filter);
        startActivity(intent);
    }
}

package com.feicuiedu.eshop_0221.feature.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop_0221.R;
import com.feicuiedu.eshop_0221.base.BaseFragment;
import com.feicuiedu.eshop_0221.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop_0221.feature.search.SearchGoodsActivity;
import com.feicuiedu.eshop_0221.network.EShopClient;
import com.feicuiedu.eshop_0221.network.api.ApiCategory;
import com.feicuiedu.eshop_0221.network.core.ApiPath;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.core.UICallback;
import com.feicuiedu.eshop_0221.network.entity.CategoryPrimary;
import com.feicuiedu.eshop_0221.network.entity.CategoryRsp;
import com.feicuiedu.eshop_0221.network.entity.Filter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gqq on 2017/2/23.
 */

public class CategoryFragment extends BaseFragment {


    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_category)
    ListView mListCategory;
    @BindView(R.id.list_children)
    ListView mListChildren;

    private List<CategoryPrimary> mData;
    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    // 请求拿到数据处理
    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {
        if (!ApiPath.CATEGORY.equals(path)) {
            throw new UnsupportedOperationException(path);
        }
        if (isSucces) {
            CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
            mData = categoryRsp.getData();
            // 数据有了之后，数据给一级分类，默认选择第一条，二级分类才能展示
            updateCategory();
        }
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {

        initToolbar();

        // ListView的展示
        mCategoryAdapter = new CategoryAdapter();
        mListCategory.setAdapter(mCategoryAdapter);

        mChildrenAdapter = new ChildrenAdapter();
        mListChildren.setAdapter(mChildrenAdapter);

        // 拿到数据
        if (mData != null) {
            // 可以直接更新UI
            updateCategory();
        } else {
            // 去进行网络请求拿到数据
            enqueue(new ApiCategory());

//            UICallback uiCallback = new UICallback() {
//                @Override
//                public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
//                    if (isSucces) {
//                        CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
//                        mData = categoryRsp.getData();
//                        // 数据有了之后，数据给一级分类，默认选择第一条，二级分类才能展示
//                        updateCategory();
//                    }
//                }
//            };
//            EShopClient.getInstance().enqueue(ApiPath.CATEGORY,null,CategoryRsp.class,uiCallback);

        }
    }

    // 更新分类数据
    private void updateCategory() {
        mCategoryAdapter.reset(mData);
        // 切换展示二级分类
        chooseCategory(0);
    }

    // 用于根据一级分类的选项展示二级分类的内容
    private void chooseCategory(int position) {
        mListCategory.setItemChecked(position, true);
        mChildrenAdapter.reset(mCategoryAdapter.getItem(position).getChildren());
    }

    // 点击一级分类：展示相应二级分类
    @OnItemClick(R.id.list_category)
    public void onItemClick(int postion) {
        chooseCategory(postion);
    }

    // 点击二级分类
    @OnItemClick(R.id.list_children)
    public void onChildrenClick(int position) {
        // 跳转到搜索页面
        int categoryId = mChildrenAdapter.getItem(position).getId();
        navigateToSearch(categoryId);
    }


    private void initToolbar() {
        // 利用包装好的Toolbar
        new ToolbarWrapper(this).setCustomTitle(R.string.category_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        // 返回箭头
        if (itemId == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        if (itemId == R.id.menu_search) {
            int position = mListCategory.getCheckedItemPosition();
            int id = mCategoryAdapter.getItem(position).getId();
            navigateToSearch(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToSearch(int categoryId) {
        // 根据id构建Filter，然后跳转页面
        Filter filter = new Filter();
        filter.setCategoryId(categoryId);
        Intent intent = SearchGoodsActivity.getStartIntent(getContext(), filter);
        getActivity().startActivity(intent);
    }

}

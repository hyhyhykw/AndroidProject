package com.zx.easyshop.main.me.goodsupload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zx.easyshop.R;
import com.zx.easyshop.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/17 16:11.
 *
 * @author HY
 *         商品上传图片选择适配器
 */
@SuppressWarnings("WeakerAccess")
public class GoodsUploadAdapter extends RecyclerView.Adapter {

    private List<ImageItem> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    public GoodsUploadAdapter(Context context, List<ImageItem> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    //############################     逻辑:start         #############################
    //模式：1=有图，2=无图
    public static final int MODE_NORMAL = 1;
    public static final int MODE_MULT_SELECT = 2;
    //当前模式
    private int mode = MODE_NORMAL;

    /**
     * 改变模式
     *
     * @param mode {@link #MODE_NORMAL} {@link #MODE_MULT_SELECT}
     */
    public void changeMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    //获取当前模式
    public int getMode() {
        return mode;
    }

    //item类型
    public enum ITEM_TYPE {
        ITEM_NORMAL, ITEM_ADD
    }

    //###############################   逻辑:end   #######################################

    //###############################   外部调用的方法    #######################################
    //获取item
    public List<ImageItem> getItems() {
        return mItems;
    }

    //添加item
    public void add(ImageItem imageItem) {
        mItems.add(imageItem);
    }

    //刷新数据
    public void update() {
        notifyDataSetChanged();
    }

    //获取数量
    public int getSize() {
        return mItems.size();
    }

    //#############################   适配器实现   ##################################
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_NORMAL.ordinal()) {
            return new ItemSelectViewHolder(mInflater.inflate(R.layout.layout_item_recyclerview, parent, false));
        } else
            return new ItemAddViewHolder(mInflater.inflate(R.layout.layout_item_recyclerviewlast, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断ViewHolder的实例类型
        if (holder instanceof ItemSelectViewHolder) {
            //获取数据
            final ImageItem imageItem = mItems.get(position);
            //强转vh
            ItemSelectViewHolder selectViewHolder = (ItemSelectViewHolder) holder;
            //vh获取数据
            selectViewHolder.photo = imageItem;
            //判断模式 （正常、可删除）
            CheckBox checkBox = selectViewHolder.mCbCheckPhoto;
            if (mode == MODE_MULT_SELECT) {//可删除
                //编辑框可见
                checkBox.setVisibility(View.VISIBLE);
                //设置ChexkBox选择 改变事件
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //imageitem设置选中
                        imageItem.setIsCheck(isChecked);
                    }
                });
                //编辑框选中状态
                checkBox.setChecked(imageItem.isCheck());
            } else if (mode == MODE_NORMAL) {
                //正常
                checkBox.setVisibility(View.GONE);
            }
            //图片相关
            ImageView imageView = selectViewHolder.mIvPhoto;
            //图片点击
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //图片点击跳转图片详情页
                    if (null != mOnItemClickListener)
                        mOnItemClickListener.onClick(imageItem);
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //改变当前模式
                    mode = MODE_MULT_SELECT;
                    //刷新
                    notifyDataSetChanged();
                    // 图片长按事件监听
                    if (null != mOnItemClickListener)
                        mOnItemClickListener.onLongClick();
                    return true;
                }
            });
            imageView.setImageBitmap(imageItem.getBitmap());
        } else if (holder instanceof ItemAddViewHolder) {
            // 强转
            ItemAddViewHolder itemAddViewHolder = (ItemAddViewHolder) holder;
            itemAddViewHolder.mIbRecycleAdd.setVisibility(position == 8 ? View.GONE : View.VISIBLE);
            //点击事件
            itemAddViewHolder.mIbRecycleAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 添加按钮
                    if (null != mOnItemClickListener)
                        mOnItemClickListener.onAddClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //最多八张图
        return Math.min(mItems.size() + 1, 8);
    }

    //获取视图类型
    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) return ITEM_TYPE.ITEM_ADD.ordinal();
        else return ITEM_TYPE.ITEM_NORMAL.ordinal();
    }

    //######################################         ViewHolder           ###################################
    //普通的ViewHolder
    static class ItemAddViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ib_recycle_add)
        ImageButton mIbRecycleAdd;//最多八张图

        public ItemAddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //有图时的ViewHolder
    static class ItemSelectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.cb_check_photo)
        CheckBox mCbCheckPhoto;

        ImageItem photo;

        public ItemSelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //图片点击事件
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //#####################################         item点击的回调接口         ####################
    //图片点击事件
    public interface OnItemClickListener {

        //点击
        void onClick(ImageItem imageItem);

        //长按
        void onLongClick();

        //添加图片
        void onAddClick();
    }


}

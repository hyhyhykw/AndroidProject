package com.zx.contact.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.contact.R;
import com.zx.contact.entity.User;
import com.zx.contact.utils.CrapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/1/20 20:29.
 *
 * @author HY
 */

class MyViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.img_icon)
    ImageView mImgIcon;
    @Bind(R.id.txt_name)
    TextView mTxtName;

    @Bind(R.id.lyt_contact)
    View mContentView;

    public MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

public class ContactAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private LayoutInflater mInflater;
    private Context mContext;

    public List<User> getUsers() {
        return mUsers;
    }

    private List<User> mUsers;

    public ContactAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsers = users;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_rcv_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        User user = mUsers.get(position);
        Bitmap photo = user.getPhoto();
        Bitmap headIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_contact_head_icon);
        if (null != photo) {
            headIcon = CrapUtils.toRoundBitmap(photo);
        }
        holder.mImgIcon.setImageBitmap(headIcon);
        holder.mTxtName.setText(user.getName().equals("") ? user.getPhone() : user.getName());
        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener)
                    mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    public void update(List<User> users) {
        this.mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null != mUsers ? mUsers.size() : 0;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

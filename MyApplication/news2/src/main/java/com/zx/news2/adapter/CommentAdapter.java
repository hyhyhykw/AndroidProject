package com.zx.news2.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zx.news2.R;
import com.zx.news2.entity.Comment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/29.
 * comment adapter
 *
 * @author HY
 */
public class CommentAdapter extends MyBaseAdapter<Comment> {

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_xlst_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = getItem(position);
        holder.mTxtCommentTime.setText(comment.stamp);
        holder.mTxtCommentContent.setText(comment.content);
        holder.mTxtUserId.setText(comment.uid);

        RequestCreator request = Picasso.with(mContext).load(comment.portrait);
        request.placeholder(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
        request.error(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
        request.into(holder.mImgUserIcon);
        return convertView;
    }

    /**
     * view holder
     *
     * @author HY
     */
    class ViewHolder {
        @Bind(R.id.img_user_icon)
        ImageView mImgUserIcon;
        @Bind(R.id.txt_user_id)
        TextView mTxtUserId;
        @Bind(R.id.txt_comment_time)
        TextView mTxtCommentTime;
        @Bind(R.id.txt_comment_content)
        TextView mTxtCommentContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

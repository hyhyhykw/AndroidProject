package com.zx.news2.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.zx.news2.R;
import com.zx.news2.entity.NewsDetail;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/26.
 * news list adapter
 *
 * @author HY
 */
public class NewsAdapter extends MyBaseAdapter<NewsDetail> {

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_lst_news_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsDetail newsDetail = getItem(position);

        Picasso picasso = Picasso.with(mContext);
        RequestCreator request = picasso.load(newsDetail.icon);
        request.placeholder(R.mipmap.defaultpic);
        request.error(R.mipmap.sorry);
        request.into(holder.mImgNewsIcon);

        holder.mTxtNewsTitle.setText(newsDetail.title);
        holder.mTxtNewsContent.setText(newsDetail.summary.trim());
        holder.mTxtNewsTime.setText(newsDetail.stamp);
        return convertView;
    }

    /**
     * @author HY
     */
    class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @Bind(R.id.img_news_icon)
        ImageView mImgNewsIcon;//show news icon
        @Bind(R.id.txt_news_title)
        TextView mTxtNewsTitle;//news title
        @Bind(R.id.txt_news_content)
        TextView mTxtNewsContent;//news content
        @Bind(R.id.txt_news_time)
        TextView mTxtNewsTime;//news time
    }
}

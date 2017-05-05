package com.zx.news2.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zx.news2.R;
import com.zx.news2.entity.LoginLog;
import com.zx.news2.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/28.
 * login log adapter
 *
 * @author HY
 */
public class LoginLogAdapter extends MyBaseAdapter<LoginLog> {


    public LoginLogAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_lst_login_log, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LoginLog loginLog = getItem(position);
        holder.mTxtLoginTime.setText(CommonUtils.getLoginDate(loginLog.time));
        holder.mTxtLoginAddress.setText(loginLog.address);
        holder.mTxtLoginType.setText(CommonUtils.getLoginType(loginLog.device));
        return convertView;
    }

    /**
     * view holder
     *
     * @author HY
     */
    class ViewHolder {
        @Bind(R.id.txt_login_time)
        TextView mTxtLoginTime;//login time
        @Bind(R.id.txt_login_address)
        TextView mTxtLoginAddress;//login adress
        @Bind(R.id.txt_login_type)
        TextView mTxtLoginType;//login type

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

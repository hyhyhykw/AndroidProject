package com.hy.filemanager.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.filemanager.R;
import com.hy.filemanager.entity.FileDetailInfo;
import com.hy.filemanager.utils.DateUitls;
import com.hy.filemanager.utils.FileUitls;

/**
 * file list adapter
 *
 * @author HY
 */
public class FileListAdapter extends BaseAdapter {

    /**
     * context object
     */
    private Context mContext;
    /**
     * adapter source
     */
    private List<FileDetailInfo> fileDetailInfos;

    public FileListAdapter(Context mContext,
                           List<FileDetailInfo> fileDetailInfos) {
        this.mContext = mContext;
        this.fileDetailInfos = fileDetailInfos;
    }

    @Override
    public int getCount() {
        return null != fileDetailInfos ? fileDetailInfos.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_lst_file_list, null);
            holder.mImgFileIcon = (ImageView) convertView
                    .findViewById(R.id.img_file_icon);
            holder.mTxtFileName = (TextView) convertView
                    .findViewById(R.id.txt_file_name);
            holder.mTxtFileTime = (TextView) convertView
                    .findViewById(R.id.txt_file_time);
            holder.mTxtFileSize = (TextView) convertView
                    .findViewById(R.id.txt_file_size);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FileDetailInfo fInfo = fileDetailInfos.get(position);
        File file = fInfo.getFile();
        if (FileUitls.isImageFile(fInfo.getSuffix())) {
            holder.mImgFileIcon.setImageURI(Uri.fromFile(file));
        } else {
            holder.mImgFileIcon.setImageResource(fInfo.getFileIcon());
        }
        holder.mTxtFileName.setText(fInfo.getFileName());

        holder.mTxtFileTime.setText(DateUitls.getDate(file));
        if (file.isDirectory()) {
            holder.mTxtFileSize.setVisibility(View.INVISIBLE);
        } else {
            holder.mTxtFileSize.setVisibility(View.VISIBLE);
            holder.mTxtFileSize.setText(FileUitls.formatLength(file.length()));
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return fileDetailInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List<FileDetailInfo> fileDetailInfos) {
        this.fileDetailInfos = fileDetailInfos;
        notifyDataSetChanged();
    }

    /**
     * @author HY
     */
    class ViewHolder {
        /**
         * file icon
         */
        ImageView mImgFileIcon;
        /**
         * file name
         */
        TextView mTxtFileName;
        /**
         * file time
         */
        TextView mTxtFileTime;
        /**
         * file size
         */
        TextView mTxtFileSize;
    }

}

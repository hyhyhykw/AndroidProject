package com.zx.news2.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zx.news2.R;
import com.zx.news2.entity.NewVersion;
import com.zx.news2.presenter.HttpPresenter;
import com.zx.news2.utils.constant.Constant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HY on 2017/1/5.
 * judge is or not update
 *
 * @author HY
 */
public class UpdateManager {
    private HttpPresenter mPresenter;
    private NewsView mNewsView;
    private Context mContext;
    private AlertDialog.Builder mBuilder;

    /*new version*/
    private NewVersion newVersion;

    public UpdateManager(Context context, NewsView newsView) {
        mContext = context;
        mNewsView = newsView;
        mPresenter = new HttpPresenter(mContext, mNewsView, mListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, R.string.connect_error, Toast.LENGTH_SHORT).show();
                mNewsView.cancelDialog();
            }
        });
        initDialog();
    }

    /**
     * initialize dialog
     */
    private void initDialog() {
        mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setMessage(R.string.found_new_version);
        mBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mNewsView.download(newVersion.link);
            }
        });
        mBuilder.setNegativeButton("否", null);
    }

    /**
     * get request data
     *
     * @return request data
     */
    private Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("imei", CommonUtils.getImei(mContext));
        data.put("pkg", CommonUtils.getPkgName(mContext));
        data.put("ver", "" + CommonUtils.getCurrVer(mContext));
        return data;
    }


    protected Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Gson gson = new Gson();
            newVersion = gson.fromJson(jsonObject.toString(), new TypeToken<NewVersion>() {
            }.getType());
            mNewsView.cancelDialog();
            int version = Integer.parseInt(newVersion.version);
            if (!isUpdate(version)) {
                Toast.makeText(mContext, R.string.text_update_msg, Toast.LENGTH_SHORT).show();
            } else {
                mBuilder.show();
            }
        }
    };

    /**
     * judge current version is or not new version
     *
     * @param newVersion new version code
     * @return is or not update
     */
    private boolean isUpdate(int newVersion) {//TODO
//        return CommonUtils.getCurrVer(mContext) < newVersion;
        return CommonUtils.getCurrVer(mContext) == newVersion;
    }

    /**
     * update
     */
    public void update() {
        mPresenter.requestHttpForGet(Constant.PATH_UPDATE + CommonUtils.map2String(getData()));
    }
}

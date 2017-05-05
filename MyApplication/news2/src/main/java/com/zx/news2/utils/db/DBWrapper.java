package com.zx.news2.utils.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.zx.news2.R;
import com.zx.news2.entity.NewsDetail;
import com.zx.news2.utils.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HY on 2016/12/27.
 * operate database
 *
 * @author HY
 */

@SuppressWarnings("ALL")
public class DBWrapper {
    private Context mContext;
    private DBHelper mHelper;
    private SQLiteDatabase db;
    private String mDataPath;

    @SuppressLint("StaticFieldLeak")
    private static DBWrapper mDBWrapper;

    private DBWrapper(Context context) {
        this.mContext = context;
        mHelper = new DBHelper();
        mDataPath = mContext.getFilesDir().getParent() + Constant.DB_PATH_NEWS;
//        LogWrapper.e("TAG",String.valueOf(null==mHelper));
        db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);
        db.execSQL("create table "
                + Constant.DB_TABLE_FAVORITE + "("
                + Constant.TABLE_NAME_ID +
                Constant.TABLE_PRIMARY_TYPE
                + Constant.TABLE_NAME_SUMMARY + " text," +
                Constant.TABLE_NAME_ICON + " text," +
                Constant.TABLE_NAME_STAMP + " text," +
                Constant.TABLE_NAME_TITLE + " text," +
                Constant.TABLE_NAME_LINK + " text," +
                Constant.TABLE_NAME_NID + " text)");
        db.close();
    }

    /**
     * singleton pattern
     *
     * @param context context object
     * @return this class object
     */
    public static DBWrapper getInstance(Context context) {
        if (null == mDBWrapper) {
            synchronized (DBHelper.class) {
                if (null == mDBWrapper) {
                    mDBWrapper = new DBWrapper(context);
                }
            }
        }
        return mDBWrapper;
    }

    /**
     * insert into database
     *
     * @param newsDetail news detail
     */
    public void instert(String tableName, NewsDetail newsDetail) {
        Toast.makeText(mContext, R.string.success_collected, Toast.LENGTH_SHORT).show();
//        db = mHelper.getWritableDatabase();
        db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);
        //insert arguments
        String[] args = {newsDetail.summary, newsDetail.icon,
                newsDetail.stamp, newsDetail.title,
                newsDetail.link, newsDetail.nid};
        //statement
        db.execSQL("insert into " + tableName
                + "(" + Constant.TABLE_NAME_SUMMARY + "," +
                Constant.TABLE_NAME_ICON + "," +
                Constant.TABLE_NAME_STAMP + "," +
                Constant.TABLE_NAME_TITLE + "," +
                Constant.TABLE_NAME_LINK + "," +
                Constant.TABLE_NAME_NID + ") values(?,?,?,?,?,?)", args);
        db.close();
    }

    /**
     * update database
     *
     * @param tableName  table name
     * @param newsDetail news detail
     */
    public void update(String tableName, NewsDetail newsDetail) {
        db = mHelper.getWritableDatabase();
        //TODO
        db.close();
    }

    /**
     * delete item
     *
     * @param tableName  table name
     * @param newsDetail news detail
     */
    public void delete(String tableName, NewsDetail newsDetail) {
        Toast.makeText(mContext, "已经删除了这条收藏新闻", Toast.LENGTH_SHORT).show();
//        db = mHelper.getWritableDatabase();
        db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);
        db.execSQL("delete from " + tableName
                        + " where " + Constant.TABLE_NAME_NID + "=?"
                , new String[]{newsDetail.nid});
        db.close();
    }

    /**
     * selete from table
     *
     * @param tableName table name
     * @return news detail list
     */
    public List<NewsDetail> selete(String tableName) {
        List<NewsDetail> newsDetails = new ArrayList<>();
//        db = mHelper.getWritableDatabase();
        db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            NewsDetail newsDetail = new NewsDetail();
            newsDetail.summary = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_SUMMARY));
            newsDetail.icon = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_ICON));
            newsDetail.stamp = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_STAMP));
            newsDetail.title = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_TITLE));
            newsDetail.link = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_LINK));
            newsDetail.nid = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_NID));
            newsDetails.add(newsDetail);
        }
        cursor.close();
        db.close();
        return newsDetails;
    }


    /**
     * judge such news is or not exist
     *
     * @param nid news nid
     * @return such news is or not exist
     */
    public boolean newsExist(String nid) {
//        db = mHelper.getWritableDatabase();
        db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);
        Cursor cursor = db.query(Constant.DB_TABLE_FAVORITE,
                new String[]{Constant.TABLE_NAME_NID},
                "nid=?", new String[]{nid}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    /**
     * create table
     *
     * @author HY
     */
    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper() {
            super(mContext, mDataPath, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db=SQLiteDatabase.openOrCreateDatabase(mDataPath,null);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

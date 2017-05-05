package com.zx.contact.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.zx.contact.entity.User;
import com.zx.contact.utils.constant.Constant;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created Time: 2017/1/23 17:23.
 *
 * @author HY
 *         创建本地数据库，方便获取联系人列表
 */
@SuppressLint("StaticFieldLeak")
public class DBWrapper {

    private Context mContext;
    private String mDataPath;
    private DBHelper mHelper;
    private SQLiteDatabase db;


    private static DBWrapper mDBWrapper;

    private DBWrapper(Context context) {
        this.mContext = context;
        mDataPath = mContext.getFilesDir().getParent() + Constant.DB_PATH_USERS;
        mHelper = new DBHelper();
    }

    public static DBWrapper newInstance(Context context) {
        if (null == mDBWrapper) {
            synchronized (DBWrapper.class) {
                if (null == mDBWrapper) {
                    mDBWrapper = new DBWrapper(context);
                }
            }
        }
        return mDBWrapper;
    }

    /**
     * insert
     *
     * @param users 联系人
     */
    public void insert(List<User> users) {
        db = mHelper.getWritableDatabase();

        //clear table users
        db.execSQL("delete from " + Constant.DB_TABLE_USERS);
        db.execSQL("update " + Constant.DB_TABLE_SQLITE_SEQUENCE + " set seq=0 where name='" + Constant.DB_TABLE_USERS + "'");

        for (User user : users) {
            ContentValues values = new ContentValues();
            Bitmap photo = user.getPhoto();

            values.put(Constant.TABLE_NAME_RAW_CONTACT_ID, user.getRaw_contact_id());
            values.put(Constant.TABLE_NAME_NAME, user.getName());
            values.put(Constant.TABLE_NAME_PHONE, user.getPhone());
            values.put(Constant.TABLE_NAME_EMAIL, user.getEmail());

            if (null != photo) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] photoBytes = bos.toByteArray();
                values.put(Constant.TABLE_NAME_PHOTO, photoBytes);
            }
            values.put(Constant.TABLE_NAME_LABLE, user.getLable());
            db.insert(Constant.DB_TABLE_USERS, null, values);
        }
        Toast.makeText(mContext, "完成", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public List<User> query() {
        List<User> users = new ArrayList<>();
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(Constant.DB_TABLE_USERS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            String rawContactIdStr = cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_RAW_CONTACT_ID));
            user.setRaw_contact_id(Integer.parseInt(rawContactIdStr));
            user.setName(cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_NAME)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_PHONE)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_EMAIL)));
            user.setLable(cursor.getString(cursor.getColumnIndex(Constant.TABLE_NAME_LABLE)));
            byte[] bs = cursor.getBlob(cursor.getColumnIndex(Constant.TABLE_NAME_PHOTO));
            if (null != bs) {
                user.setPhoto(BitmapFactory.decodeByteArray(bs, 0, bs.length));
            }
            users.add(user);
        }
        cursor.close();
        db.close();
        return users;
    }

    /**
     * create table
     *
     * @author HY
     */
    private class DBHelper extends SQLiteOpenHelper {

        DBHelper() {
            super(mContext, mDataPath, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + Constant.DB_TABLE_USERS + "(" +
                    Constant.TABLE_NAME_ID + Constant.TABLE_PRIMARY_TYPE
                    + Constant.TABLE_NAME_RAW_CONTACT_ID + " text,"
                    + Constant.TABLE_NAME_NAME + " text,"
                    + Constant.TABLE_NAME_PHONE + " text,"
                    + Constant.TABLE_NAME_EMAIL + " text,"
                    + Constant.TABLE_NAME_PHOTO + " blob,"
                    + Constant.TABLE_NAME_LABLE + " text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

package com.hy.weather.biz.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hy.weather.biz.Constant;
import com.hy.weather.entity.CityInfo;
import com.hy.weather.entity.WeatherInfo;

import java.io.File;
import java.util.List;

/**
 * Created Time: 2017/1/25 15:02.
 *
 * @author HY
 */

public class DBWrapper {
    private Context mContext;
    private SQLiteDatabase db;
    private String mDataPath;

    @SuppressLint("StaticFieldLeak")
    private static DBWrapper mDBWrapper;

    @SuppressLint("SdCardPath")
    private DBWrapper(Context context) {
        this.mContext = context;
        mDataPath = "/data/data/com.hy.weather" + Constant.DB_FILE;
    }

    /**
     * singleton pattern
     *
     * @param context context object
     * @return this class object
     */
    public static DBWrapper newInstance(Context context) {
        if (null == mDBWrapper) {
            synchronized (DBHelper.class) {
                if (null == mDBWrapper) {
                    mDBWrapper = new DBWrapper(context);
                }
            }
        }
        return mDBWrapper;
    }

    public void insertCity(List<CityInfo> cityInfos) {
        db = SQLiteDatabase.openOrCreateDatabase(mDataPath, null);
        db.execSQL("delete from " + Constant.DB_TABLE_CITIES);
        db.execSQL("update " + Constant.DB_TABLE_SQLITE_SEQUENCE + " set seq=0 where name='" + Constant.DB_TABLE_CITIES + "'");
        for (CityInfo cityInfo : cityInfos) {
            Log.e("TAG", String.valueOf(cityInfo));
            String args[] = {cityInfo.getCityId(), cityInfo.getCityNameCn(), cityInfo.getCityNameEn(),
                    cityInfo.getCountry(), cityInfo.getCountryCode(), cityInfo.getExclusiveCn_1(), cityInfo.getExclusiveEn_1()
                    , cityInfo.getExclusiveCn_2(), cityInfo.getExclusiveEn_2(), cityInfo.getTimeZone(), cityInfo.getCityLevel()};
            db.execSQL("insert into " + Constant.DB_TABLE_CITIES + " (" +
                    "city_id" + "," + "city_name_cn" + "," +
                    "city_name_en" + "," + "country" + "," +
                    "country_code" + "," + "exclusive_cn_1" + "," +
                    "exclusive_en_1" + "," + "exclusive_cn_2" + "," +
                    "exclusive_en_2" + "," + "time_zone" + "," +
                    "city_level) values(?,?,?,?,?,?,?,?,?,?,?)", args);
        }
        db.close();
    }

    public void insertWeather(List<WeatherInfo> data) {
        db = SQLiteDatabase.openOrCreateDatabase(mDataPath, null);
        //TODO
        db.close();
    }


    /**
     * @author HY
     */
    private class DBHelper extends SQLiteOpenHelper {

        DBHelper() {
            super(mContext, mDataPath, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("TAG", "onCreate");
            try {
                if (!new File(mDataPath).exists()) {
                    db = SQLiteDatabase.openOrCreateDatabase(mDataPath, null);
                }
                db.execSQL("create table " +
                        Constant.DB_TABLE_CITIES
                        + "(_id integer primary key autoincrement not null," +
                        "city_id text," +
                        "city_name_cn text," +
                        "city_name_en text," +
                        "country text," +
                        "country_code text," +
                        "exclusive_cn_1 text," +
                        "exclusive_en_1 text," +
                        "exclusive_cn_2 text," +
                        "exclusive_en_2 text," +
                        "time_zone text," +
                        "city_level text)");
                Log.e("TAG", "create");
            } catch (Exception e) {
                Log.e("TAG", "ERROR");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

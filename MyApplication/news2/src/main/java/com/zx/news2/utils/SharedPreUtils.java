package com.zx.news2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zx.news2.entity.LoginLog;
import com.zx.news2.entity.News;
import com.zx.news2.entity.User;
import com.zx.news2.entity.UserDetail;
import com.zx.news2.utils.constant.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HY on 2016/12/30.
 * shared preference utils
 *
 * @author HY
 */
@SuppressWarnings("WeakerAccess")
public class SharedPreUtils {

    /**
     * clear login state
     *
     * @param context used to get shared preference
     */
    public static void clear(Context context) {
        context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences(Constant.SHARED_NAME_USER, Context.MODE_PRIVATE).edit().clear().commit();
    }

    /**
     * write login state
     *
     * @param context used to get shared preference
     * @param news    login state
     */
    public static void writeLogin(Context context, News<User> news, Map<String, String> data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE).edit();
        User user = news.data;
        editor.putInt(Constant.SHARED_KEY_STATUS, news.status);
        editor.putString(Constant.SHARED_KEY_MESSAGE, news.message);
        editor.putInt(Constant.SHARED_KEY_RESULT, user.result);
        editor.putString(Constant.SHARED_KEY_TOKEN, user.token);
        editor.putString(Constant.SHARED_KEY_EXPLAIN, user.explain);
        for (String str : data.keySet()) {
            editor.putString(str, data.get(str));
        }
        editor.commit();
    }

    /**
     * write user detail
     *
     * @param context    used to get shared preference
     * @param userDetail user detail
     */
    public static void writeUser(Context context, UserDetail<LoginLog> userDetail) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.SHARED_NAME_USER, Context.MODE_PRIVATE).edit();
        editor.putString(Constant.SHARED_KEY_PORTRAIT, userDetail.portrait);
        editor.putString(Constant.SHARED_KEY_UID, userDetail.uid);
        editor.commit();
    }

    /**
     * get token
     *
     * @param context used to get shared preference
     * @return user token
     */
    public static String getToken(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getString(Constant.SHARED_KEY_TOKEN, "");
    }

    /**
     * get user portrait link
     *
     * @param context used to get shared preference
     * @return user portrait link
     */
    public static String getPortrait(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_USER, Context.MODE_PRIVATE)
                .getString(Constant.SHARED_KEY_PORTRAIT, "");
    }

    /**
     * get user id
     *
     * @param context used to get shared preference
     * @return user id
     */
    public static String getUid(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_USER, Context.MODE_PRIVATE)
                .getString(Constant.SHARED_KEY_UID, "USER");
    }

    /**
     * get login result
     *
     * @param context used to get shared preference
     * @return login result
     */
    public static int getResult(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE)
                .getInt(Constant.SHARED_KEY_RESULT, 1);
    }

    /**
     * write run state
     *
     * @param context used to get shared preference
     */
    public static void writeIsFirst(Context context) {
        context.getSharedPreferences(Constant.SHARED_NAME_ISFIRST, Context.MODE_PRIVATE).edit()
                .putBoolean(Constant.SHARED_KEY_ISFIRST, false).commit();
    }

    /**
     * get run state
     *
     * @param context used to get shared preference
     * @return run state
     */
    public static boolean getIsFirst(Context context) {
        return context.getSharedPreferences(Constant.SHARED_NAME_ISFIRST, Context.MODE_PRIVATE).
                getBoolean(Constant.SHARED_KEY_ISFIRST, true);
    }


//    /**
//     * write current position
//     *
//     * @param context  used to get shared preference
//     * @param position current position
//     */
//    public static void writePos(Context context, int position) {
//        context.getSharedPreferences("state", Context.MODE_PRIVATE).edit().putInt("position", position).commit();
//    }

//    /**
//     * get last position
//     *
//     * @param context used to get shared preference
//     * @return last position
//     */
//    public static int getPos(Context context) {
//        return context.getSharedPreferences("state", Context.MODE_PRIVATE)
//                .getInt("position", 0);
//    }

    /**
     * get login data
     *
     * @param context used to get shared preference
     * @return login data
     */
    public static Map<String, String> getLogin(Context context) {
        Map<String, String> data = new HashMap<>();
        SharedPreferences shared = context.getSharedPreferences(Constant.SHARED_NAME_LOGIN, Context.MODE_PRIVATE);
        data.put(Constant.MAP_KEY_UID, shared.getString(Constant.SHARED_KEY_UID, ""));
        data.put(Constant.MAP_KEY_PWD, shared.getString(Constant.SHARED_KEY_PWD, ""));
        data.put(Constant.MAP_KEY_DEVICE, "0");
        return data;
    }

}
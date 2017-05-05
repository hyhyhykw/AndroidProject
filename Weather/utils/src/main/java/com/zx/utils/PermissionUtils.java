package com.zx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created Time: 2017/3/1 19:14.
 *
 * @author HY
 */
public class PermissionUtils implements Permissions {

    private static Context mContext;

    public static final int REQUEST_CODE = 111;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 请求权限
     *
     * @param activity   请求权限的Activity
     * @param permission 权限 {@link #CAMERA}{@link #READ_CALENDAR}{@link #READ_CONTACTS}
     *                   {@link #GET_ACCOUNTS} {@link #LOCATION} {@link #WRITE_CALENDAR}
     *                   {@link #WRITE_CONTACTS}
     */
    public static void request(Activity activity, String permission) {
        if (null == permission || null == activity) return;
        if (VERSION.SDK_INT >= 23) {
            int permissionCode = ContextCompat.checkSelfPermission(mContext, permission);
            if (permissionCode != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE);
            }
        }
    }

    /**
     * 处理
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    public static void handlerRequest(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public static abstract class PermissionHandler{

    }
}

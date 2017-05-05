package com.zx.eshop.base.wrapper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created Time: 2017/2/28 15:26.
 *
 * @author HY
 *         显示吐司的封装类
 */
public class ToastWrapper {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static Toast mToast;

    /**
     * 初始化工具类，为了方便调用，可以在Application中初始化
     *
     * @param context 上下文对象
     */
    @SuppressLint("ShowToast")
    public static void init(Context context) {
        mContext = context;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    /**
     * 显示土司消息
     *
     * @param resId 资源id
     * @param args  参数
     */
    public static void show(@StringRes int resId, Object... args) {
        String text = mContext.getString(resId, args);
        show(text);
    }

    /**
     * 显示吐司消息
     *
     * @param msg  消息
     * @param args 参数
     */
    public static void show(String msg, Object... args) {
        String text = String.format(msg, args);
        mToast.setText(text);
        mToast.show();
    }

}

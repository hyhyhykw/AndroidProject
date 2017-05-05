package com.feicuiedu.eshop_0221.base.wrapper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gqq on 2017/2/28.
 */
// 吐司的包装类
public class ToastWrapper {

    private static Toast mToast;
    private static Context mContext;

    // Toast初始化操作，在调用show之前一定要先调用init，所以初始化写到application里面
    public static void init(Context context){
        mContext = context;
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void show(int resId,Object... args){
        String text = mContext.getString(resId, args);
        mToast.setText(text);
        mToast.show();
    }

    public static void show(CharSequence charSequence,Object... args){
        String text = String.format(charSequence.toString(),args);
        mToast.setText(text);
        mToast.show();
    }
}

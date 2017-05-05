package com.zx.contact.model;

import android.database.ContentObserver;
import android.os.Handler;

import com.zx.contact.utils.LogWrapper;

/**
 * 通讯录改变监听类
 */
public class ContactContentObservers extends ContentObserver {

    private static String TAG = ContactContentObservers.class.getSimpleName();
    private static final int CONTACT_CHANGE = 1;
    private Handler mHandler;

    public ContactContentObservers(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        LogWrapper.v(TAG, "the contacts has changed");
        mHandler.sendEmptyMessage(CONTACT_CHANGE);
    }

}

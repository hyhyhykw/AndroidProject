package com.hy.filemanager.utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUitls {

    private static final String PATTERN = "yy-MM-dd HH:mm:ss";

    /**
     * get file last modify date string
     *
     * @param file file
     * @return file datatime
     */
    public static String getDate(File file) {
        long tmpData = file.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        return sdf.format(tmpData);
    }

    /**
     * get date by date string
     *
     * @param dateStr data string
     * @return data object
     */
    public static Date str2Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}

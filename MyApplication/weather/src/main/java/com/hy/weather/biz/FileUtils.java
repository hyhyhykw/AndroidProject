package com.hy.weather.biz;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created Time: 2017/1/25 14:05.
 *
 * @author HY
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static void unZip(Context context) {
        String zipFilePath = context.getExternalFilesDir(null)
                .getAbsolutePath() + "/icons";
        FileOutputStream fos;
        InputStream is;
        ZipInputStream zis = null;
        int count;
        File file = new File(zipFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        byte[] buffer = new byte[1024 * 1024];
        try {
            is = context.getAssets().open("weather_icon.zip");
            zis = new ZipInputStream(is);
            ZipEntry zipEntry = zis.getNextEntry();
            while (null != zipEntry) {
                file = new File(zipFilePath + File.separator + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!file.exists()) file.mkdirs();
                } else {
                    if (!file.exists()) {
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                        while ((count = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, count);
                        }
                        fos.close();
                    }
                }
                zipEntry = zis.getNextEntry();
            }
        } catch (IOException e) {
            Log.e(TAG, "IO ERROR");
        } finally {
            if (null != zis)
                try {
                    zis.close();
                } catch (IOException e) {
                    Log.e(TAG, "IO Close Error");
                }
        }
    }
}

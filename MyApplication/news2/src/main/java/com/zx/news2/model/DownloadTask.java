package com.zx.news2.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;

import com.zx.news2.utils.LogWrapper;

/**
 * Created by HY on 2017/1/3.
 * download new version
 *
 * @author HY
 */
public class DownloadTask extends AsyncTask<String, Integer, File> {

    private static final String TAG = DownloadTask.class.getSimpleName();
    protected OnDownoadListener mListener;

    public DownloadTask(OnDownoadListener listener) {
        this.mListener = listener;
    }

    @Override
    protected File doInBackground(String... strings) {
        File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                fos = new FileOutputStream(file);
                bis = new BufferedInputStream(is);
                byte[] bs = new byte[1024];
                long sum = conn.getContentLength();
                mListener.fileLength(sum);
                int length;
                long total = 0;
                publishProgress((int) total);
                while ((length = bis.read(bs)) != -1) {
                    if (isCancelled())
                        break;
                    fos.write(bs, 0, length);
                    total += length;
                    publishProgress((int) (total * 100 / sum));
                    mListener.downLength(total);
                }
            }
            return file;
        } catch (MalformedURLException e) {
            LogWrapper.e(TAG, "Malformed URL");
        } catch (IOException e) {
            LogWrapper.e(TAG, "IO Error");
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LogWrapper.e(TAG, "FOS Close Error");
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    LogWrapper.e(TAG, "BIS Close Error");
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        if (null != file)
            mListener.onResponseSuccess(file);
        else
            mListener.onResponseFail("Download Error");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mListener.onProgress(values[0]);
    }

    /**
     * Created by HY on 2017/1/3.
     * download listener
     *
     * @author HY
     */
    public interface OnDownoadListener {
        /**
         * response success
         *
         * @param file param file
         */
        void onResponseSuccess(File file);

        /**
         * download progress
         *
         * @param progress progress
         */
        void onProgress(int progress);

        /**
         * download error
         *
         * @param error error msg
         */
        void onResponseFail(String error);

        /**
         * get new file total length
         *
         * @param length new file total length
         */
        void fileLength(long length);

        /**
         * get already down file length
         *
         * @param length already down file length
         */
        void downLength(long length);
    }
}


package com.zx.news2.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import com.zx.news2.utils.LogWrapper;

/**
 * Created by HY on 2017/1/4.
 * used to download and upload file
 *
 * @author HY
 */
public class UploadTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = UploadTask.class.getSimpleName();
    private static final String CHARSET = "utf-8";
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    private File mImgFile;
    protected Map<String, String> data;


    public UploadTask(Bitmap headIcon, Map<String, String> data) {
        File imgFile = new File(Environment.getExternalStorageDirectory(), "news_temp.png");
        try {
            FileOutputStream fos = new FileOutputStream(imgFile);
            headIcon.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.mImgFile = imgFile;
        this.data = data;
    }

    @Override
    protected String doInBackground(String... strings) {
        DataOutputStream dos = null;
        InputStream is = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sbf = new StringBuffer();
            for (String s : data.keySet()) {
                String value = data.get(s);
                sbf.append(PREFIX).append(BOUNDARY).append(LINE_END);
                sbf.append("Content-Disposition: form-data; name=\"").append(s).append("\"").append(LINE_END).append(LINE_END);
                sbf.append(value).append(LINE_END);
                dos.write(sbf.toString().getBytes());
            }

            sbf = new StringBuffer();
            sbf.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sbf.append("Content-Disposition:form-data; name=\"").append("portrait").append("\"; filename=\"").append(mImgFile.getName()).append("\"").append(LINE_END);
            sbf.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            sbf.append(LINE_END);
            dos.write(sbf.toString().getBytes());

            /*upload file*/
            is = new FileInputStream(mImgFile);
            // onUploadProcessListener.initUpload((int) file.length());
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
                // onUploadProcessListener.onUploadProcess(curLen);
            }

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);

            if (conn.getResponseCode() == 200)
                return "success";
        } catch (MalformedURLException e) {
            LogWrapper.e(TAG, "MalformedURL");
        } catch (IOException e) {
            LogWrapper.e(TAG, "IO Error");
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogWrapper.e(TAG, "IS Close Error");
                }
            }
            if (null != dos) {
                try {
                    dos.close();
                } catch (IOException e) {
                    LogWrapper.e(TAG, "DOS Close Error");
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        LogWrapper.e(TAG, null != s ? s : "failed");
    }
}
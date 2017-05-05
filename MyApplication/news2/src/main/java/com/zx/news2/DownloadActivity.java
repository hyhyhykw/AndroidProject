package com.zx.news2;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.news2.model.DownloadTask;
import com.zx.news2.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * download page :show download progress
 *
 * @author HY
 */
public class DownloadActivity extends BaseActivity implements DownloadTask.OnDownoadListener {
    @Bind(R.id.txt_total_length)
    protected TextView mTxtFileLength;
    @Bind(R.id.txt_down_length)
    protected TextView mTxtDownLength;
    @Bind(R.id.pgb_download)
    protected ProgressBar mPgbDownload;

    protected DownloadTask mDownloadTask;
    protected String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
        link = getBundle().getString("link");
        mDownloadTask = new DownloadTask(this);
        mDownloadTask.execute("http://117.34.51.232/211/27/24/muzhiwan/0/letv.cdn.muzhiwan.com/2016/12/30/com.berryrabbit.rpgcookus_586606df341c3.apk?crypt=20aa7f2e98550&b=1314&nlh=4096&nlt=60&bf=6000&p2p=1&video_type=apk&termid=0&tss=no&platid=0&splatid=0&its=0&qos=5&fcheck=0&proxy=22207914,3683668026,1786224534&uid=22040725.rp&keyitem=GOw_33YJAAbXYE-cnQwpfLlv_b2zAkYctFVqe5bsXQpaGNn3T1-vhw..&ntm=1483470000&nkey=585cce46be1ab2f2ae9526397c8b4581&nkey2=228cc1599e0099631d718d36312bff99&geo=CN-27-376-1&errc=0&gn=1072&vrtmcd=102&buss=106551&cips=1.80.80.149&lersrc=Y2MuY2RuLm11emhpd2FuLmNvbQ==&tag=muzhiwan&cuhost=letv.cdn.muzhiwan.com&cuid=134810&fext=.apk");//TODO
//        execute(link);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDownloadTask.cancel(true);
    }

    @Override
    public void onResponseSuccess(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    public void onProgress(int progress) {
        mPgbDownload.setProgress(progress);
    }

    @Override
    public void onResponseFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void fileLength(final long length) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtFileLength.setText(CommonUtils.formatLength(length));
            }
        });

    }

    @Override
    public void downLength(final long length) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtDownLength.setText(CommonUtils.formatLength(length));
            }
        });
    }

    @OnClick({R.id.txt_redown, R.id.txt_cancel_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_redown:
                mDownloadTask.cancel(true);
                mDownloadTask = new DownloadTask(this);
                mDownloadTask.
                        execute("http://117.34.51.232/211/27/24/muzhiwan/0/letv.cdn.muzhiwan.com/2016/12/30/com.berryrabbit.rpgcookus_586606df341c3.apk?crypt=20aa7f2e98550&b=1314&nlh=4096&nlt=60&bf=6000&p2p=1&video_type=apk&termid=0&tss=no&platid=0&splatid=0&its=0&qos=5&fcheck=0&proxy=22207914,3683668026,1786224534&uid=22040725.rp&keyitem=GOw_33YJAAbXYE-cnQwpfLlv_b2zAkYctFVqe5bsXQpaGNn3T1-vhw..&ntm=1483470000&nkey=585cce46be1ab2f2ae9526397c8b4581&nkey2=228cc1599e0099631d718d36312bff99&geo=CN-27-376-1&errc=0&gn=1072&vrtmcd=102&buss=106551&cips=1.80.80.149&lersrc=Y2MuY2RuLm11emhpd2FuLmNvbQ==&tag=muzhiwan&cuhost=letv.cdn.muzhiwan.com&cuid=134810&fext=.apk");//TODO
//        execute(link);
                break;
            case R.id.txt_cancel_down:
                mDownloadTask.cancel(true);
                finish();
                break;
        }
    }
}
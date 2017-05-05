package com.zx.player;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zx.player.entity.Last;
import com.zx.player.entity.Next;
import com.zx.player.entity.Pause;
import com.zx.player.entity.Play;
import com.zx.player.entity.Seek;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.txt_name)
    protected TextView mTvName;
    @BindView(R.id.tv_time)
    protected TextView mTvTime;
    @BindView(R.id.tv_all_time)
    protected TextView mTvTimeAll;
    @BindView(R.id.seekbar)
    protected SeekBar mSeekBar;
    @BindView(R.id.iv_pause)
    protected ImageView mIvPause;
    @BindView(R.id.iv_play)
    protected ImageView mIvPlay;

    private MediaBroadcastReceiver mReceiver;
    private Unbinder mUnbinder;

    private Intent service;
    private static final String OPERATION = "operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开始获取媒体信息
        mUnbinder = ButterKnife.bind(this);
        if (null == service)
            service = new Intent(this, PlayerService.class);
        startService(service);

        //广播接受者
        receive();
        initEvent();
    }

    //广播相关
    private void receive() {
        mReceiver = new MediaBroadcastReceiver();

        //媒体信息
        mReceiver.setOnReceiveMusicInfoListener(new MediaBroadcastReceiver.OnReceiveMusicInfoListener() {
            @Override
            public void onReceiveMusicInfo(MusicInfo musicInfo) {
                //接受到媒体信息
                if (null == musicInfo) return;
                mTvName.setText(musicInfo.getName());
                mTvTimeAll.setText(Utils.timeParse(musicInfo.getTime()));
                mSeekBar.setMax(musicInfo.getTime());

            }
        });

//        //播放状态
//        mReceiver.setOnReceiveStateListener(new MediaBroadcastReceiver.OnReceiveStateListener() {
//            @Override
//            public void onReceiveState(boolean isPlaying) {
//                if (isPlaying) {
//                    mIvPlay.setVisibility(View.VISIBLE);
//                    mIvPause.setVisibility(View.GONE);
//                } else {
//                    mIvPlay.setVisibility(View.GONE);
//                    mIvPause.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });

        //播放进度
        mReceiver.setOnReceiveProgressListener(new MediaBroadcastReceiver.OnReceiveProgressListener() {
            @Override
            public void onReceiveProgress(int progress) {
                mSeekBar.setProgress(progress);
            }
        });

        //创建广播过滤器，注册并接受广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(MediaBroadcastReceiver.ACTION_MEDIA);
        registerReceiver(mReceiver, filter);
    }

    //初始化事件
    private void initEvent() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvTime.setText(String.format("%s/", Utils.timeParse(progress)));
                // TODO: 2017/2/22
                if (null != service) {
                    service.putExtra("progress", progress);
                    service.putExtra(OPERATION, new Seek());
                    startService(service);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (null != service) {
                    service.putExtra(OPERATION, new Pause());
                    startService(service);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @OnClick({R.id.iv_last, R.id.iv_next, R.id.iv_play, R.id.iv_pause, R.id.iv_stop})
    public void onClick(View v) {
        //不管点击哪一个，都将SeekBar的进度设置为0
        mSeekBar.setProgress(0);
        switch (v.getId()) {
            case R.id.iv_last:
                // TODO: 2017/2/22 上一个
                if (null == service) return;
                service.putExtra(OPERATION, new Last());
                startService(service);
                break;
            case R.id.iv_next:
                // TODO: 2017/2/22 下一个
                if (null == service) return;

                service.putExtra(OPERATION, new Next());
                startService(service);
                break;
            case R.id.iv_play:
                // TODO: 2017/2/22 播放
                mIvPlay.setVisibility(View.GONE);
                mIvPause.setVisibility(View.VISIBLE);

                if (null == service)
                    service = new Intent(this, PlayerService.class);
                service.putExtra(OPERATION, new Play());
                startService(service);
                break;
            case R.id.iv_pause:
                // TODO: 2017/2/22 暂停
                mIvPlay.setVisibility(View.VISIBLE);
                mIvPause.setVisibility(View.GONE);
                if (null == service) return;
                service.putExtra(OPERATION, new Pause());
                startService(service);
                break;
            case R.id.iv_stop:
                // TODO: 2017/2/22 停止
                if (null == service) return;
                stopService(service);
                service = null;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (null != mReceiver)
            unregisterReceiver(mReceiver);
        if (null != service) {
            stopService(service);
            service = null;
        }
    }
}

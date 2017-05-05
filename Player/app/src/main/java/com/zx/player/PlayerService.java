package com.zx.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.zx.player.entity.Last;
import com.zx.player.entity.Next;
import com.zx.player.entity.Pause;
import com.zx.player.entity.Play;
import com.zx.player.entity.Seek;

import java.io.Serializable;

public class PlayerService extends Service {

    private MediaPlayer mMediaPlayer;
    private int[] musics = {R.raw.wuqilong_zhuanwan, R.raw.xiaguang, R.raw.yanyuan, R.raw.mxd_froze_room};

    private String[] musicNames = {"转弯", "霞光", "演员", "冰之神殿"};

    private int count;

    private static final String OPERATION = "operation";


    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new PlayerThread().start();
        new ProgressThread().start();
        createMedia();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Serializable operation = intent.getSerializableExtra(OPERATION);
        if (null != operation) {
            if (operation instanceof Last) {
                last();
                isChanged = true;
                createMedia();
            }
            if (operation instanceof Next) {
                next();
                isChanged = true;
                createMedia();
            }

            if (operation instanceof Pause) {
                pause();
            }
            if (operation instanceof Play) {
                play();
            }
            if (operation instanceof Seek) {
                seekTo(intent);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    //创建播放器
    private void createMedia() {
        //按键的操作
        int position = count % musics.length;
        mMediaPlayer = MediaPlayer.create(this, musics[position]);

//        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                mp.reset();
//                return true;
//            }
//        });
        //通过广播将对象发送出去
        MusicInfo musicInfo = new MusicInfo(mMediaPlayer.getDuration(),
                musicNames[position]);
        Intent media = new Intent(MediaBroadcastReceiver.ACTION_MEDIA);

        media.putExtra(MediaBroadcastReceiver.MUSIC, musicInfo);
        sendBroadcast(media);
        isChanged = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent media = new Intent(MediaBroadcastReceiver.ACTION_MEDIA);
        media.putExtra(MediaBroadcastReceiver.MUSIC, new MusicInfo(6000, "音乐"));
        sendBroadcast(media);
        isStop = true;
        release();
    }

    //释放资源
    private void release() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //##############################    媒体的控制   #################################

    /**
     * 暂停
     */
    private void pause() {
        if (null == mMediaPlayer)
            mMediaPlayer = MediaPlayer.create(this, musics[0]);
        mMediaPlayer.pause();
    }

    /**
     * 播放
     */
    private void play() {
        if (null == mMediaPlayer || mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 下一个
     */
    private void next() {
        // TODO: 2017/2/23
//        release();
        count++;
    }

    /**
     * 上一个
     */
    private void last() {
        // TODO: 2017/2/23
//        release();
        if (count == 0) count = 4;
        count--;
    }

    /**
     * 进度跳转
     *
     * @param intent 意图
     */
    private void seekTo(Intent intent) {
        // TODO: 2017/2/23
        //进度跳转
        int progress = intent.getIntExtra("progress", -1);
        if (progress != -1 && null != mMediaPlayer) {
            mMediaPlayer.seekTo(progress);
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }
    }

    private boolean isStop = false;

    //##############################    新的线程     #####################################
    class PlayerThread extends Thread {

        @Override
        public void run() {
            while (true) {
                if (isStop) {
                    break;
                }
                //每隔3秒发送一个广播，用以获取媒体的状态
                if (null != mMediaPlayer) {
                    Intent intent = new Intent(MediaBroadcastReceiver.ACTION_MEDIA);
                    intent.putExtra(MediaBroadcastReceiver.ACTION_MEDIA, mMediaPlayer.isPlaying());
                    sendBroadcast(intent);
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //#####################################     进度      #################################
    private boolean isChanged;

    class ProgressThread extends Thread {

        @Override
        public void run() {
            while (true) {
                if (isStop) break;
                if (null != mMediaPlayer && !isChanged) {
                    Intent intent = new Intent(MediaBroadcastReceiver.ACTION_MEDIA);
                    intent.putExtra(MediaBroadcastReceiver.PROGRESS, mMediaPlayer.getCurrentPosition());
                    sendBroadcast(intent);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

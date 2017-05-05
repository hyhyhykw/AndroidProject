
package com.zx.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created Time: 2017/2/22 19:13.
 *
 * @author HY
 */

public class MediaBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_MEDIA = "action.send.media";


    public static final String MUSIC = "music";

    public static final String STATE = "state";

    public static final String PROGRESS = "progress";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != mOnReceiveMusicInfoListener) {
            mOnReceiveMusicInfoListener.
                    onReceiveMusicInfo((MusicInfo) intent.getSerializableExtra(MUSIC));
        }
        if (null != mOnReceiveStateListener) {
            mOnReceiveStateListener.onReceiveState(intent.getBooleanExtra(STATE, false));
        }
        if (null != mOnReceiveProgressListener) {
            mOnReceiveProgressListener.onReceiveProgress(intent.getIntExtra(PROGRESS, 0));
        }

    }

    //###############################      媒体信息监听      #########################################
    private OnReceiveMusicInfoListener mOnReceiveMusicInfoListener;

    public void setOnReceiveMusicInfoListener(OnReceiveMusicInfoListener onReceiveMusicInfoListener) {
        mOnReceiveMusicInfoListener = onReceiveMusicInfoListener;
    }

    public interface OnReceiveMusicInfoListener {
        void onReceiveMusicInfo(MusicInfo musicInfo);
    }

    //################################        媒体状态监听           #################################
    public interface OnReceiveStateListener {
        void onReceiveState(boolean isPlaying);
    }

    private OnReceiveStateListener mOnReceiveStateListener;

    public void setOnReceiveStateListener(OnReceiveStateListener onReceiveStateListener) {
        mOnReceiveStateListener = onReceiveStateListener;
    }

    //##############################         进度                #############################

    public interface OnReceiveProgressListener {
        void onReceiveProgress(int progress);
    }

    private OnReceiveProgressListener mOnReceiveProgressListener;

    public void setOnReceiveProgressListener(OnReceiveProgressListener onReceiveProgressListener) {
        mOnReceiveProgressListener = onReceiveProgressListener;
    }
}

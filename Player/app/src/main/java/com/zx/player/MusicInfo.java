package com.zx.player;

import java.io.Serializable;

/**
 * Created Time: 2017/2/22 19:16.
 *
 * @author HY
 */

public class MusicInfo implements Serializable {
    private int time;//时间
    private String name;//名称

    public MusicInfo(int time, String name) {
        this.time = time;
        this.name = name;
    }

    public MusicInfo() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

package com.hy.patrol_leader.model;

/**
 * Created time : 2017/4/28 14:11.
 *
 * @author HY
 */

public class SettingItem {
    private int icon;
    private String title;

    public SettingItem() {
    }

    public SettingItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

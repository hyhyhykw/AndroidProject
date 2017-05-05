package com.zx.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created Time: 2017/2/10 15:38.
 *
 * @author HY
 */
public class User {
    /**
     * username : xc62    用户名
     * name : yt59856b15cf394e7b84a7d48447d16098  环信id
     * uuid : 0F8EC12223174657B2E842076D54C361    环信用户表主键
     * password : 123456  密码
     */
    @SerializedName("username")
    private String name;
    @SerializedName("name")
    private String hxId;
    @SerializedName("uuid")
    private String tableId;
    private String password;
    @SerializedName("other")
    private  String headIcon;
    private String nickname;

    public void setName(String name) {
        this.name = name;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getHxId() {
        return hxId;
    }

    public String getTableId() {
        return tableId;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public String getNickname() {
        return nickname;
    }
}

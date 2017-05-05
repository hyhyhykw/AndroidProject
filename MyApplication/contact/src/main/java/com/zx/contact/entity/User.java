package com.zx.contact.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


/**
 * Created time : 2017/1/12 17:49.
 *
 * @author HY
 *         实体类中含有Bitmap，不能序列化，需要实现Parcelable和Parcelable.Creator接口
 *         用于在Intent是传递数据，实现Comparable是为了排序
 */

public class User implements Comparable<User>, Parcelable, Parcelable.Creator {

    private int raw_contact_id;
    private String name;
    private String phone;
    private String email;
    private Bitmap photo;
    private String lable;

    public User() {

    }

    private User(Parcel in) {
        raw_contact_id = in.readInt();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        lable = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getRaw_contact_id() {
        return raw_contact_id;
    }

    public void setRaw_contact_id(int raw_contact_id) {
        this.raw_contact_id = raw_contact_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return  name +
                "," + phone +
                "," + email +
                "," + lable ;
    }

    @Override
    public int compareTo(@NonNull User user) {
        if (user.getLable().equals("#"))
            return -1;
        else if (lable.equals("#"))
            return 1;
        else
            return this.lable.compareTo(user.getLable());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(raw_contact_id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeParcelable(photo, flags);
        dest.writeString(lable);
    }

    @Override
    public Object createFromParcel(Parcel source) {
        return null;
    }

    @Override
    public Object[] newArray(int size) {
        return new Object[0];
    }
}

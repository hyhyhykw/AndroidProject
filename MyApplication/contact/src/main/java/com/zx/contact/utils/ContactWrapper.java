package com.zx.contact.utils;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

import com.zx.contact.R;
import com.zx.contact.entity.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created time : 2017/1/12 17:46.
 *
 * @author HY
 */

public class ContactWrapper {

    private final static String TAG = ContactWrapper.class.getSimpleName();
    private Context mContext;
    private ContentResolver mResolver;

    public ContactWrapper(Context context) {
        this.mContext = context;
        mResolver = context.getContentResolver();
    }

    /**
     * 查询全部列表
     *
     * @return 联系人信息
     */
    public List<User> query() {
        List<User> users = new ArrayList<>();
        //查询contact表,获取contact_id
        Cursor cursor = mResolver.query(Contacts.CONTENT_URI, null, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                int contact_id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
                //根据contact_id 在rawcontact中查询
                Cursor rawContactCursor = mResolver.query(RawContacts.CONTENT_URI, null,
                        RawContacts.CONTACT_ID + "=?", new String[]{contact_id + ""}, null);
                //查询信息
                if (null != rawContactCursor) {
                    while (rawContactCursor.moveToNext()) {
                        int raw_contact_id = rawContactCursor.getInt(rawContactCursor.getColumnIndex(RawContacts._ID));
                        //从数据库中获取数据
                        String name = getName(raw_contact_id);
                        String phone = getPhone(raw_contact_id);
                        String email = getEmail(raw_contact_id);
                        Bitmap photo = getPhoto(raw_contact_id);
                        if (phone.equals("")) {
                            continue;
                        }
                        //创建实体类
                        User user = new User();
                        user.setRaw_contact_id(raw_contact_id);
                        user.setName(name);
                        user.setPhone(phone);
                        user.setEmail(email);
                        user.setPhoto(photo);
                        user.setLable(name.equals("") ? "#" : ChineseUtils.Chinese2Spell(name));
                        users.add(user);
                    }
                }
                if (null != rawContactCursor) {
                    rawContactCursor.close();
                }
            }
        }
        if (null != cursor) {
            cursor.close();
        }
        return users;
    }


    /**
     * get column value
     *
     * @param raw_contact_id column raw_contact_id
     * @return column value
     */
    private String getName(int raw_contact_id) {
        Cursor cursor = mResolver.query(Data.CONTENT_URI, null,
                Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?",
                new String[]{raw_contact_id + "", StructuredName.CONTENT_ITEM_TYPE}, null);
        String str = null;
        if (null != cursor) {
            if (cursor.moveToNext())
                str = cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
            cursor.close();
        }
        return null != str ? str : "";
    }

    /**
     * get column value
     *
     * @param raw_contact_id column raw_contact_id
     * @return column value
     */
    private String getPhone(int raw_contact_id) {
        Cursor cursor = mResolver.query(Data.CONTENT_URI, null,
                Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?",
                new String[]{raw_contact_id + "", Phone.CONTENT_ITEM_TYPE}, null);
        String str = null;
        if (null != cursor) {
            if (cursor.moveToNext())
                str = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            cursor.close();
        }
        return null != str ? str : "";
    }

    /**
     * get email
     *
     * @param raw_contact_id column raw_contact_id
     * @return email
     */
    private String getEmail(int raw_contact_id) {
        Cursor cursor = mResolver.query(Data.CONTENT_URI, null,
                Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?",
                new String[]{raw_contact_id + "", Email.CONTENT_ITEM_TYPE}, null);
        String str = null;
        if (null != cursor) {
            if (cursor.moveToNext())
                str = cursor.getString(cursor.getColumnIndex(Email.ADDRESS));
            cursor.close();
        }

        return null != str ? str : "";
    }

    /**
     * get photo
     *
     * @param raw_contact_id column raw_contact_id
     * @return photo
     */
    private Bitmap getPhoto(int raw_contact_id) {
        Cursor cursor = mResolver.query(Data.CONTENT_URI, null, Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?",
                new String[]{raw_contact_id + "", Photo.CONTENT_ITEM_TYPE}, null);
        byte[] bs = null;
        if (null != cursor) {
            if (cursor.moveToNext())
                bs = cursor.getBlob(cursor.getColumnIndex(Photo.PHOTO));
            cursor.close();
        }
        if (null != bs && bs.length > 0) {
            return BitmapFactory.decodeByteArray(bs, 0, bs.length);
        } else {
            return BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_contact_head_icon);
        }
    }

    public boolean insert(User user) {
//        mResolver.applyBatch()
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        //
        ContentProviderOperation opt1 = ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build();
        operations.add(opt1);

        //name
        String name = user.getName();
        if (!name.equals("")) {
            ContentProviderOperation opt2 = ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(StructuredName.DISPLAY_NAME, name)
                    .build();
            operations.add(opt2);
        }
        //phone
        String phone = user.getPhone();
        if (!phone.equals("")) {
            ContentProviderOperation opt3 = ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, phone)
                    .build();
            operations.add(opt3);
        }

        //email
        String email = user.getEmail();
        if (!email.equals("")) {
            ContentProviderOperation opt4 = ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Email.ADDRESS, email)
                    .build();
            operations.add(opt4);
        }

        //photo
        Bitmap icon = user.getPhoto();
        if (null != icon) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bs = bos.toByteArray();
            ContentProviderOperation opt5 = ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
                    .withValue(Photo.PHOTO, bs)
                    .build();
            operations.add(opt5);
        }
        try {
            mResolver.applyBatch(ContactsContract.AUTHORITY, operations);
            return true;
        } catch (RemoteException e) {
            LogWrapper.e(TAG, "Remote");
            return false;
        } catch (OperationApplicationException e) {
            LogWrapper.e(TAG, "Operation Application Error");
            return false;
        }
    }

    /**
     * 删除联系人
     *
     * @param user 联系人
     * @return 是否成功
     */
    public boolean delete(User user) {
        int raw_contact_id = user.getRaw_contact_id();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation.newDelete(RawContacts.CONTENT_URI).
                withSelection(RawContacts._ID + "=?", new String[]{"" + raw_contact_id}).
                build());
        try {
            mResolver.applyBatch(ContactsContract.AUTHORITY, operations);
            return true;
        } catch (RemoteException e) {
            LogWrapper.e(TAG, "Remote");
            return false;
        } catch (OperationApplicationException e) {
            LogWrapper.e(TAG, "Operation Application Error");
            return false;
        }
    }

    /**
     * 更新表
     *
     * @param user 联系人实体
     */
    public void update(User user) {
        int raw_contact_id = user.getRaw_contact_id();
//        Bitmap photo = user.getPhoto();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        //name
        String name = user.getName();
        if (!name.equals("")) {
            ContentProviderOperation operation1 = ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?"
                            , new String[]{raw_contact_id + "", StructuredName.CONTENT_ITEM_TYPE})
                    .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(StructuredName.DISPLAY_NAME, name)
                    .build();
            operations.add(operation1);
        }

        //phone
        String phone = user.getPhone();
        if (!phone.equals("")) {
            ContentProviderOperation operation2 = ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?"
                            , new String[]{raw_contact_id + "", Phone.CONTENT_ITEM_TYPE})
                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, phone)
                    .build();
            operations.add(operation2);
        }


        //email
        String email = user.getEmail();
        if (!email.equals("")) {
            ContentProviderOperation operation3 = ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data.RAW_CONTACT_ID + "=? and " + Data.MIMETYPE + "=?"
                            , new String[]{raw_contact_id + "", Email.CONTENT_ITEM_TYPE})
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Email.ADDRESS, email)
                    .build();
            LogWrapper.e(TAG, email + "  " + Email.CONTENT_ITEM_TYPE);
            operations.add(operation3);
        }

        try {
            mResolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            LogWrapper.e(TAG, "Remote");
        } catch (OperationApplicationException e) {
            LogWrapper.e(TAG, "Operation Application Error");
        }
    }

    /**
     * 查询指定的联系人
     *
     * @param raw_contact_id 查询条件
     * @return 联系人
     */
    public User query(int raw_contact_id) {
        User user = new User();
        //从数据库中获取数据
        String name = getName(raw_contact_id);
        String phone = getPhone(raw_contact_id);
        String email = getEmail(raw_contact_id);
        Bitmap photo = getPhoto(raw_contact_id);
        //创建实体类
        user.setRaw_contact_id(raw_contact_id);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPhoto(photo);
        user.setLable(name.equals("") ? "#" : ChineseUtils.Chinese2Spell(name));
        return user;
    }


}

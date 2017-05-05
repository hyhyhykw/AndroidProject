package com.zx.contact.utils.constant;

/**
 * Created Time: 2017/1/22 9:26.
 *
 * @author HY
 */

public class Constant {
    public static final String ADD_USER = "add";
    public static final String EDIT_USER = "edit";

    public static final String SCAN_RESULT_KEY="result";

    public static final int DELETE_SUCCESS = 1;
    public static final int DELETE_FAILED = -1;
    public static final String DB_PATH_USERS = "/databases/users.db";
    /*database table users*/
    public static final String DB_TABLE_USERS = "users";
    /*database table sqlite_sequence*/
    public static final String DB_TABLE_SQLITE_SEQUENCE = "sqlite_sequence";
    /* table name _id*/
    public static final String TABLE_NAME_ID = "_id";
    /* table name name*/
    public static final String TABLE_NAME_NAME = "name";
    /* table name phone*/
    public static final String TABLE_NAME_PHONE = "phone";
    /* table name email*/
    public static final String TABLE_NAME_EMAIL = "email";
    /* table name photo*/
    public static final String TABLE_NAME_PHOTO = "photo";
    /* table name lable*/
    public static final String TABLE_NAME_LABLE = "lable";
    /* table name raw_contact_id*/
    public static final String TABLE_NAME_RAW_CONTACT_ID = "raw_contact_id";
    /*sql statement primary type*/
    public static final String TABLE_PRIMARY_TYPE = " integer primary key autoincrement not null,";
}

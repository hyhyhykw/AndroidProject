package com.zx.news2.utils.constant;

/**
 * Created by HY on 2016/12/23.
 * provide common constant
 *
 * @author HY
 */
public class Constant {
    //news list
    /**
     * news url base path
     */
    private static final String PATH_BASE = "http://118.244.212.82:9092/newsClient";
    /**
     * news list url base path
     */
    private static final String PATH_BASE_NEWS_LIST = PATH_BASE + "/news_list?ver=1&";
    /**
     * news list suffix
     */
    private static final String PATH_NEWS_LIST_SUFFIX = "&dir=1&nid=1&stamp=20140321&cnt=20";
    /**
     * miliary news path
     */
    public static final String PATH_NEWS_MILITARY = PATH_BASE_NEWS_LIST + "subid=1" + PATH_NEWS_LIST_SUFFIX;
    /**
     * society news path
     */
    public static final String PATH_NEWS_SOCIETY = PATH_BASE_NEWS_LIST + "subid=2" + PATH_NEWS_LIST_SUFFIX;
    /**
     * economy news path
     */
    public static final String PATH_NEWS_ECONOMY = PATH_BASE_NEWS_LIST + "subid=3" + PATH_NEWS_LIST_SUFFIX;
    /**
     * finance news path
     */
    public static final String PATH_NEWS_FINANCE = PATH_BASE_NEWS_LIST + "subid=4" + PATH_NEWS_LIST_SUFFIX;
    /**
     * science news path
     */
    public static final String PATH_NEWS_SCIENCE = PATH_BASE_NEWS_LIST + "subid=5" + PATH_NEWS_LIST_SUFFIX;

    /**
     * path regist
     */
    public static final String PATH_REGIST = PATH_BASE + "/user_register?ver=1&";

    /**
     * path login
     */
    public static final String PATH_LOGIN = PATH_BASE + "/user_login?ver=1&";

    /**
     * path user center
     */
    public static final String PATH_USER_CENTER = PATH_BASE + "/user_home?ver=1&";

    /**
     * path forget password
     */
    public static final String PATH_FORGET = PATH_BASE + "/user_forgetpass?ver=1&";
    /**
     * path comment count
     */
    public static final String PATH_COMNUM = PATH_BASE + "/cmt_num?ver=1&";
    /**
     * path comment detail
     */
    public static final String PATH_COMMENT_DETAIL = PATH_BASE + "/cmt_list?ver=1&";
    /**
     * path send comment
     */
    public static final String PATH_COMMENT_SEND = PATH_BASE + "/cmt_commit?ver=1&";
    /**
     * path send comment
     */
    public static final String PATH_UPLOAD = PATH_BASE + "/user_image";
    /**
     * path send comment
     */
    public static final String PATH_UPDATE = PATH_BASE + "/update?";


    //login
    /**
     * login success
     */
    public static final int RESULT_LOGIN_SUCCESS = 0;
    /**
     * full user
     */
    public static final int RESULT_PWD_ERROR = -1;
    /**
     * user exist
     */
    public static final int RESULT_IP_LIMIT = -2;
    /**
     * email exist
     */
    public static final int RESULT_LONG_DISTANCE = -3;


    //regist
    /**
     * regist success
     */
    public static final int RESULT_SUCCESS = 0;
    /**
     * full user
     */
    public static final int RESULT_USER_FILLED = -1;
    /**
     * user exist
     */
    public static final int RESULT_USER_EXIST = -2;
    /**
     * email exist
     */
    public static final int RESULT_EMAIL_EXIST = -3;


    //left menu position
    /**
     * left menu item news
     */
    public static final int ITEM_NEWS = 0;
    /**
     * left menu item favorite
     */
    public static final int ITEM_FAVORITE = 1;
    /**
     * left menu item local
     */
    public static final int ITEM_LOCAL = 2;
    /**
     * left menu item comment
     */
    public static final int ITEM_COMMENT = 3;
    /**
     * left menu item photo
     */
    public static final int ITEM_PHOTO = 4;

    // database
    /**
     * favorite news database
     */
    public static final String DB_PATH_NEWS = "/databases/news.db";
    /**
     * favorite table name
     */
    public static final String DB_TABLE_FAVORITE = "favorite";
    /**
     * table name _id
     */
    public static final String TABLE_NAME_ID = "_id";
    /**
     * table name summary
     */
    public static final String TABLE_NAME_SUMMARY = "summary";
    /**
     * table name icon
     */
    public static final String TABLE_NAME_ICON = "icon";
    /**
     * table name stamp
     */
    public static final String TABLE_NAME_STAMP = "stamp";
    /**
     * table name title
     */
    public static final String TABLE_NAME_TITLE = "title";
    /**
     * table name link
     */
    public static final String TABLE_NAME_LINK = "link";
    /**
     * table name nid
     */
    public static final String TABLE_NAME_NID = "nid";
    /**
     * sql statement primary type
     */
    public static final String TABLE_PRIMARY_TYPE = " integer primary key autoincrement not null,";


    //DateUtils
    /**
     * data format
     */
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";


    //sharedPreference
    /**
     * shared file name register
     */
    public static final String SHARED_NAME_LOGIN = "login";
    /**
     * shared file name user
     */
    public static final String SHARED_NAME_USER = "user";
    /**
     * shared file name isFirst
     */
    public static final String SHARED_NAME_ISFIRST = "isFirst";
    /**
     * shared file key isFirst
     */
    public static final String SHARED_KEY_ISFIRST = "isFirst";
    /**
     * shared file key token
     */
    public static final String SHARED_KEY_TOKEN = "token";
    /**
     * shared file key portrait
     */
    public static final String SHARED_KEY_PORTRAIT = "portrait";
    /**
     * shared file key uid
     */
    public static final String SHARED_KEY_UID = "uid";
    /**
     * shared file key pwd
     */
    public static final String SHARED_KEY_PWD = "pwd";
    /**
     * shared file key status
     */
    public static final String SHARED_KEY_STATUS = "status";
    /**
     * shared file key message
     */
    public static final String SHARED_KEY_MESSAGE = "message";
    /**
     * shared file key result
     */
    public static final String SHARED_KEY_RESULT = "result";
    /**
     * shared file key explain
     */
    public static final String SHARED_KEY_EXPLAIN = "explain";

    //map key
    /**
     * map key token
     */
    public static final String MAP_KEY_TOKEN = "token";
    /**
     * map key imei
     */
    public static final String MAP_KEY_IMEI = "imei";
    /**
     * map key uid
     */
    public static final String MAP_KEY_UID = "uid";
    /**
     * map key email
     */
    public static final String MAP_KEY_EMAIL = "email";
    /**
     * map key pwd
     */
    public static final String MAP_KEY_PWD = "pwd";
    /**
     * map key device
     */
    public static final String MAP_KEY_DEVICE = "device";

}

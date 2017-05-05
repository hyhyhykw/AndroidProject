package com.hy.filemanager.constant;

/**
 * here provide common constant
 * 
 * @author HY
 * 
 */
public class Constant {
	/**
	 * char set utf-8
	 */
	public static final String CHARSET_CODE_UTF_8 = "utf-8";
	/**
	 * char set gbk
	 */
	public static final String CHARSET_CODE_GBK = "gbk";
	/**
	 * char set gb2312
	 */
	public static final String CHARSET_CODE_GB2312 = "gb2312";
	/**
	 * char set utf-16
	 */
	public static final String CHARSET_CODE_UTF_16 = "utf-16";
	/**
	 * char set us-ascii
	 */
	public static final String CHARSET_CODE_US_ASCII = "us-ascii";
	/**
	 * char set iso-8858-1
	 */
	public static final String CHARSET_CODE_ISO_8859 = "iso-8858-1";
	/**
	 * code is checked
	 */
	public static final String CODE_IS_CHECKED = "isChecked";

	/**
	 * code is changed
	 */
	public static final String CODE_IS_CHANGED = "changeCode";

	/**
	 * sort mode has been checked
	 */
	public static final String SORT_MODE = "sortMode";

	/**
	 * text edit request code
	 */
	public static final int REQUEST_CODE_CHARSET_SELECT = 1;

	/**
	 * setting request code
	 */
	public static final int REQUEST_CODE_CHARSET_SET = 2;
	/**
	 * sort request code
	 */
	public static final int REQUEST_CODE_SORT = 3;

	/**
	 * char set utf-8 result code
	 */
	public static final int RESULT_CODE_UTF_8 = 0;
	/**
	 * char set gbk result code
	 */
	public static final int RESULT_CODE_GBK = 1;
	/**
	 * char set gb2312 result code
	 */
	public static final int RESULT_CODE_GB2312 = 2;
	/**
	 * char set utf-16 result code
	 */
	public static final int RESULT_CODE_UTF_16 = 3;
	/**
	 * char set us-ascii result code
	 */
	public static final int RESULT_CODE_US_ASCII = 4;
	/**
	 * char set iso-8859-1 result code
	 */
	public static final int RESULT_CODE_ISO_8859 = 5;

	/**
	 * sort by name
	 */
	public static final int SORT_BY_NAME = 0;
	/**
	 * sort by date
	 */
	public static final int SORT_BY_DATE = 1;
	/**
	 * sort by size
	 */
	public static final int SORT_BY_SIZE = 2;
	/**
	 * sort by type
	 */
	public static final int SORT_BY_TYPE = 3;
	/**
	 * inverted sort by name
	 */
	public static final int SORT_BY_NAME_INVERTED = 4;
	/**
	 * inverted sort by data
	 */
	public static final int SORT_BY_DATE_INVERTED = 5;
	/**
	 * inverted sort by size
	 */
	public static final int SORT_BY_SIZE_INVERTED = 6;

	// Operate
	/**
	 * move file
	 */
	public static final int OPERATE_MOVE_FILE = 0;
	/**
	 * copy file
	 */
	public static final int OPERATE_COPY_FILE = 1;
	/**
	 * delete file
	 */
	public static final int OPERATE_DELETE_FILE = 2;
	/**
	 * name file
	 */
	public static final int OPERATE_RENAME_FILE = 3;
	/**
	 * see file detail
	 */
	public static final int OPERATE_FILE_DETAIL = 4;

	public static final String PATH_SEPARATOR = "/";
}

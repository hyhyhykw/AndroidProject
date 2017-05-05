package com.hy.filemanager.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import com.hy.filemanager.constant.Constant;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * file helper class
 * 
 * @author Administrator
 * 
 */
public class FileUitls {
	private static final String TAG = FileUitls.class.getSimpleName();

	public static String getMimeType(File file) {
		String[][] MIME_TYPE = {
				// {suffix， MIME类型}
				{ ".3gp", "video/3gpp" },
				{ ".apk", "application/vnd.android.package-archive" },
				{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
				{ ".bin", "application/octet-stream" },
				{ ".bmp", "image/bmp" },
				{ ".class", "application/octet-stream" },
				{ ".doc", "application/msword" },
				{ ".exe", "application/octet-stream" },
				{ ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
				{ ".gz", "application/x-gzip" }, { ".htm", "text/html" },
				{ ".html", "text/html" },
				{ ".jar", "application/java-archive" },
				{ ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" },
				{ ".js", "application/x-javascript" },
				{ ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" },
				{ ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" },
				{ ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
				{ ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" },
				{ ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" },
				{ ".mpc", "application/vnd.mpohun.certificate" },
				{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
				{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
				{ ".mpga", "audio/mpeg" },
				{ ".msg", "application/vnd.ms-outlook" },
				{ ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
				{ ".png", "image/png" },
				{ ".pps", "application/vnd.ms-powerpoint" },
				{ ".ppt", "application/vnd.ms-powerpoint" },
				{ ".rar", "application/x-rar-compressed" },
				{ ".rmvb", "audio/x-pn-realaudio" },
				{ ".rtf", "application/rtf" }, { ".c", "text/plain" },
				{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
				{ ".h", "text/plain" }, { ".java", "text/plain" },
				{ ".log", "text/plain" }, { ".prop", "text/plain" },
				{ ".rc", "text/plain" }, { ".sh", "text/plain" },
				{ ".txt", "text/plain" }, { ".cfg", "text/plain" },
				{ ".ini", "text/plain" }, { ".xml", "text/plain" },
				{ ".tar", "application/x-tar" },
				{ ".tgz", "application/x-compressed" },
				{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
				{ ".wmv", "audio/x-ms-wmv" },
				{ ".wps", "application/vnd.ms-works" },
				{ ".z", "application/x-compress" },
				{ ".zip", "application/zip" }, };
		String fName = file.getName();
		String type = "*/*";
		int index = fName.lastIndexOf(".");

		if (index < 0)
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		String end = fName.substring(index).toLowerCase();
		if (end.equals(""))
			return type;
		for (int i = 0; i < MIME_TYPE.length; i++) {//
			if (end.equals(MIME_TYPE[i][0]))
				type = MIME_TYPE[i][1];
		}
		return type;
	}

	/**
	 * judge is it text file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isTextType(String suffix) {
		String[] str = { "txt", "c", "h", "cpp", "hpp", "java", "js", "css",
				"ini", "log", "cfg" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not html file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isHtmlType(String suffix) {
		String[] str = { "html", "xml", "xhtml" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not xls files
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isXlsType(String suffix) {
		String[] str = { "xls", "xlsx" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not ppt files
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isPptType(String suffix) {
		String[] str = { "ppt", "pptx", };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not pdf file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isPdfType(String suffix) {
		String[] str = { "pdf" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not doc files
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isDocType(String suffix) {
		String[] str = { "doc", "docx" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is or not database file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isDBType(String suffix) {
		String[] str = { "db" };
		for (String suff : str) {
			if (suff.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it video file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isVideoFile(String suffix) {
		String[] str = { "flv", "mkv", "avi", "mp4", "rm", "rmvb", "3gp",
				"flash" };
		for (String string : str) {
			if (suffix.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it audio file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isAudioFile(String suffix) {
		String[] str = { "mp3", "wav", "wma", "flac", "ape", "mid", "ogg" };
		for (String string : str) {
			if (suffix.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it image
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isImageFile(String suffix) {
		String[] str = { "bmp", "jpg", "gif", "png", "jpeg", "ico", "tiff",
				"xcf" };
		for (String string : str) {
			if (suffix.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it compressed files
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isZipFile(String suffix) {
		String[] str = { "zip", "rar", "gz", "tar", "7z" };
		for (String string : str) {
			if (suffix.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge is it android application package file
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean isProgramFile(String suffix) {
		String[] str = { "apk" };
		for (String string : str) {
			if (suffix.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * format file length
	 * 
	 * @param length
	 * @return
	 */
	public static String formatLength(long length) {
		DecimalFormat format = new DecimalFormat(".0");

		if (length >= 1024 && length < 1024 * 1024) {
			double len = length * 1.0 / 1024;
			String string = format.format(len);
			return string + "K";
		} else if (length >= 1024 * 1024 && length < 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024);
			String string = format.format(len);
			return string + "M";
		} else if (length >= 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024 * 1024);
			String string = format.format(len);
			return string + "G";
		}

		return length + "B";
	}

	/**
	 * sdcard all path
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getPaths(Context context) {
		StorageManager sManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		Class<?> cla = StorageManager.class;
		Method method = null;
		String[] paths = new String[2];
		try {
			method = cla.getDeclaredMethod("getVolumePaths");
			method.setAccessible(true);
			String array[] = (String[]) method.invoke(sManager);
			method.setAccessible(false);
			for (int i = 0; i < paths.length; i++) {
				if (array.length == i) {
					paths[i] = null;
				} else {
					paths[i] = array[i];
				}
			}
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "ERROR No Such Method");
		} catch (SecurityException e) {
			Log.e(TAG, "ERROR Security");
		} catch (IllegalAccessException e) {
			Log.e(TAG, "ERROR Illegal Access");
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "ERROR Illegal Argument");
		} catch (InvocationTargetException e) {
			Log.e(TAG, "ERROR Invocation Target");
		}
		return paths;
	}

	/**
	 * inner sdcard
	 * 
	 * @return
	 */

	public static String getInnerSdcard(Context context) {
		return getPaths(context)[0];
	}

	/**
	 * 
	 * @return
	 */
	public static String getOuterSdcard(Context context) {
		return getPaths(context)[1];
	}

	/**
	 * 
	 * @param innerPath
	 * @param outerPath
	 * @return
	 */
	public static long getInnerAndOuterTotal(Context context) {
		long outerTotal = 0;
		long innerTotal = 0;
		String outerPath = getOuterSdcard(context);
		String innerPath = getInnerSdcard(context);
		if (null != innerPath) {
			innerTotal = getTotal(innerPath);
		}
		if (null != outerPath) {
			outerTotal = getTotal(outerPath);
		}
		return innerTotal + outerTotal;
	}

	/**
	 * total memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getTotal(String path) {
		StatFs statfs = new StatFs(path);
		return statfs.getBlockCountLong() * statfs.getBlockSizeLong();
	}

	/**
	 * get unused memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getAvail(String path) {
		StatFs statfs = new StatFs(path);
		return statfs.getAvailableBlocksLong() * statfs.getBlockSizeLong();
	}

	/**
	 * get used memory
	 * 
	 * @param path
	 * @return
	 */
	public static long getUsed(String path) {
		return getTotal(path) - getAvail(path);
	}

	/**
	 * get used memory percent
	 * 
	 * @param path
	 * @return
	 */
	public static float getUsedPercent(String path) {
		float percent = 0;
		if (null != path) {
			percent = getUsed(path) * 1.0f / getTotal(path);
		}
		return percent;
	}

	/**
	 * get root path
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * read text file
	 * 
	 * @param file
	 * @return
	 */
	public static StringBuffer readText(File file, String charsetCode) {
		BufferedReader br = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		StringBuffer sbf = new StringBuffer("");
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, charsetCode);
			br = new BufferedReader(isr);
			String str = null;
			while (null != (str = br.readLine())) {
				sbf.append(str);
				sbf.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sbf;
	}

	/**
	 * get density
	 * 
	 * @return
	 */
	public static int getDensity(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int densityDpi = metric.densityDpi;
		return densityDpi;
	}

	/**
	 * delete file
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file) {
		boolean isSuccess = false;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				isSuccess = delFile(file2);
			}
		}
		isSuccess = file.delete();
		return isSuccess;
	}

	/**
	 * create new file or folder
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createFile(File file, boolean isFile) {
		boolean isSuccess = false;
		if (isFile) {
			try {
				isSuccess = file.createNewFile();
			} catch (IOException e) {
				Log.e(TAG, "IO ERROR");
				return false;
			}
		} else {
			isSuccess = file.mkdirs();
		}

		return isSuccess;
	}

	/**
	 * judge path is or not empty
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isEmpty(File path) {
		File[] files = path.listFiles();
		return null == files || files.length == 0;
	}

	/**
	 * rename file
	 * 
	 * @param file
	 * @param newName
	 * @return
	 */
	public static boolean rename(File file, String newName) {
		File dest = new File(file.getParent() + Constant.PATH_SEPARATOR
				+ newName);
		return file.renameTo(dest);
	}

	/**
	 * copy file
	 * 
	 * @param file
	 * @param targetPath
	 */
	public static void copyFileTo(File file, String targetPath) {
		File dest = new File(targetPath + Constant.PATH_SEPARATOR
				+ file.getName());
		if (file.isFile()) {
			byte[] bs = new byte[1024];
			int len = 0;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(dest);
				while ((len = fis.read(bs)) != -1) {
					fos.write(bs, 0, len);
				}
			} catch (FileNotFoundException e) {
				Log.e(TAG, "ERROR File Not Found");
			} catch (IOException e) {
				Log.e(TAG, "IO ERROR");
			} finally {
				try {
					fos.close();
					fis.close();
				} catch (IOException e) {
					Log.e(TAG, "IO ERROR");
				}
			}
		} else {
			if (isEmpty(file)) {
				dest.mkdirs();
			} else {
				File[] files = file.listFiles();
				for (File file2 : files) {
					String path = targetPath + Constant.PATH_SEPARATOR
							+ file2.getParentFile().getName();
					copyFileTo(file2, path);
				}
			}

		}
	}

}

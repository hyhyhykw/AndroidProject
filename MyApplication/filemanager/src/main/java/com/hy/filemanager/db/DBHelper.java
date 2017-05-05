package com.hy.filemanager.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * this class help open database
 * 
 * @author HY
 * 
 */
@SuppressWarnings("ALL")
public class DBHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	private File dbFile;
	private Cursor cursor;

	public DBHelper(File dbFile) {
		super();
		this.dbFile = dbFile;
	}

	@SuppressWarnings("StringBufferMayBeStringBuilder")
	public List<String> readTableList() {
		List<String> tableList = new ArrayList<>();
		Runtime runtime = Runtime.getRuntime();
		OutputStream os = null;
		InputStream is;
		BufferedReader br;
		try {
			Process proc = runtime.exec("sqlite3 " + dbFile.getAbsolutePath()
					+ " \n");
			os = proc.getOutputStream();
			is = proc.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			int readLines = getReadLines();
			os.write(".tables \n".getBytes());
			StringBuffer sbf = new StringBuffer();
			String readIn;
			int num = 0;
			while (true) {
				readIn = br.readLine();
				sbf.append(readIn);
				Log.e(TAG, readIn);
				num++;
				if (num == readLines) {
					break;
				}
			}
			readIn = sbf.toString();
			os.write(".quit \n".getBytes());
			String[] tables = readIn.split("\\s+");
			Collections.addAll(tableList, tables);
		} catch (IOException e) {
			Log.e(TAG, "IO ERROR");
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e(TAG, "IO ERROR");
				}
			}
		}
		Collections.sort(tableList);
		return tableList;
	}

	/**
	 * get number of database tables
	 * 
	 * @return get read statements lines
	 */
	private int getReadLines() {
		Runtime runtime = Runtime.getRuntime();
		OutputStream os;
		InputStream is;
		BufferedReader br;
		try {
			Process proc = runtime.exec("sqlite3 " + dbFile.getAbsolutePath()
					+ " \n");
			os = proc.getOutputStream();
			is = proc.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			os.write(".dbinfo \n".getBytes());
			int infoNum = 0;
			String dbInfo = "";
			while (infoNum < 17) {
				dbInfo = br.readLine();
				infoNum++;
			}
			String numString = dbInfo.split(":\\s+", 2)[1];
			int tableNum = Integer.parseInt(numString) - 1;
			os.write(".quit \n".getBytes());
			return tableNum % 4 == 0 ? tableNum / 4 : tableNum / 4 + 1;
		} catch (IOException e) {
			Log.e(TAG, "IO ERROR");
		}
		return 0;
	}

	/**
	 * 
	 * @return column number
	 */
	public int getColumnNum() {
		return cursor.getColumnCount();
	}

	/**
	 * 
	 * @return get row number
	 */
	public int getRowNum() {
		return cursor.getCount();
	}

	/**
	 * 
	 * @param table table name
	 */
	public void openDBFile(String table) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		cursor = db.query(table, null, null, null, null, null, null);
	}

	/**
	 * 
	 * @return table names
	 */
	public String[] getColunmnNames() {
		return cursor.getColumnNames();
	}

	/**
	 * 
	 */
	public void close() {
		cursor.close();
	}

	/**
	 * 
	 * @return column list
	 */
	public List<List<String>> getColumns() {
		List<List<String>> columns = new ArrayList<List<String>>();
		String[] columnNames = getColunmnNames();
		for (String columnName : columnNames) {
			columns.add(getColumn(columnName));
		}
		return columns;
	}

	/**
	 * 
	 * @param columnName
	 * @return
	 */
	private List<String> getColumn(String columnName) {
		List<String> column = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String col = cursor.getString(cursor.getColumnIndex(columnName));
			column.add(col);
		}
		return column;
	}

}

package com.hy.filemanager.entity;

import java.io.File;

/**
 * file detail information
 * 
 * @author HY
 * 
 */
public class FileDetailInfo {

	/**
	 * file icon
	 */
	private int fileIcon;
	/**
	 * file
	 */
	private File file;
	/**
	 * file name
	 */
	private String fileName;
	/**
	 * file suffix
	 */
	private String suffix;

	/**
	 * judge is or not empty
	 */
	private boolean isEmpty;

	public FileDetailInfo() {
	}

	public FileDetailInfo(int fileIcon, File file, String fileName,
			String suffix) {
		this.fileIcon = fileIcon;
		this.file = file;
		this.fileName = fileName;
		this.suffix = suffix;
	}

	public int getFileIcon() {
		return fileIcon;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean isEmpty() {
		setEmpty();
		return isEmpty;
	}

	private void setEmpty() {
		File[] files;
		if (file.isDirectory()) {
			files = file.listFiles();
		} else {
			files = null;
		}
		int length = null != files ? files.length : 0;
		isEmpty = length == 0;
	}

}

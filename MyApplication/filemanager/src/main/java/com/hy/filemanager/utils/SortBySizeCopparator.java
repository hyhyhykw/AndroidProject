package com.hy.filemanager.utils;

import java.io.File;
import java.util.Comparator;
import com.hy.filemanager.entity.FileDetailInfo;

public class SortBySizeCopparator implements Comparator<FileDetailInfo> {
	@Override
	public int compare(FileDetailInfo lhs, FileDetailInfo rhs) {
		File lFile = lhs.getFile();
		File rFile = rhs.getFile();
		return Long.valueOf(lFile.length()).compareTo(
				Long.valueOf(rFile.length()));
	}
}

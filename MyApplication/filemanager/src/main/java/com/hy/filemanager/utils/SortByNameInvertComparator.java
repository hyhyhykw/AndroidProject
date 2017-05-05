package com.hy.filemanager.utils;

import java.util.Comparator;

import com.hy.filemanager.entity.FileDetailInfo;

public class SortByNameInvertComparator implements Comparator<FileDetailInfo> {

	@Override
	public int compare(FileDetailInfo lhs, FileDetailInfo rhs) {
		return -lhs.getFileName().compareTo(rhs.getFileName());
	}
}

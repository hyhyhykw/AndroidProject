package com.hy.filemanager.utils;

import java.util.Comparator;
import com.hy.filemanager.entity.FileDetailInfo;

/**
 * sort file by file type
 * 
 * @author HY
 * 
 */
public class SortByTypeComparator implements Comparator<FileDetailInfo> {
	@Override
	public int compare(FileDetailInfo lhs, FileDetailInfo rhs) {
		String lSuffix = lhs.getSuffix();
		String rSuffix = rhs.getSuffix();
		return lSuffix.compareToIgnoreCase(rSuffix);
	}
}

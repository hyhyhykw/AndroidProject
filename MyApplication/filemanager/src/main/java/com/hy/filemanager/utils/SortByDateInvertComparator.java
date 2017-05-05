package com.hy.filemanager.utils;

import java.util.Comparator;
import java.util.Date;

import com.hy.filemanager.entity.FileDetailInfo;

public class SortByDateInvertComparator implements Comparator<FileDetailInfo> {

	@Override
	public int compare(FileDetailInfo lhs, FileDetailInfo rhs) {
		String lDataStr = DateUitls.getDate(lhs.getFile());
		Date lDate = DateUitls.str2Date(lDataStr);

		String rDataStr = DateUitls.getDate(rhs.getFile());
		Date rDate = DateUitls.str2Date(rDataStr);

		if (null != rDate && null != lDate)
			return -lDate.compareTo(rDate);
		else
			return 0;
	}

}

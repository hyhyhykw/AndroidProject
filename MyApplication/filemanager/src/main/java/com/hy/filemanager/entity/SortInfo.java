package com.hy.filemanager.entity;

public class SortInfo {

	private String sortMode;

	private boolean isChecked;

	public SortInfo(String sortMode) {
		super();
		this.sortMode = sortMode;
	}

	public String getSortMode() {
		return sortMode;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}

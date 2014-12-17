package com.fowlcorp.homebank4android.gui;

public class DrawerItem {

	private String itemName;
	private int imgResID = 0;
	private boolean isOverview;
	private boolean isHeader;

	public DrawerItem(String itemName, int imgResID, boolean isOverview, boolean isHeader) {
		this.itemName = itemName;
		this.imgResID = imgResID;
		this.isOverview = isOverview;
		this.isHeader = isHeader;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getImgResID() {
		return imgResID;
	}

	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}

	public boolean isOverview() {
		return isOverview;
	}

	public void setOverview(boolean isOverview) {
		this.isOverview = isOverview;
	}

	public boolean isHeader() {
		return isHeader;
	}

	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	
	

	

}
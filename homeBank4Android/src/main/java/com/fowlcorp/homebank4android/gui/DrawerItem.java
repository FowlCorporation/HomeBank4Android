/*
 * Copyright © 2014-2015 Fowl Corporation
 *
 * This file is part of HomeBank4Android.
 *
 * HomeBank4Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HomeBank4Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HomeBank4Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fowlcorp.homebank4android.gui;

public class DrawerItem {

	private String itemName;
	private int imgResID = 0;
	private boolean isOverview;
	private boolean isHeader;
	private int key = -1;

	public DrawerItem(String itemName, int imgResID, int key) {
		this.itemName = itemName;
		this.imgResID = imgResID;
		this.key = key;
		isOverview = false;
		isHeader = false;
	}

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

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}


}
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

package com.fowlcorp.homebank4android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Axel
 */
public class Category extends AccPayCatTagAbstract implements Serializable {

	private Category parent;
	private ArrayList<Category> subCategories;            //Key of the sub categories
	private int flags;                                    //Flags of the category (usage to be determined)

	/**
	 * Create the category with minimal informations
	 *
	 * @param key  Integer identifying uniquely the category
	 * @param name Name of the category
	 */
	public Category(int key, String name) {
		super(key, name);
		setParent(null);
		setSubCategories(new ArrayList<Category>());
	}

	/**
	 * Create the category with flags informations
	 *
	 * @param key   Integer identifying uniquely the category
	 * @param flags Flags of the category
	 * @param name  Name of the category
	 */
	public Category(int key, String name, int flags) {
		super(key, name);
		this.setFlags(flags);
		setSubCategories(new ArrayList<Category>());
	}

	/**
	 * Add subcategory to this category
	 *
	 * @param category The Category object to add to the list of children
	 */
	public void addSubCategory(Category category) {
		getSubCategories().add(category);
	}

	// Getters and Setters

	/**
	 * Return flags information
	 *
	 * @return int
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * Return subcategories
	 *
	 * @return ArrayList of sub categories
	 */
	public ArrayList<Category> getSubcategories() {
		return getSubCategories();
	}

	@Override
	public String toString() {
		return "Category : " + getKey() + ", name : " + getName() + (getSubCategories().isEmpty() ? "" : ", " + getSubCategories().size() + " sub categorie(s)");
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public ArrayList<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(ArrayList<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}
}

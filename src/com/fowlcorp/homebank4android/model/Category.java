/**
 * 
 */
package com.fowlcorp.homebank4android.model;

import java.util.ArrayList;

/**
 * @author Axel
 *
 */
public class Category {
	
	private int key;									//Key identifying the category
	private ArrayList<Category> subCategories;			//Key of the parent category
	private int flags;									//Flags of the category (usage to be determined)
	private String name;								//Name of the category

	/**
	 * Create the category with minimal informations
	 */
	public Category(int key, String name) {
		this.key = key;
		this.name = name;
		subCategories = new ArrayList<Category>();
	}
	
	/**
	 * Create the category with flags informations
	 */
	public Category(int key, int flags, String name) {
		this.key = key;
		this.flags = flags;
		this.name = name;
		subCategories = new ArrayList<Category>();
	}
	
	public void addSubCategory(Category category) {
		subCategories.add(category);
	}
}

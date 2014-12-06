/**
 * 
 */
package com.fowlcorp.homebank4android.model;

import java.util.ArrayList;

/**
 * @author Axel
 *
 */
public class Category extends AccPayCatTagAbstract {

	private ArrayList<Category> subCategories;			//Key of the sub categories
	private int flags;									//Flags of the category (usage to be determined)

	/**
	 * Create the category with minimal informations
	 * @param key	Integer identifying uniquely the category
	 * @param name	Name of the category
	 */
	public Category(int key, String name) {
		super(key, name);
		subCategories = new ArrayList<Category>();
	}

	/**
	 * Create the category with flags informations
	 * @param key	Integer identifying uniquely the category
	 * @param flags	Flags of the category 
	 * @param name	Name of the category
	 */
	public Category(int key, String name, int flags) {
		super(key, name);
		this.flags = flags;
		subCategories = new ArrayList<Category>();
	}

	/**
	 * Add subcategory to this category
	 * @param category	The Category object to add to the list of children
	 */
	public void addSubCategory(Category category) {
		subCategories.add(category);
	}

	// Getters and Setters
	/**
	 * Return flags information
	 * @return int
	 */
	public int getFlags() {
		return flags;
	}
	
	/**
	 * Return subcategories
	 * @return ArrayList of sub categories
	 */
	public ArrayList<Category> getSubcategories() {
		return subCategories;
	}
}

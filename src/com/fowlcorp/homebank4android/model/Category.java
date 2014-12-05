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
						
	private ArrayList<Category> subCategories;			//Key of the parent category
	private int flags;									//Flags of the category (usage to be determined)

	/**
	 * Create the category with minimal informations
	 * @param key
	 * @param Name
	 */
	public Category(int key, String name) {
		super(key, name);
		subCategories = new ArrayList<Category>();
	}
	
	/**
	 * Create the category with flags informations
	 * @param key
	 * @param flags
	 * @param Name
	 */
	public Category(int key, int flags, String name) {
		super(key, name);
		this.flags = flags;
		subCategories = new ArrayList<Category>();
	}
	
	public void addSubCategory(Category category) {
		subCategories.add(category);
	}
}

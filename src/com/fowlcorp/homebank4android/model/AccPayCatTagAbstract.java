/**
 * 
 */
package com.fowlcorp.homebank4android.model;

/**
 * @author Axel
 *
 */
public abstract class AccPayCatTagAbstract {

	private int key;		//Key identifying the object
	private String name;	//Textual name of the object (name of the account, category, payee...)
	
	/**
	 * Create the object with minimal informations
	 * @param key	Integer identifying uniquely the object
	 * @param name	Name of the object
	 */
	public AccPayCatTagAbstract(int key, String name) {
		this.key = key;
		this.name = name;
	}
	
	
	// Getters and Setters
	/**
	 * Return the key identifier of the object
	 * @return int
	 */
	public int getKey() {
		return key;
	}
		
	/**
	 * Return the name information of the object
	 * @return String
	 */
	public String getName() {
		return name;
	}

}

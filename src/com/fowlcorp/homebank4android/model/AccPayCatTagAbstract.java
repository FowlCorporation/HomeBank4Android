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
	 * 
	 */
	public AccPayCatTagAbstract(int key, String Name) {
		
	}
	
	public int getKey() {
		return key;
	}
	
	public String getName() {
		return name;
	}

}

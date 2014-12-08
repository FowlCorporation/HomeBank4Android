/**
 * 
 */
package com.fowlcorp.homebank4android.model;

/**
 * @author Axel
 *
 */
public class Payee extends AccPayCatTagAbstract {

	/**
	 * @param key
	 * @param Name
	 */
	public Payee(int key, String Name) {
		super(key, Name);
	}

	@Override
	public String toString() {
		return "Payee : " + getKey() +", name : " + getName();
	}
}

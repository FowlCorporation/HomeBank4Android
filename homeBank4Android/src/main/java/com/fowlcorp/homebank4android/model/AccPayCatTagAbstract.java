/**
 *	Copyright (C) 2014 Fowl Corporation
 *
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
		this.setName(name);
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


    public void setName(String name) {
        this.name = name;
    }
}
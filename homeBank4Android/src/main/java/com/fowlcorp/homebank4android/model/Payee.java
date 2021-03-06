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

/**
 * @author Axel
 */
public class Payee extends AccPayCatTagAbstract implements Serializable {

	/**
	 * @param key
	 * @param Name
	 */
	public Payee(int key, String Name) {
		super(key, Name);
	}

	@Override
	public String toString() {
		return "Payee : " + getKey() + ", name : " + getName();
	}
}

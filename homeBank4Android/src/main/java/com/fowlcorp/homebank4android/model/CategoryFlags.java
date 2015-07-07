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

/**
 * Represents the Category types
 * @author Cédric
 */
public class CategoryFlags {
    // A category type is income if the flags field int has a 1 at the first (i.e. INCOME) bit
	public static final int SUB = 0;
	public static final int INCOME = 1;
	public static final int CUSTOM = 2;
	public static final int BUDGET = 3;
	public static final int FORCED = 4;
}

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
public class Account extends AccPayCatTagAbstract {

	private double initBalance;
	private String bankName, accountNumber; // accountNumber may contains letters ...
	
	public Account(int key, String name) {
		super(key, name);
	}
	
	public Account(int key, String name, double initBalance) {
		super(key, name);
		this.initBalance = initBalance;
	}

	public double getInitBalance() {
		return initBalance;
	}

	public void setInitBalance(double initBalance) {
		this.initBalance = initBalance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Override
	public String toString() {
		return "Account : "+getKey() +", " + getName() + (getBankName() == null ? "" : ", bank name : " + getBankName())  + (getAccountNumber() == null ? "" : ", account number : " + getAccountNumber());
	}
}

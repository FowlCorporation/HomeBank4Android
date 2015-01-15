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
 * @author Axel
 *
 */
public class Account extends AccPayCatTagAbstract {

	private double initBalance;
    private double todayAccountBalance;
    private double bankAccountBalance;
    private double futureAccountBalance;
	private String bankName, accountNumber;
    private boolean modified;
    private int type;
	
	public Account(int key, String name) {
		super(key, name);
        setModified(true);
        bankName = "No bank name"; // to avoid NPE
	}
	
	public Account(int key, String name, double initBalance) {
		super(key, name);
		this.initBalance = initBalance;
        setModified(true);
        bankName = "No bank name"; // to avoid NPE
	}

	public double getInitBalance() {
		return initBalance;
	}

	public void setInitBalance(double initBalance) {
		this.initBalance = initBalance;
        modified = false;
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
		return "Account : "+getKey() +", " + getName() + (getBankName() == null ? "" : ", bank name : " + getBankName())  + (getAccountNumber() == null ? "" : ", account number : " + getAccountNumber()) + ", initial balance : " + getInitBalance();
	}

    public double getTodayAccountBalance() {
        return todayAccountBalance;
    }

    public void setTodayAccountBalance(double todayAccountBalance) {
        this.todayAccountBalance = todayAccountBalance;
    }

    public double getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(double bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public double getFutureAccountBalance() {
        return futureAccountBalance;
    }

    public void setFutureAccountBalance(double futureAccountBalance) {
        this.futureAccountBalance = futureAccountBalance;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

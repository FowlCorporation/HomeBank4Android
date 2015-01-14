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

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Model {
	
	private HashMap<Integer,Payee> payees;
	private HashMap<Integer,Category> categories;
	private HashMap<Integer,Account> accounts;
    private HashMap<Integer,Tag> tags;
	private HashMap<Integer,List<Operation>> operations; // one List for each account
    private int selectedAccount;
	private double grandTotalBank;
	private double grandTotalToday;
	private double grandTotalFuture;

	public Model() {

	}

	public void updateOperationAccountBalance(int accountKey) {
		Account selectedAcc = accounts.get(accountKey);
		if(selectedAcc.isModified() == false) { // balances already up to date
			return;
		}
		Collections.sort(operations.get(accountKey));
		double todayBalance = selectedAcc.getInitBalance();
		double bankBalance = todayBalance, futureBalance = todayBalance;
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(System.currentTimeMillis());
		//System.err.println(accounts.get(selectedAccount));
		for(Operation op : operations.get(accountKey)) {
			if(op.getAccount().getKey() == accountKey) {
				if(op.getDate().compareTo(today) <= 0) { // today or past operation
					if(!op.isRemind()) {
						todayBalance += op.getAmount();
					}
					if(op.isReconciled()) {
						bankBalance += op.getAmount();
					}
				}
				if(!op.isRemind()) {
					futureBalance += op.getAmount();
				}
				op.setBalanceAccount(futureBalance);
			}
		}
		selectedAcc.setTodayAccountBalance(todayBalance);
		selectedAcc.setBankAccountBalance(bankBalance);
		selectedAcc.setFutureAccountBalance(futureBalance);
		//System.err.println("Today : " + todayBalance);
		//System.err.println("Bank : " + bankBalance);
		//System.err.println("Future : " + futureBalance);
		selectedAcc.setModified(false);

		updateGrandTotal();
	}
    public void updateOperationAccountBalance() {
        updateOperationAccountBalance(selectedAccount);
    }

	public void updateGrandTotal() {
		setGrandTotalBank(0);
		setGrandTotalToday(0);
		setGrandTotalFuture(0);
		for(Account acc : accounts.values()) {
			setGrandTotalBank(getGrandTotalBank() + acc.getBankAccountBalance());
			setGrandTotalFuture(getGrandTotalFuture() + acc.getFutureAccountBalance());
			setGrandTotalToday(getGrandTotalToday() + acc.getTodayAccountBalance());
		}

		System.err.println("Today : " + getGrandTotalBank());
		System.err.println("Bank : " + getGrandTotalFuture());
		System.err.println("Future : " + getGrandTotalToday());
	}

	public HashMap<Integer, Payee> getPayees() {
		return payees;
	}

	public void setPayees(HashMap<Integer, Payee> payees) {
		this.payees = payees;
	}

	public HashMap<Integer, Category> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<Integer, Category> categories) {
		this.categories = categories;
	}

	public HashMap<Integer, Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(HashMap<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public List<Operation> getOperations(Account acc) {
		return operations.get(acc.getKey());
	}

	public void setOperations(List<Operation> operations, Account acc) {
		this.operations.put(acc.getKey(), operations);
        acc.setModified(true);
	}

    public HashMap<Integer, List<Operation>> getOperations() {
        return operations;
    }

    public void setOperations(HashMap<Integer, List<Operation>> operations) {
        this.operations = operations;
    }

    public int getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(int selectedAccount) {
        System.err.println("New operation selected");
        this.selectedAccount = selectedAccount;
    }

    public HashMap<Integer, Tag> getTags() {
        return tags;
    }

    public void setTags(HashMap<Integer, Tag> tags) {
        this.tags = tags;
    }

	public double getGrandTotalBank() {
		return grandTotalBank;
	}

	public void setGrandTotalBank(double grandTotalBank) {
		this.grandTotalBank = grandTotalBank;
	}

	public double getGrandTotalToday() {
		return grandTotalToday;
	}

	public void setGrandTotalToday(double grandTotalToday) {
		this.grandTotalToday = grandTotalToday;
	}

	public double getGrandTotalFuture() {
		return grandTotalFuture;
	}

	public void setGrandTotalFuture(double grandTotalFuture) {
		this.grandTotalFuture = grandTotalFuture;
	}
}

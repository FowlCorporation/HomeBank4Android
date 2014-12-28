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
	
	public Model() {
		
	}

    public void updateOperationAccountBalance() {
        Account selectedAcc = accounts.get(selectedAccount);
        if(selectedAcc.isModified() == false) { // balances already up to date
            return;
        }
        Collections.sort(operations.get(selectedAccount));
        double todayBalance = selectedAcc.getInitBalance();
        double bankBalance = todayBalance, futureBalance = todayBalance;
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        //System.err.println(accounts.get(selectedAccount));
        for(Operation op : operations.get(selectedAccount)) {
            if(op.getAccount().getKey() == selectedAccount) {
                if(op.getDate().compareTo(today) <= 0) { // today or past operation
                    todayBalance += op.getAmount();
                    if(op.getFlag() == 3 || op.getFlag() == 1) {
                        bankBalance += op.getAmount();
                    }
                }
                futureBalance += op.getAmount();
                op.setBalanceAccount(futureBalance);
            }
        }
        selectedAcc.setTodayAccountBalance(todayBalance);
        selectedAcc.setBankAccountBalance(bankBalance);
        selectedAcc.setFutureAccountBalance(futureBalance);
        System.err.println("Today : " + todayBalance);
        System.err.println("Bank : " + bankBalance);
        System.err.println("Future : " + futureBalance);
        selectedAcc.setModified(false);
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
        this.selectedAccount = selectedAccount;
    }

    public HashMap<Integer, Tag> getTags() {
        return tags;
    }

    public void setTags(HashMap<Integer, Tag> tags) {
        this.tags = tags;
    }

}

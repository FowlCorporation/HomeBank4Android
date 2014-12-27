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
    private double selectedTodayAccountBalance;
    private double selectedBankAccountBalance;
    private double selectedFutureAccountBalance;
	
	public Model() {
		
	}

    public void updateOperationAccountBalance() {
        Collections.sort(operations.get(selectedAccount));
        double todayBalance = accounts.get(selectedAccount).getInitBalance();
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
        setSelectedTodayAccountBalance(todayBalance);
        setSelectedBankAccountBalance(bankBalance);
        setSelectedFutureAccountBalance(futureBalance);
        System.err.println("Today : " + todayBalance);
        System.err.println("Bank : " + bankBalance);
        System.err.println("Future : " + futureBalance);
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

    public double getSelectedTodayAccountBalance() {
        return selectedTodayAccountBalance;
    }

    public void setSelectedTodayAccountBalance(double selectedTodayAccountBalance) {
        this.selectedTodayAccountBalance = selectedTodayAccountBalance;
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

    public double getSelectedBankAccountBalance() {
        return selectedBankAccountBalance;
    }

    public void setSelectedBankAccountBalance(double selectedBankAccountBalance) {
        this.selectedBankAccountBalance = selectedBankAccountBalance;
    }

    public double getSelectedFutureAccountBalance() {
        return selectedFutureAccountBalance;
    }

    public void setSelectedFutureAccountBalance(double selectedFutureAccountBalance) {
        this.selectedFutureAccountBalance = selectedFutureAccountBalance;
    }
}

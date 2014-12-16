package com.fowlcorp.homebank4android.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Model {
	
	private HashMap<Integer,Payee> payees;
	private HashMap<Integer,Category> categories;
	private HashMap<Integer,Account> accounts;
	private HashMap<Integer,List<Operation>> operations; // one List for each account
    private int selectedAccount;
    private double selectedAccountBalance;
	
	public Model() {
		
	}

    public void updateOperationAccountBalance() {
        Collections.sort(operations.get(selectedAccount));
        double balance = accounts.get(selectedAccount).getInitBalance();
        //System.err.println(accounts.get(selectedAccount));
        for(Operation op : operations.get(selectedAccount)) {
            if(op.getAccount().getKey() == selectedAccount) {
                //System.err.println(op);
                balance += op.getAmount();
                op.setBalanceAccount(balance);
            }
        }
        setSelectedAccountBalance(balance);
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

    public double getSelectedAccountBalance() {
        return selectedAccountBalance;
    }

    public void setSelectedAccountBalance(double selectedAccountBalance) {
        this.selectedAccountBalance = selectedAccountBalance;
    }

    public void setSelectedAccount(int selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
}

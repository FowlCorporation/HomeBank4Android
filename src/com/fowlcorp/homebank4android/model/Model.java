package com.fowlcorp.homebank4android.model;

import java.util.HashMap;
import java.util.List;

public class Model {
	
	private HashMap<Integer,Payee> payees;
	private HashMap<Integer,Category> categories;
	private HashMap<Integer,Account> accounts;
	private List<Operation> operations;
	
	public Model() {
		
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

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	
}

package com.fowlcorp.homebank4android.model;

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

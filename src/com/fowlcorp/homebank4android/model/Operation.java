package com.fowlcorp.homebank4android.model;

import java.util.GregorianCalendar;

public class Operation {

	private GregorianCalendar date;
	private int xmlDate, flag;
	private double amount;
	private Account account;
	private String wording, tags; // wording <=> memo
	private Category category;
	private Payee payee;
	
	
	
	
	public Operation(int xmlDate, double amount, Account account, Category category, Payee payee) {
		this.xmlDate = xmlDate;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.payee = payee;
		computeGregorianDate();
	}
	
	private void computeGregorianDate() {
		date = new GregorianCalendar();
		date.set(1, 0, 1);
		date.add(GregorianCalendar.DATE, xmlDate + 1);
		//DEBUG
//		System.out.println("Day : " + calendar.get(GregorianCalendar.DATE) + " " + calendar.get(GregorianCalendar.MONTH) + " " + calendar.get(GregorianCalendar.YEAR));
	}
	
	public String verboseDate() {
		return date.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (date.get(GregorianCalendar.MONTH)+1) + "/" + date.get(GregorianCalendar.YEAR);
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	public int getXmlDate() {
		return xmlDate;
	}
	public void setXmlDate(int xmlDate) {
		this.xmlDate = xmlDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getWording() {
		return wording;
	}
	public void setWording(String wording) {
		this.wording = wording;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Payee getPayee() {
		return payee;
	}
	public void setPayee(Payee payee) {
		this.payee = payee;
	}
	
	
}

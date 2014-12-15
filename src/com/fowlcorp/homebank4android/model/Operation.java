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

import java.util.GregorianCalendar;

/**
 * @author Cédric
 *
 */
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
	
	@Override
	public String toString() {
		return "Operation : " + verboseDate() + ", amount : " + getAmount() + (getPayee()==null ? "" : ", payee : " + getPayee().getName()) + (getCategory()==null ? "" : ", category : " + getCategory().getName());
		
	}
	
}

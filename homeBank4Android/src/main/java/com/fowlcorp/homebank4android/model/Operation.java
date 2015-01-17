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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * @author Cédric
 *
 */
public class Operation implements Comparable, Serializable {

	private GregorianCalendar date;
	private int xmlDate;
	private int flag;
	private int payMode;
	private double balanceAccount;
	private double amount;
	private Account account;
	private String wording, tags; // wording <=> memo
	private Category category;
	private Payee payee;
	private ArrayList<Couple> splits;
	
	
	public Operation(int xmlDate, double amount, Account account, Category category, Payee payee) {
		this.xmlDate = xmlDate;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.payee = payee;
		computeGregorianDate();
	}

	/**
	 * Transform xml date (example 735431) in a GregorianCalendar
	 */
	private void computeGregorianDate() {
		date = new GregorianCalendar();
		date.set(1, 0, 1);
		date.add(GregorianCalendar.DATE, xmlDate + 1);
		//DEBUG
//		System.out.println("Day : " + calendar.get(GregorianCalendar.DATE) + " " + calendar.get(GregorianCalendar.MONTH) + " " + calendar.get(GregorianCalendar.YEAR));
	}

	public boolean isReconciled() {
//        #define OF_VALID	 (1<<0)
//        #define OF_INCOME	 (1<<1)
//        #define OF_AUTO	 (1<<2)  //tmp flag scheduled
//        #define OF_ADDED	 (1<<3)  //tmp flag
//        #define OF_CHANGED (1<<4)  //tmp flag
//        #define OF_REMIND	 (1<<5)
//        #define OF_CHEQ2	 (1<<6)
//        #define OF_LIMIT	 (1<<7)	//scheduled
//        #define OF_SPLIT	 (1<<8)
		return flag % 2 == 1;
	}
	
	public String verboseDate() {
		return date.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (date.get(GregorianCalendar.MONTH)+1) + "/" + date.get(GregorianCalendar.YEAR);
	}
	
	public GregorianCalendar getDate() {
		return date;
	}

    /**
     * Set the GregorianDate and update the xmlDate
     * @param date
     */
	public void setDate(GregorianCalendar date) {
		this.date = date;
        GregorianCalendar ref = new GregorianCalendar();
        ref.set(1, 0, 1);
        xmlDate = (int)( (date.getTimeInMillis() - ref.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}
	public int getXmlDate() {
		return xmlDate;
	}

    /**
     * Set the xmlDate and update the GregorianDate
     * @param xmlDate
     */
	public void setXmlDate(int xmlDate) {
		this.xmlDate = xmlDate;
		computeGregorianDate();
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
        if(isSplit()) {
            splits = new ArrayList<>();
        }
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
	public double getBalanceAccount() {
		return balanceAccount;
	}
	public void setBalanceAccount(double balanceAccount) {
		this.balanceAccount = balanceAccount;
	}

	public int getPayMode() {
		return payMode;
	}

	public void setPayMode(int payMode) {
		this.payMode = payMode;
	}

	@Override
	public String toString() {
		return "Operation : " + verboseDate() + ", amount : " + getAmount() + (getPayee()==null ? "" : ", payee : " + getPayee().getName())
                + (getCategory()== null ? "" : ", category : " + getCategory().getName())
                + (splits == null ? "" : ", splitted : " + splits.size() + " operations");
		
	}

	public int compareTo(Object o) {
		if(o instanceof  Operation) {
			Operation operation = (Operation) o;
			return this.date.compareTo(operation.getDate());
		} else {
			throw new ClassCastException();
		}
	}

	public boolean isSplit() {
		return (flag&0x100) != 0;
	}
	
	public boolean isRemind() {
		return (flag&0x20) != 0;
	}

	public ArrayList<Couple> getSplits() {
		return splits;
	}

	public void setSplits(ArrayList<Couple> splits) {
		this.splits = splits;
	}
}

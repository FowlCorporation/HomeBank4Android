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

package com.fowlcorp.homebank4android.utils;

import android.content.Context;

import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.AccountType;
import com.fowlcorp.homebank4android.model.Category;
import com.fowlcorp.homebank4android.model.Couple;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.model.Payee;
import com.fowlcorp.homebank4android.model.Tag;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Axel, Cédric
 *
 */
public class DataParser {

	Document dom;
	Context context;

	
	public DataParser(Context context, File file) throws ParserConfigurationException, SAXException, IOException {
		this.context = context;
		parseXmlFile(file);
	}
	
	public DataParser(Context context) {
		this.context = context;
		parseXmlFile();
	}
	
	private void parseXmlFile() {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			// TODO: point to the right file
			dom = db.parse(context.getResources().getAssets().open("anonymized.xhb"));

		} catch (ParserConfigurationException | IOException | SAXException pce) {
			pce.printStackTrace();
		}
	}



	
	/**
	 * Parse a DropBox tracked file
	 * @param file
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private void parseXmlFile(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			// TODO: point to the right file
			FileInputStream in = new FileInputStream(file);
			dom = db.parse(in);
	}

	/**
	 * Retrieve the payees from the xml file
	 * @return the payees
	 */
	public HashMap<Integer,Payee> parsePayees() {
		HashMap<Integer,Payee> payees = new HashMap<>();
		Element docEle = dom.getDocumentElement();
		Element el;
		NodeList nl;
		nl = docEle.getElementsByTagName("pay");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Payee p = new Payee(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"));
				// DEBUG
				//System.err.println(p.toString());
				payees.put(p.getKey(), p);
			}
		}
		return payees;
	}

	/**
	 * Retrieve the categories from the xml file
	 * @return the categories
	 */
	public HashMap<Integer,Category> parseCategories() {
		Element docEle = dom.getDocumentElement();
		Element el;
		NodeList nl;
		HashMap<Integer,Category> categories = new HashMap<>();
		nl = docEle.getElementsByTagName("cat");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Category c = new Category(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"));
				categories.put(c.getKey(), c);
				if(el.hasAttribute("parent")) {
					int parent = Integer.parseInt(el.getAttribute("parent"));
					categories.get(parent).addSubCategory(c);
					c.setParent(categories.get(parent));
				}
				// DEBUG
				//System.err.println(c.toString());
			}
		}
		return categories;
	}

	/**
	 * Retrieve the accounts from the xml file
	 * @return teh accounts
	 */
	public HashMap<Integer,Account> parseAccounts() {
		Element docEle = dom.getDocumentElement();
		Element el;
		NodeList nl;
		HashMap<Integer,Account> accounts = new HashMap<>();
		nl = docEle.getElementsByTagName("account");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Account a = new Account(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"), Double.parseDouble(el.getAttribute("initial")));
				if(el.hasAttribute("bankname")) {
					a.setBankName(el.getAttribute("bankname"));
				}
				if(el.hasAttribute("number")) {
					a.setAccountNumber(el.getAttribute("number"));
				}
                if(el.hasAttribute("type")) {
                    a.setType(Integer.parseInt(el.getAttribute("type")));
                } else {
                    a.setType(AccountType.NONE);
                }
				accounts.put(a.getKey(), a);
				// DEBUG
				//System.err.println(a.toString());
			}
		}
		return accounts;
	}

	/**
	 * Retrieve the tags from the xml file
	 * @return the tags
	 */
	public HashMap<Integer,Tag> parseTags() {
		Element docEle = dom.getDocumentElement();
		Element el;
		NodeList nl;
		HashMap<Integer,Tag> tags = new HashMap<>();
		nl = docEle.getElementsByTagName("tag");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Tag t = new Tag(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"));
				tags.put(t.getKey(), t);
				// DEBUG
				//System.err.println(t.toString());
			}
		}
		return tags;
	}

	/**
	 * Retrieve the operations from the xml file and make links with the accounts, payees and accounts
	 * @param accounts the accounts to link with operations
	 * @param categories the categories to link with operations
	 * @param payees the payees to link with operations
	 * @return the operations
	 */
	public HashMap<Integer,List<Operation>> parseOperations(HashMap<Integer,Account> accounts, HashMap<Integer,Category> categories, HashMap<Integer,Payee> payees) {
		HashMap<Integer,List<Operation>> operations = new HashMap<>();
		Element docEle = dom.getDocumentElement();
		Element el;
		NodeList nl;
		nl = docEle.getElementsByTagName("ope");
		int accountKey;

        // init
        for(Integer key : accounts.keySet()) {
            operations.put(key, new ArrayList<Operation>()); // new Operation List for each account
        }

		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Category c = null;
				Payee p = null;
				if(el.hasAttribute("category")) { // missing for splitted operations
					c = categories.get(Integer.parseInt(el.getAttribute("category")));
				}
				if(el.hasAttribute("payee")) { // may miss
					p = payees.get(Integer.parseInt(el.getAttribute("payee")));
				}
				accountKey = Integer.parseInt(el.getAttribute("account"));
				Operation op = new Operation(Integer.parseInt(el.getAttribute("date")),
						Double.parseDouble(el.getAttribute("amount")),
						accounts.get(accountKey),
						c,
						p);
				if(el.hasAttribute("wording")) { // may miss
					op.setWording(el.getAttribute("wording"));
				}

				if(el.hasAttribute("flags")) { // may miss
					op.setFlag(Integer.parseInt(el.getAttribute("flags")));
				}

				if(el.hasAttribute("paymode")) { // may miss
					op.setPayMode(Integer.parseInt(el.getAttribute("paymode")));
				}

				if(op.isSplit()) { // handle split operations
					String separator = "\\|\\|";
					String[] samt = el.getAttribute("samt").split(separator);
					String[] scat = el.getAttribute("scat").split(separator);
					//DEBUG
                    //System.out.println("taile samt : "+samt.length);
					//System.out.println("taile scat : "+scat.length);
					for(int j = 0; j < samt.length; j++) {
						op.getSplits().add(new Couple(Double.parseDouble(samt[j]), categories.get(Integer.parseInt(scat[j]))));
					}
				}

				operations.get(accountKey).add(op);
				// DEBUG
				//System.err.println(op.toString());
			}
		}
		return operations;
	}

}

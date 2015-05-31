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
import com.fowlcorp.homebank4android.model.Properties;
import com.fowlcorp.homebank4android.model.Template;
import com.fowlcorp.homebank4android.model.Triplet;
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
 */
public class DataParser {

	private Document dom;
	private Context context;


	public DataParser(Context context, File file) throws ParserConfigurationException, SAXException, IOException {
		this.context = context;
		parseXmlFile(file);
	}

	public DataParser(Context context) {
		this.context = context;
	}

	public void parseXmlFile(String fileName) {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			// TODO: point to the right file
			dom = db.parse(context.getResources().getAssets().open(fileName));

		} catch (ParserConfigurationException | IOException | SAXException pce) {
			pce.printStackTrace();
		}
	}


	/**
	 * Parse a file
	 *
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
	 *
	 * @return the payees
	 */
	public HashMap<Integer, Payee> parsePayees() {
		HashMap<Integer, Payee> payees = new HashMap<>();
		Element docEle = dom.getDocumentElement();
		Element element;
		NodeList nodeList;
		nodeList = docEle.getElementsByTagName("pay");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				Payee p = new Payee(Integer.parseInt(element.getAttribute("key")), element.getAttribute("name"));
				// DEBUG
				//System.err.println(p.toString());
				payees.put(p.getKey(), p);
			}
		}
		return payees;
	}

	/**
	 * Retrieve the categories from the xml file
	 *
	 * @return the categories
	 */
	public HashMap<Integer, Category> parseCategories() {
		Element docEle = dom.getDocumentElement();
		Element element;
		NodeList nodeList;
		HashMap<Integer, Category> categories = new HashMap<>();
		nodeList = docEle.getElementsByTagName("cat");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				Category c = new Category(Integer.parseInt(element.getAttribute("key")), element.getAttribute("name"));
				categories.put(c.getKey(), c);
				if (element.hasAttribute("parent")) {
					int parent = Integer.parseInt(element.getAttribute("parent"));
					categories.get(parent).addSubCategory(c);
					c.setParent(categories.get(parent));
				}
                if (element.hasAttribute("flags")) {
                    c.setFlags(Integer.parseInt(element.getAttribute("flags")));
                }
				// DEBUG
				//System.err.println(c.toString());
			}
		}
		return categories;
	}

	/**
	 * Retrieve the accounts from the xml file
	 *
	 * @return teh accounts
	 */
	public HashMap<Integer, Account> parseAccounts() {
		Element docEle = dom.getDocumentElement();
		Element element;
		NodeList nodeList;
		HashMap<Integer, Account> accounts = new HashMap<>();
		nodeList = docEle.getElementsByTagName("account");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				Account a = new Account(Integer.parseInt(element.getAttribute("key")), element.getAttribute("name"), Double.parseDouble(element.getAttribute("initial")));
				if (element.hasAttribute("bankname")) {
					a.setBankName(element.getAttribute("bankname"));
				}
				if (element.hasAttribute("number")) {
					a.setAccountNumber(element.getAttribute("number"));
				}
				if (element.hasAttribute("minimum")) {
					a.setMinimumBalance(Double.parseDouble(element.getAttribute("minimum")));
				}
                if (element.hasAttribute("flags")) {
                    a.setFlags(Integer.parseInt(element.getAttribute("flags")));
                }
				if (element.hasAttribute("type")) {
					a.setType(Integer.parseInt(element.getAttribute("type")));
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
	 *
	 * @return the tags
	 */
	public HashMap<Integer, Tag> parseTags() {
		Element docEle = dom.getDocumentElement();
		Element element;
		NodeList nodeList;
		HashMap<Integer, Tag> tags = new HashMap<>();
		nodeList = docEle.getElementsByTagName("tag");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				Tag t = new Tag(Integer.parseInt(element.getAttribute("key")), element.getAttribute("name"));
				tags.put(t.getKey(), t);
				// DEBUG
				//System.err.println(t.toString());
			}
		}
		return tags;
	}

    /**
     * Retrieve the owner info from the xml file
     *
     * @return the tags
     */
    public Properties parseProperties(HashMap<Integer, Category> categories) {
        Element docEle = dom.getDocumentElement();
        Element element;
        NodeList nodeList;
        Properties properties = new Properties();
        nodeList = docEle.getElementsByTagName("owner");
        if (nodeList != null && nodeList.getLength() == 1) {
            element = (Element) nodeList.item(0);
            if (element.hasAttribute("title")) {
                properties.setTitle(element.getAttribute("title"));
            }
            if (element.hasAttribute("auto_smode")) {
                properties.setAutoSmode(Integer.parseInt(element.getAttribute("auto_smode")));
            }
            if (element.hasAttribute("auto_weekday")) {
                properties.setAutoWeekday(Integer.parseInt(element.getAttribute("auto_weekday")));
            }
            if (element.hasAttribute("auto_nbdays")) {
                properties.setAutoNbdays(Integer.parseInt(element.getAttribute("auto_nbdays")));
            }
            if (element.hasAttribute("car_category")) {
                properties.setCarCategory(categories.get(Integer.parseInt(element.getAttribute("car_category"))));
            }
        }
        return properties;
    }

	/**
	 * Retrieve the operations from the xml file and make links with the accounts, payees and accounts
	 *
	 * @param accounts   the accounts to link with operations
	 * @param categories the categories to link with operations
	 * @param payees     the payees to link with operations
	 * @return the operations
	 */
	public HashMap<Integer, List<Operation>> parseOperations(HashMap<Integer, Account> accounts, HashMap<Integer, Category> categories, HashMap<Integer, Payee> payees) {
		HashMap<Integer, List<Operation>> operations = new HashMap<>();
		Element docEle = dom.getDocumentElement();
		Element element;
		NodeList nodeList;
		nodeList = docEle.getElementsByTagName("ope");
		int accountKey;

		// init
		for (Integer key : accounts.keySet()) {
			operations.put(key, new ArrayList<Operation>()); // new Operation List for each account
		}

		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				Category c = null;
				Payee p = null;
				if (element.hasAttribute("category")) { // missing for splitted operations
					c = categories.get(Integer.parseInt(element.getAttribute("category")));
				}
				if (element.hasAttribute("payee")) { // may miss
					p = payees.get(Integer.parseInt(element.getAttribute("payee")));
				}
				accountKey = Integer.parseInt(element.getAttribute("account"));
				Operation op = new Operation(Integer.parseInt(element.getAttribute("date")),
						Double.parseDouble(element.getAttribute("amount")),
						accounts.get(accountKey),
						c,
						p);
				if (element.hasAttribute("wording")) { // may miss
					op.setWording(element.getAttribute("wording"));
				}

				if (element.hasAttribute("tags")) { // may miss
					op.setTags(element.getAttribute("tags"));
				}

				if (element.hasAttribute("flags")) { // may miss
					op.setFlags(Integer.parseInt(element.getAttribute("flags")));
				}

				if (element.hasAttribute("paymode")) { // may miss
					op.setPayMode(Integer.parseInt(element.getAttribute("paymode")));
				}

                if (element.hasAttribute("st")) { // may miss
                    op.setState(Integer.parseInt(element.getAttribute("st")));
                }

				if (op.isSplit()) { // handle split operations
					String separator = "\\|\\|";
					String[] samt = element.getAttribute("samt").split(separator);
					String[] scat = element.getAttribute("scat").split(separator,-1);
					String[] smem = element.getAttribute("smem").split(separator, -1);
					//DEBUG
					//System.out.println("taile samt : "+samt.length);
					//System.out.println("taile scat : "+scat.length);
					// System.err.println("Length : "+scat.length +", smem length "+smem.length);
					String tmpCat;
					for (int j = 0; j < samt.length; j++) {
						tmpCat = scat[j]; // sub category could miss
						op.getSplits().add(new Triplet(Double.parseDouble(samt[j]), smem[j], tmpCat.length() > 0 ? categories.get(Integer.parseInt(tmpCat)) : new Category(0,"")));
					}
				}

				operations.get(accountKey).add(op);
				// DEBUG
				//System.err.println(op.toString());
			}
		}
		return operations;
	}

    /**
     * Retrieve the template/scheduled op from the xml file and make links with the accounts, payees and accounts
     *
     * @param accounts   the accounts to link with template/scheduled op
     * @param categories the categories to link with template/scheduled op
     * @param payees     the payees to link with template/scheduled op
     * @return the operations
     */
    public List<Template> parseTemplate(HashMap<Integer, Account> accounts, HashMap<Integer, Category> categories, HashMap<Integer, Payee> payees) {
        List<Template> templates = new ArrayList<>();

        Element docEle = dom.getDocumentElement();
        Element element;
        NodeList nodeList;
        nodeList = docEle.getElementsByTagName("ope");

        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                element = (Element) nodeList.item(i);
                Template template = new Template();
                template.setAmount(Double.parseDouble(element.getAttribute("amount")));
                if (element.hasAttribute("account")) {
                    template.setAccount(accounts.get(Integer.parseInt(element.getAttribute("account"))));
                }
                if (element.hasAttribute("paymode")) {
                    template.setPaymode(Integer.parseInt(element.getAttribute("paymode")));
                }
                if (element.hasAttribute("flags")) {
                    template.setFlags(Integer.parseInt(element.getAttribute("flags")));
                }
                if (element.hasAttribute("payee")) {
                    template.setPayee(payees.get(Integer.parseInt(element.getAttribute("payee"))));
                }
                if (element.hasAttribute("category")) {
                    template.setCategory(categories.get(Integer.parseInt(element.getAttribute("category"))));
                }
                if (element.hasAttribute("wording")) {
                    template.setWording(element.getAttribute("wording"));
                }
                if (element.hasAttribute("nextdate")) {
                    template.setNextDateXml(Integer.parseInt(element.getAttribute("nextdate")));
                }
                if (element.hasAttribute("every")) {
                    template.setEvery(Integer.parseInt(element.getAttribute("every")));
                }
                if (element.hasAttribute("unit")) {
                    template.setUnit(Integer.parseInt(element.getAttribute("unit")));
                }
                if (element.hasAttribute("limit")) {
                    template.setLimit(Integer.parseInt(element.getAttribute("limit")));
                }
                if (element.hasAttribute("weekend")) {
                    template.setWeekend(Integer.parseInt(element.getAttribute("weekend")));
                }
            }
        }
        return templates;
    }

}

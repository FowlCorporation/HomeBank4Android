/**
 *
 */
package com.fowlcorp.homebank4android.utils;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Category;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.model.Payee;

/**
 * @author Axel
 *
 */
public class DataParser {

	HashMap<Integer,Payee> payees;
	HashMap<Integer,Category> categories;
	HashMap<Integer,Account> accounts;
	List<Operation> operations;
	Document dom;
	Context context;

	public DataParser(Context context) {
	/*	payees = new HashMap<>();
		categories = new HashMap<>();
		accounts = new HashMap<>();
		operations = new ArrayList<>();
		this.context = context;*/
	}

	public void runExample() {
		parseXmlFile();
		parseDocument();
	}

	private void parseXmlFile() {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(context.getResources().getAssets().open("anonymized.xhb"));

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocument() {
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		Element el;
		//get a nodelist of <payee> elements
		NodeList nl = docEle.getElementsByTagName("pay");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Payee p = new Payee(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"));
				// DEBUG
				System.err.println(p.toString());
				payees.put((Integer)p.getKey(), p); 
			}
		}
		
		nl = docEle.getElementsByTagName("cat");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				Category c = new Category(Integer.parseInt(el.getAttribute("key")), el.getAttribute("name"));
				categories.put((Integer) c.getKey(), c);
				if(el.hasAttribute("parent")) {
					int parent = Integer.parseInt(el.getAttribute("parent"));
					categories.get(parent).addSubCategory(c);
				}
				// DEBUG
				System.err.println(c.toString());
			}
		}
		
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
				accounts.put((Integer) a.getKey(), a);
				// DEBUG
				System.err.println(a.toString());
			}
		}
		
		nl = docEle.getElementsByTagName("ope");
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
				Operation op = new Operation(Integer.parseInt(el.getAttribute("date")),
						Double.parseDouble(el.getAttribute("amount")),
						accounts.get(Integer.parseInt(el.getAttribute("account"))),
						c,
						p);
				operations.add(op);
				// DEBUG
				System.err.println(op.toString());
			}
		}
	}

}

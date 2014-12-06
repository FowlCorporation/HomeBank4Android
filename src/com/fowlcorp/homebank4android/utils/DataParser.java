/**
 *
 */
package com.fowlcorp.homebank4android.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fowlcorp.homebank4android.model.Category;
import com.fowlcorp.homebank4android.model.Payee;

/**
 * @author Axel
 *
 */
public class DataParser {

	HashMap<Integer,Payee> payees;
	HashMap<Integer,Category> categories;
	Document dom;
	Context context;

	public DataParser(Context context) {
		payees = new HashMap<Integer, Payee>();
		categories = new HashMap<Integer, Category>();
		this.context = context;
	}

	public void runExample() {
		if(!new File("anonymized.xhb").exists()) {
			System.err.println("Error !!!!!!!!!!!!!!!!!!!!!!!!");
		} else {
			parseXmlFile();
	
			parseDocument();
		}

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
				System.err.println("Payee : "+p.getKey() +", " + p.getName());
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
				System.err.println("Category : "+c.getKey() +", " + c.getName() + (el.hasAttribute("parent") ? ", parent : " + el.getAttribute("parent") : ""));
			}
		}
	}

}

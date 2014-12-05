/**
 * 
 */
package com.fowlcorp.homebank4android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.fowlcorp.homebank4android.model.Category;

import android.util.Xml;

/**
 * @author Axel
 *
 */
public class DataLoader {

	private XmlPullParser parser;
	private static final String ns = null;		// We don't use namespaces

	/**
	 * Constructor
	 */
	public DataLoader() {
		parser = Xml.newPullParser();
	}

	public List parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
	}

	private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List entries = new ArrayList();

		parser.require(XmlPullParser.START_TAG, ns, "feed");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("homebank")) {
//				entries.add(readEntry(parser));
			} else {
				skip(parser);
			}
		}  
		return entries;
	}

	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	private void readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "entry");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				readTitle(parser);
			} else if (name.equals("summary")) {
				readSummary(parser);
			} else if (name.equals("cat")) {
				readCategory(parser);
			} else {
				skip(parser);
			}
		}
	}

	// Processes title tags in the feed.
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}
	
	// Processes link tags in the feed.
	private Category readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
		
		//Vars
		Category category;
		int key = -1;
		int parent = -1;
		int flags = -1;
		
		
		parser.require(XmlPullParser.START_TAG, ns, "cat");
		String tag = parser.getName();
		try {
			key = Integer.parseInt(parser.getAttributeValue(null, "key"));
			parent = Integer.parseInt(parser.getAttributeValue(null, "parent"));
			flags = Integer.parseInt(parser.getAttributeValue(null, "flags"));
		} catch(NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}
		String name = parser.getAttributeValue(null, "name");
		parser.require(XmlPullParser.END_TAG, ns, "cat");
		if (flags != -1) {
			category = new Category(key, flags, name);
		} else {
			category = new Category(key, name);
		}
		if (parent != -1) {
			
		}
		return category;
	}

	// Processes summary tags in the feed.
	private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "summary");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "summary");
		return summary;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}

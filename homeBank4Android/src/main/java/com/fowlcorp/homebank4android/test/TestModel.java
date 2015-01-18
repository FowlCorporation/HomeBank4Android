package com.fowlcorp.homebank4android.test;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.DataParser;

/**
 * Created by Ced on 18/01/2015.
 */


public class TestModel extends InstrumentationTestCase {

	public void test() throws Exception {
		Context context = this.getInstrumentation().getTargetContext().getApplicationContext();

		Model model = new Model(); //create the datamodel
		DataParser dp = new DataParser(context);
		dp.parseXmlFile("HomeBankTest.xhb");

		model.setAccounts(dp.parseAccounts());
		model.setCategories((dp.parseCategories()));
		model.setPayees(dp.parsePayees());
		model.setTags(dp.parseTags());
		model.setOperations(dp.parseOperations(model.getAccounts(), model.getCategories(), model.getPayees()));
		assertEquals(5, 5);
	}
}

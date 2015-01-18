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

package com.fowlcorp.homebank4android;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.utils.DataParser;
import com.fowlcorp.homebank4android.utils.Round;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

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

		for (Account acc : model.getAccounts().values()) {
			model.updateOperationAccountBalance(acc.getKey());
		}
		/*
		<account key="1" pos="1" type="4" name="CreditCardAccount" number="AccountName#1" bankname="Bank#1" initial="123.45" minimum="-100" />
		<account key="2" pos="2" type="2" name="Wallet" initial="22.329999999999998" minimum="0" />
		<account key="3" pos="3" type="3" name="Life insurance" number="Number#2" bankname="Bank#1" initial="0" minimum="0" />
		<account key="4" pos="4" type="1" name="BankAccount" bankname="Bank#2" initial="500" minimum="0" cheque1="5" cheque2="50" />
		<account key="5" pos="5" name="EmptyAccount" initial="-10" minimum="-20" />
		<account key="6" pos="6" name="EmptyAccount#2" initial="-10" minimum="-5" />
		 */

		//TODO: all the tests

		assertEquals(6, model.getAccounts().size());

		// Account 1
		Account creditCardAccount = model.getAccounts().get(1);
		assertEquals("CreditCardAccount", creditCardAccount.getName());
		assertEquals("AccountName#1", creditCardAccount.getAccountNumber());
		assertEquals("Bank#1", creditCardAccount.getBankName());
		assertEquals(123.45, creditCardAccount.getInitBalance());
		assertEquals(-100.0, creditCardAccount.getMinimumBalance());

		// Operation 1
		List<Operation> operations = model.getOperations().get(1);
		Operation op = operations.get(0);
		assertEquals(10, operations.size());
		assertEquals(735520, op.getXmlDate());
		assertEquals("14/10/2014", op.verboseDate());

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2014,9,15);
		op.setDate(cal);
		assertEquals("15/10/2014", op.verboseDate());
		assertEquals(735520 + 1, op.getXmlDate());

		cal.set(2014,9,13);
		op.setDate(cal);
		assertEquals("13/10/2014", op.verboseDate());
		assertEquals(735520-1, op.getXmlDate());

		assertEquals("Gas Station", op.getPayee().getName());
		assertEquals("Gasoline", op.getCategory().getName());

		// Balances
		assertEquals(1127.45, creditCardAccount.getBankAccountBalance());
		assertEquals(957.45, creditCardAccount.getTodayAccountBalance());
		assertEquals(932.45, creditCardAccount.getFutureAccountBalance());



		// Account 2
		Account wallet = model.getAccounts().get(2);
		assertEquals("Wallet", wallet.getName());
		assertEquals(22.33, Round.roundAmount(wallet.getInitBalance()));
		assertEquals(0.0, wallet.getMinimumBalance());


		// Grand total
		assertEquals(16924.3, Round.roundAmount(model.getGrandTotalBank()));
		assertEquals(16754.3, Round.roundAmount(model.getGrandTotalToday()));
		assertEquals(16729.3, Round.roundAmount(model.getGrandTotalFuture()));

	}
}

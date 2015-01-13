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

package com.fowlcorp.homebank4android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class DetailedCardActivity extends ActionBarActivity {
	private EditText date;
	private AutoCompleteTextView category;
	private AutoCompleteTextView payee;
	private TextView wording;
	private TextView balance;
	private EditText amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bdl = this.getIntent().getExtras();

		setContentView(R.layout.activity_detailed_card);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_toolbar); 
		
		setSupportActionBar(toolbar); 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Get ids
		date = (EditText) findViewById(R.id.detailedCardDate);
		category = (AutoCompleteTextView) findViewById(R.id.detailedCardCategory);
		payee = (AutoCompleteTextView) findViewById(R.id.detailedCardPayee);
		amount = (EditText) findViewById(R.id.detailedCardAmount);
		wording = (EditText) findViewById(R.id.detailedCardWording);
		
		try {
			date.setText((String) bdl.getString("Date"), TextView.BufferType.SPANNABLE);
		} catch (Exception e) {
		}
		try {
			category.setText(bdl.getString("Category"));
		} catch (Exception e) {
		}
		try {
			payee.setText(bdl.getString("Payee"));
		} catch (Exception e) {
		}
		try {
			wording.setText(bdl.getString("Wording"));
		} catch (Exception e) {
		}
		try {
			amount.setText(bdl.getString("Amount"));
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_card, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else {
			onBackPressed();
			return true;
		}
		//return super.onOptionsItemSelected(item);
	}
}
package com.fowlcorp.homebank4android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailedCardActivity extends Activity {
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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
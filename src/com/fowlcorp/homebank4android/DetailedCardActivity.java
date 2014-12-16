package com.fowlcorp.homebank4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailedCardActivity extends Activity {
	private EditText date;
	private Spinner category;
	private Spinner payee;
	private TextView wording;
	private TextView balance;
	private TextView amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bdl = this.getIntent().getExtras();
//		int day = bdl.getInt("Day");
//		int month = bdl.getInt("Month");
//		int year = bdl.getInt("Year");
//		System.out.println(day+"/"+month+"/"+year);		

		//Get ids
		date = (EditText) findViewById(R.id.detailedCardDate);
		category = (Spinner) findViewById(R.id.detailedCardCategory);
		payee = (Spinner) findViewById(R.id.detailedCardPayee);
		
		try {
			date.setText(bdl.getString("Date"));
		} catch (Exception e) {
		}
//		try {
//			category.setText(context.getString(R.string.cardLayout_category)+" "+operation.getCategory().getName());
//		} catch (Exception e) {
//		}
//		try {
//			tier.setText(context.getString(R.string.cardLayout_tier)+" "+operation.getPayee().getName());
//		} catch (Exception e) {
//		}
		
		setContentView(R.layout.activity_detailed_card);
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
		}
		return super.onOptionsItemSelected(item);
	}
}

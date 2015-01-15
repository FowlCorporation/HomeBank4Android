/*
 * Copyright © 2015 Fowl Corporation
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fowlcorp.homebank4android.model.PayMode;

public class DetailedCardActivity extends ActionBarActivity {
	private EditText date;
	private AutoCompleteTextView category;
	private AutoCompleteTextView payee;
	private TextView wording;
	private TextView balance;
	private EditText amount;
    private ImageView image;

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
        image = (ImageView) findViewById(R.id.detailedCardIcon);
		
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
        int typeAccount = bdl.getInt("Type", -1);
        try {
            switch (typeAccount) {
                case PayMode.CREDIT_CARD:
                    image.setImageResource(R.drawable.card);
                    break;
                case PayMode.DEBIT_CARD:
                    image.setImageResource(R.drawable.card);
                    break;
                case PayMode.CASH:
                    image.setImageResource(R.drawable.espece);
                    break;
                case PayMode.TRANSFERT:
                    image.setImageResource(R.drawable.transfert);
                    break;
                case PayMode.ELECTRONIC_PAYMENT:
                    image.setImageResource(R.drawable.nfc);
                    break;
                case PayMode.CHEQUE:
                    image.setImageResource(R.drawable.cheque);
                    break;
                default:
                    image.setImageDrawable(null);
                    break;
            }
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
        if (id == R.id.action_settings) { //the settings button is selected
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent); //start the activity of preferences
            return true;
        }
        if (id == R.id.action_about) { //the settings button is selected
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent); //start the activity of preferences
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
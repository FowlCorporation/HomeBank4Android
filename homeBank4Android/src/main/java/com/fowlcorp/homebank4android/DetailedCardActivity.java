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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fowlcorp.homebank4android.gui.MyRadioGroup;
import com.fowlcorp.homebank4android.model.Couple;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.PayMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailedCardActivity extends ActionBarActivity {

	private TextView balance;
	private MyRadioGroup detailedCardStateGroup;
	private LinearLayout layout;
	private EditText date;
	private AutoCompleteTextView category;
	private AutoCompleteTextView payee;
	private EditText amount;
	private EditText wording;
	private EditText info;
	private RadioButton none, reconciled, remind, cleared;
    private LinearLayout linearSplit;
    private TextView categoryView;
    private TableLayout splitTable;
    private GridLayout gridLayout;
    private boolean isSplit = false;
	private Calendar myCalendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);

		Bundle bdl = this.getIntent().getExtras();

		setContentView(R.layout.activity_detailed_card);
	   /* TransitionInflater transitionInflater = TransitionInflater.from(this);
        final Transition transition = transitionInflater.inflateTransition(R.transition.cardview_fade);

        this.getWindow().setExitTransition(transition);*/

		Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_toolbar);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		layout = (LinearLayout) findViewById(R.id.detailedCardLayout_root);
		date = (EditText) findViewById(R.id.detailedCardDate);
		category = (AutoCompleteTextView) findViewById(R.id.detailedCardCategory);
		payee = (AutoCompleteTextView) findViewById(R.id.detailedCardPayee);
		amount = (EditText) findViewById(R.id.detailedCardAmount);
		info = (EditText) findViewById(R.id.detailedCardInfo);
		wording = (EditText) findViewById(R.id.detailedCardWording);
		none = (RadioButton) findViewById(R.id.detailedCardState_none);
		reconciled = (RadioButton) findViewById(R.id.detailedCardState_reconciled);
		remind = (RadioButton) findViewById(R.id.detailedCardState_remind);
		cleared = (RadioButton) findViewById(R.id.detailedCardState_cleared);
        linearSplit = (LinearLayout) findViewById(R.id.detailed_split_linear);
        categoryView = (TextView) findViewById(R.id.detailedCardCategoryLabel);
        splitTable = (TableLayout) findViewById(R.id.detailed_split_table);
        gridLayout = (GridLayout) findViewById(R.id.GridLayout1);

		ImageView image = (ImageView) findViewById(R.id.detailedCardIcon);
		date.setInputType(InputType.TYPE_NULL);
		myCalendar = Calendar.getInstance();
        myCalendar.clear();

		final DatePickerDialog.OnDateSetListener dateDiag = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}

		};

		date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(DetailedCardActivity.this, dateDiag, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					new DatePickerDialog(DetailedCardActivity.this, dateDiag, myCalendar
							.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
							myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				}
			}
		});


		try {
			date.setText(bdl.getString("Date"), TextView.BufferType.SPANNABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
            isSplit = bdl.getBoolean("Split");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!isSplit){
            try {
                Log.d("debug", "is not Split");
                linearSplit.setVisibility(View.GONE);
                category.setText(bdl.getString("Category"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<Couple> coupleArrayList = (ArrayList<Couple>) bdl.getSerializable("Couple");
            int key = bdl.getInt("Key");
            int position = bdl.getInt("Position");
            //ArrayList<Couple> coupleArrayList = model.getOperations(model.getAccounts().get(key)).get(position).getSplits();
            linearSplit.setVisibility(View.VISIBLE);
            category.setVisibility(View.GONE);
            categoryView.setVisibility(View.GONE);
            for(int i=0; i<coupleArrayList.size();i++){
                TableRow categoryRow = (TableRow) getLayoutInflater().inflate(R.layout.detailed_split_row, splitTable, false);
                TableRow amountRow = (TableRow) getLayoutInflater().inflate(R.layout.detailed_split_row, splitTable, false);
                TextView categoryLabel = (TextView) categoryRow.findViewById(R.id.detailedSplitLabel);
                AutoCompleteTextView categoryEdit = (AutoCompleteTextView) categoryRow.findViewById(R.id.detailedSplitEdit);
                TextView amountLabel = (TextView) amountRow.findViewById(R.id.detailedSplitLabel);
                AutoCompleteTextView amountEdit = (AutoCompleteTextView) amountRow.findViewById(R.id.detailedSplitEdit);
                categoryLabel.setText(getString(R.string.Category));
                categoryEdit.setText(coupleArrayList.get(i).getCategory().getName());
                amountLabel.setText(getString(R.string.Amount));
                amountEdit.setText(String.valueOf(coupleArrayList.get(i).getAmount()));
                splitTable.addView(categoryRow);
                splitTable.addView(amountRow);

            }
            gridLayout.invalidate();


        }

		try {
			payee.setText(bdl.getString("Payee"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			wording.setText(bdl.getString("Wording"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			info.setText(bdl.getString("Info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			amount.setText(bdl.getString("Amount"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			detailedCardStateGroup = new MyRadioGroup();
			detailedCardStateGroup.addRadioButton(none, false);
			detailedCardStateGroup.addRadioButton(reconciled, bdl.getBoolean("Reconciled"));
			detailedCardStateGroup.addRadioButton(remind, bdl.getBoolean("Remind"));
			detailedCardStateGroup.addRadioButton(cleared, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int typeAccount = bdl.getInt("Type", -1);
		try {
			switch (typeAccount) {
				case PayMode.CREDIT_CARD:
					image.setImageResource(R.drawable.mastercard);
					break;
				case PayMode.DEBIT_CARD:
					image.setImageResource(R.drawable.card);
					break;
				case PayMode.CASH:
					image.setImageResource(R.drawable.cash);
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
			e.printStackTrace();
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
		} else if (id == R.id.action_about) { //the settings button is selected
			Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
			startActivity(intent); //start the activity of preferences
			return true;
		} else {
			onBackPressed();
			return true;
		}
		//return super.onOptionsItemSelected(item);
	}

	private void updateLabel() {

		String myFormat = "dd/MM/yyyy"; //In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

		date.setText(sdf.format(myCalendar.getTime()));
	}
}
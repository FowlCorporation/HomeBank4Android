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

package com.fowlcorp.homebank4android.gui;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.SettingsActivity;
import com.fowlcorp.homebank4android.model.Operation;

public class AccountCardView extends CardView {
	
	private TextView date;
	private TextView category;
	private TextView tier;
	private TextView memo;
	private TextView solde;
	private TextView montant;
	private Context context;
	private Date Mydate;
	

	public AccountCardView(final Context context, ViewGroup parent, final Operation operation ) {
		super(context);
		this.context=context;
		Mydate = operation.getDate().getTime();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context.getApplicationContext(), DetailedCardActivity.class);
				Bundle bdl = new Bundle();
				bdl.putInt("Day", operation.getDate().get(Calendar.DAY_OF_MONTH));
				bdl.putInt("Month", operation.getDate().get(Calendar.MONTH));
				intent.putExtra("args", bdl);
				context.startActivity(intent);
			}
		});
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.card_layout,parent);
		
		date = (TextView) view.findViewById(R.id.cardLayout_date);
		category = (TextView) view.findViewById(R.id.cardLayout_category);
		tier = (TextView) view.findViewById(R.id.cardLayout_tier);
		memo = (TextView) view.findViewById(R.id.cardLayout_memo);
		solde = (TextView) view.findViewById(R.id.cardLayout_solde);
		montant = (TextView) view.findViewById(R.id.cardLayout_montant);
		
		
		
		/*String month = operation.getDate().getDisplayName(GregorianCalendar.mon, GregorianCalendar.LONG, Locale.FRANCE);
		String year = operation.getDate().getDisplayName(GregorianCalendar.YEAR, GregorianCalendar.LONG, Locale.FRANCE);
		
		String formatDate = day+"/"+month+"/"+year;*/
		
		try {
			date.setText(context.getString(R.string.cardLayout_date)+" "+df.format(Mydate));
		} catch (Exception e) {
		}
		try {
			category.setText(context.getString(R.string.cardLayout_category)+" "+operation.getCategory().getName());
		} catch (Exception e) {
		}
		try {
			tier.setText(context.getString(R.string.cardLayout_tier)+" "+operation.getPayee().getName());
		} catch (Exception e) {
		}
		try {
			memo.setText(context.getString(R.string.cardLayout_memo)+" "+operation.getWording());
		} catch (Exception e) {
		}
		try {
			montant.setText(context.getString(R.string.cardLayout_montant)+" "+String.valueOf(operation.getAmount()));
		} catch (Exception e) {
		}
		
		
		this.addView(view);
		
	}

}

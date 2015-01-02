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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Operation;


/**
 * @author Martin, Axel
 *
 */
public class AccountCardView extends CardView {
	
	private TextView date;
	private TextView category;
	private TextView payee;
	private TextView wording;
	private TextView balance;
	private TextView amount;
	private Context context;
	private Calendar myDate;
	

	public AccountCardView(final Context context, ViewGroup parent, final Operation operation ) {
		super(context);
		this.context=context;
		myDate = Calendar.getInstance();
		myDate.setTime(operation.getDate().getTime());
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context.getApplicationContext(), DetailedCardActivity.class);
				intent.putExtra("Date", df.format(myDate.getTime()));
				intent.putExtra("Category", operation.getCategory().getName());
				intent.putExtra("Payee", operation.getPayee().getName());
				intent.putExtra("Wording", operation.getWording());
				intent.putExtra("Amount", String.valueOf(operation.getAmount()));
				context.startActivity(intent);
			}
		});
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.card_layout,parent);
		
		date = (TextView) view.findViewById(R.id.cardLayout_date);
		category = (TextView) view.findViewById(R.id.cardLayout_category);
		payee = (TextView) view.findViewById(R.id.cardLayout_tier);
		wording = (TextView) view.findViewById(R.id.cardLayout_memo);
		balance = (TextView) view.findViewById(R.id.cardLayout_solde);
		amount = (TextView) view.findViewById(R.id.cardLayout_montant);
		
		
		
		/*String month = operation.getDate().getDisplayName(GregorianCalendar.mon, GregorianCalendar.LONG, Locale.FRANCE);
		String year = operation.getDate().getDisplayName(GregorianCalendar.YEAR, GregorianCalendar.LONG, Locale.FRANCE);
		
		String formatDate = day+"/"+month+"/"+year;*/
		      
		double montantdec = Math.round(operation.getBalanceAccount()*100);
		montantdec = montantdec/100;
		
		try {
			date.setText(context.getString(R.string.date)+" : "+df.format(myDate.getTime()));
		} catch (Exception e) {
		}
		try {
			category.setText(context.getString(R.string.category)+" : "+operation.getCategory().getName());
		} catch (Exception e) {
		}
		try {
			payee.setText(context.getString(R.string.payee)+" : "+operation.getPayee().getName());
		} catch (Exception e) {
		}
		try {
			wording.setText(context.getString(R.string.wording)+" : "+operation.getWording());
		} catch (Exception e) {
		}
		try {
            amount.setText(colorText(context.getString(R.string.amount) + " : ", String.valueOf(operation.getAmount())));
		} catch (Exception e) {
		}
        try {
            balance.setText(colorText(context.getString(R.string.balance) + " : ", String.valueOf(montantdec)));
        } catch (Exception e) {
        }
		
		
		this.addView(view);
		
	}

    private Spannable colorText(String fieldName, String value) {
        Spannable span = new SpannableString(fieldName + value);
        span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

}

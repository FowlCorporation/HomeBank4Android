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
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;


/**
 * @author Martin, Axel
 *
 */
public class OverviewCardView extends CardView {

	private TextView title;
	private TextView solde;
	private TextView futur;
	private TextView today;
	private double soldeValue;
	private double futurValue;
	private double todayValue;
	

	private Context context;
	private Model modele;

	public OverviewCardView(final Context context, ViewGroup parent, final Account account, final MainActivity activity) {
		super(context);
		this.context=context;

		modele = activity.getModel();
		
		/*	this.setOnClickListener(new OnClickListener() {

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
		});*/

		int key = account.getKey();
		modele.setSelectedAccount(key);
		modele.updateOperationAccountBalance();
		soldeValue = modele.getSelectedBankAccountBalance();
		futurValue = modele.getSelectedFutureAccountBalance();
		todayValue = modele.getSelectedTodayAccountBalance();
		
		soldeValue = Math.round(soldeValue*100);
		soldeValue = soldeValue/100;
		
		futurValue = Math.round(futurValue*100);
		futurValue = futurValue/100;
		
		todayValue = Math.round(todayValue*100);
		todayValue = todayValue/100;
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.overview_card_layout,parent);

		title = (TextView) view.findViewById(R.id.overview_card_title);
		solde = (TextView) view.findViewById(R.id.overview_card_solde);
		futur = (TextView) view.findViewById(R.id.overview_card_futur);
		today = (TextView) view.findViewById(R.id.overview_card_today);

		title.setText(account.getName());
		solde.setText(colorText(context.getString(R.string.overViewCard_solde) + " ", String.valueOf(soldeValue)));
		futur.setText(colorText(context.getString(R.string.overViewCard_future) + " ", String.valueOf(futurValue)));
		today.setText(colorText(context.getString(R.string.overviewCard_today) + " ", String.valueOf(todayValue)));
		
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = -1;
				for(int i = 0; i<activity.getDrawerList().size();i++){
					if(!activity.getDrawerList().get(i).isHeader()
							&& !activity.getDrawerList().get(i).isOverview()){
						if(activity.getDrawerList().get(i).getItemName().equals(account.getName())){
							position = i;
							break;
						}
					}
				}
				try {
					activity.getmNavigationDrawerFragment().selectItem(position);
					activity.onSectionAttached(position);
					activity.restoreActionBar();
				} catch (Exception e) {
				}
			}
		});
		
		this.addView(view);

	}

	private Spannable colorText(String fieldName, String value) {
		Spannable span = new SpannableString(fieldName + value);
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

}

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

package com.fowlcorp.homebank4android.gui;


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.Round;
import android.view.LayoutInflater;


class OverViewCard {


	public OverViewCard(Context context, LayoutInflater inflater, ViewGroup parent, Model model) {
		

        Account selectedAcc = model.getAccounts().get(model.getSelectedAccount());

        double balanceValue;
        double futureValue;
        double todayValue;

        balanceValue = selectedAcc.getBankAccountBalance();
        futureValue = selectedAcc.getFutureAccountBalance();
        todayValue = selectedAcc.getTodayAccountBalance();

        balanceValue = Round.roundAmount(balanceValue);
        futureValue = Round.roundAmount(futureValue);
        todayValue = Round.roundAmount(todayValue);

		View view = inflater.inflate(R.layout.overviewcard,parent);

        TextView balanceView;
        TextView todayView;
        TextView futureView;

        balanceView = (TextView) view.findViewById(R.id.overviewCard_solde);
        futureView = (TextView) view.findViewById(R.id.overviewCard_future);
        todayView = (TextView) view.findViewById(R.id.overviewCard_today);

        balanceView.setText(colorText(context.getString(R.string.Balance) + " : ", String.valueOf(balanceValue)));
		futureView.setText(colorText(context.getString(R.string.Future) + " : ", String.valueOf(futureValue)));
		todayView.setText(colorText(context.getString(R.string.Today) + " : ", String.valueOf(todayValue)));
		
		
	}
	
	private Spannable colorText(String fieldName, String value) {
		Spannable span = new SpannableString(fieldName + value);
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}


	
	

}

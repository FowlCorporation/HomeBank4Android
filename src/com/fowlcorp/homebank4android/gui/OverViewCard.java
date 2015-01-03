package com.fowlcorp.homebank4android.gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.Round;

public class OverViewCard extends CardView{
	

	private TextView soldeView;
	private TextView futurView;
	private TextView todayView;
	
	private double soldeValue;
	private double futurValue;
	private double todayValue;

	public OverViewCard(Context context, ViewGroup parent, Model model ) {
		super(context);
		
        Account selectedAcc = model.getAccounts().get(model.getSelectedAccount());
        soldeValue = selectedAcc.getBankAccountBalance();
        futurValue = selectedAcc.getFutureAccountBalance();
		todayValue = selectedAcc.getTodayAccountBalance();
			
		soldeValue = Round.roundAmount(soldeValue);
        futurValue = Round.roundAmount(futurValue);
        todayValue = Round.roundAmount(todayValue);
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.overviewcard,parent);
		
		soldeView = (TextView) view.findViewById(R.id.overviewCard_solde);
		futurView = (TextView) view.findViewById(R.id.overviewCard_future);
		todayView = (TextView) view.findViewById(R.id.overviewCard_today);
		
		soldeView.setText(colorText(context.getString(R.string.balance) + " ", String.valueOf(soldeValue)));
		futurView.setText(colorText(context.getString(R.string.future) + " ", String.valueOf(futurValue)));
		todayView.setText(colorText(context.getString(R.string.today) + " ", String.valueOf(todayValue)));
		
		this.addView(view);
		
	}
	
	private Spannable colorText(String fieldName, String value) {
		Spannable span = new SpannableString(fieldName + value);
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

}

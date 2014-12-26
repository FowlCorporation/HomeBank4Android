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
import com.fowlcorp.homebank4android.model.Model;

public class OverViewCard extends CardView{
	

	private TextView soldeView;
	private TextView futurView;
	private TextView todayView;
	
	private double soldeValue;
	private double futurValue;
	private double todayValue;

	public OverViewCard(Context context, ViewGroup parent, Model model ) {
		super(context);
		
		 soldeValue = model.getSelectedBankAccountBalance();
			futurValue = model.getSelectedFutureAccountBalance();
			todayValue = model.getSelectedTodayAccountBalance();
			
			soldeValue = Math.round(soldeValue*100);
			soldeValue = soldeValue/100;
			
			futurValue = Math.round(futurValue*100);
			futurValue = futurValue/100;
			
			todayValue = Math.round(todayValue*100);
			todayValue = todayValue/100;
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.overviewcard,parent);
		
		soldeView = (TextView) view.findViewById(R.id.overviewCard_solde);
		futurView = (TextView) view.findViewById(R.id.overviewCard_future);
		todayView = (TextView) view.findViewById(R.id.overviewCard_today);
		
		soldeView.setText(colorText(context.getString(R.string.overViewCard_solde) + " ", String.valueOf(soldeValue)));
		futurView.setText(colorText(context.getString(R.string.overViewCard_future) + " ", String.valueOf(futurValue)));
		todayView.setText(colorText(context.getString(R.string.overviewCard_today) + " ", String.valueOf(todayValue)));
		
		this.addView(view);
		
	}
	
	private Spannable colorText(String fieldName, String value) {
		Spannable span = new SpannableString(fieldName + value);
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

}

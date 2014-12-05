package com.fowlcorp.homebank4android.gui;

import android.R;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.TextView;

public class AccountCardView extends CardView{
	
	private GridLayout gridLayout;
	private TextView date, tier, categorie, debit, credit, solde, memo;
	
	private String dateLabel = "Date : ";
	private String tierLabel = "Tier : ";
	private String categorieLabel = "Catégorie : ";
	private String debitLabel = "Débit : ";
	private String creditLabel = "Crédit : ";
	private String soldeLabel = "Solde : ";
	private String memoLabel = "Memo : ";

	public AccountCardView(Context context) {
		super(context);
		gridLayout = new GridLayout(context);
		date = new TextView(context);
		tier = new TextView(context);
		categorie = new TextView(context);
		debit = new TextView(context);
		credit = new TextView(context);
		solde = new TextView(context);
		memo = new TextView(context);
		
		date.setText(dateLabel + "05/12/2014");
		tier.setText(tierLabel + "Martin");
		categorie.setText(categorieLabel + "Loisir");
		debit.setText(debitLabel + "0€");
		credit.setText(creditLabel + "10€");
		solde.setText(soldeLabel + "10€");
		memo.setText(memoLabel + "Cinéma");
		
		gridLayout.addView(date);
		gridLayout.addView(tier);
		gridLayout.addView(categorie);
		gridLayout.addView(debit);
		gridLayout.addView(credit);
		gridLayout.addView(solde);
		gridLayout.addView(memo);
		this.addView(gridLayout);
		
	}

}

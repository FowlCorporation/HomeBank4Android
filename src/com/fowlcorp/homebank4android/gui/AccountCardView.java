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

import android.R;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.Gravity;
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
		
		gridLayout.setColumnCount(3);
		gridLayout.setRowCount(3);
		
		GridLayout.LayoutParams paramCenter = new GridLayout.LayoutParams();
		
		
		
		
		/*date.setLayoutParams(paramCenter);
		tier.setLayoutParams(paramCenter);
		categorie.setLayoutParams(paramCenter);
		debit.setLayoutParams(paramCenter);
		credit.setLayoutParams(paramCenter);
		solde.setLayoutParams(paramCenter);
		memo.setLayoutParams(paramCenter);*/
		
		//gridLayout.setLayoutParams(paramCenter);
		gridLayout.setUseDefaultMargins(true);
		
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

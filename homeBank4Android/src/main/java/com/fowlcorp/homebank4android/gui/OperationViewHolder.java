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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fowlcorp.homebank4android.R;

public class OperationViewHolder extends RecyclerView.ViewHolder {
	
	private TextView date;
	private TextView category;
	private TextView payee;
	private TextView wording;
	private TextView balance;
	private TextView amount;
    private CardView card;
	private ImageView mode, option;
	private Context context;
	private LinearLayout splitLinear;
	private LinearLayout unSplitLinear;
	private View view;
    private LinearLayout rootLayout;

	public OperationViewHolder(View view) {
		super(view);
		this.view = view;
        card = (CardView) view.findViewById(R.id.card_view);
		date = (TextView) view.findViewById(R.id.cardLayout_date);
		category = (TextView) view.findViewById(R.id.cardLayout_category);
		payee = (TextView) view.findViewById(R.id.cardLayout_tier);
		wording = (TextView) view.findViewById(R.id.cardLayout_memo);
		balance = (TextView) view.findViewById(R.id.cardLayout_solde);
		amount = (TextView) view.findViewById(R.id.cardLayout_montant);
		mode = (ImageView) view.findViewById(R.id.pay_mode_icon);
        option = (ImageView) view.findViewById(R.id.optionnal_icon);
        rootLayout = (LinearLayout) view.findViewById(R.id.card_root_linear);
		splitLinear = (LinearLayout) view.findViewById(R.id.split_linear);
		unSplitLinear = (LinearLayout) view.findViewById(R.id.no_split_linear);
	}

	public TextView getDate() {
		return date;
	}

	public void setDate(TextView date) {
		this.date = date;
	}

	public TextView getCategory() {
		return category;
	}

	public void setCategory(TextView category) {
		this.category = category;
	}

	public TextView getPayee() {
		return payee;
	}

	public void setPayee(TextView payee) {
		this.payee = payee;
	}

	public TextView getWording() {
		return wording;
	}

	public void setWording(TextView wording) {
		this.wording = wording;
	}

	public TextView getBalance() {
		return balance;
	}

	public void setBalance(TextView balance) {
		this.balance = balance;
	}

	public TextView getAmount() {
		return amount;
	}

	public void setAmount(TextView amount) {
		this.amount = amount;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public ImageView getMode() {
		return mode;
	}

	public void setMode(ImageView mode) {
		this.mode = mode;
	}

	public LinearLayout getSplitLinear() {
		return splitLinear;
	}

	public void setSplitLinear(LinearLayout splitLinear) {
		this.splitLinear = splitLinear;
	}

	public LinearLayout getUnSplitLinear() {
		return unSplitLinear;
	}

	public void setUnSplitLinear(LinearLayout unSplitLinear) {
		this.unSplitLinear = unSplitLinear;
	}

    public CardView getCard() {
        return card;
    }

    public void setCard(CardView card) {
        this.card = card;
    }

    public LinearLayout getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(LinearLayout rootLayout) {
        this.rootLayout = rootLayout;
    }

    public ImageView getOption() {
        return option;
    }

    public void setOption(ImageView option) {
        this.option = option;
    }
}

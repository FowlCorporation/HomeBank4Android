package com.fowlcorp.homebank4android.gui;

import java.util.Calendar;

import com.fowlcorp.homebank4android.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class OperationViewHolder extends RecyclerView.ViewHolder {
	
	private TextView date;
	private TextView category;
	private TextView tier;
	private TextView memo;
	private TextView solde;
	private TextView montant;
	private Context context;
	private View view;

	public OperationViewHolder(View view) {
		super(view);
		this.view = view;
		date = (TextView) view.findViewById(R.id.cardLayout_date);
		category = (TextView) view.findViewById(R.id.cardLayout_category);
		tier = (TextView) view.findViewById(R.id.cardLayout_tier);
		memo = (TextView) view.findViewById(R.id.cardLayout_memo);
		solde = (TextView) view.findViewById(R.id.cardLayout_solde);
		montant = (TextView) view.findViewById(R.id.cardLayout_montant);
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

	public TextView getTier() {
		return tier;
	}

	public void setTier(TextView tier) {
		this.tier = tier;
	}

	public TextView getMemo() {
		return memo;
	}

	public void setMemo(TextView memo) {
		this.memo = memo;
	}

	public TextView getSolde() {
		return solde;
	}

	public void setSolde(TextView solde) {
		this.solde = solde;
	}

	public TextView getMontant() {
		return montant;
	}

	public void setMontant(TextView montant) {
		this.montant = montant;
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

	
	
	
	
}
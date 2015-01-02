package com.fowlcorp.homebank4android.gui;

import java.util.Calendar;

import com.fowlcorp.homebank4android.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OperationViewHolder extends RecyclerView.ViewHolder {
	
	private TextView date;
	private TextView category;
	private TextView tier;
	private TextView memo;
	private TextView solde;
	private TextView montant;
	private ImageView mode;
	private Context context;
	private LinearLayout splitLinear;
	private LinearLayout unSplitLinear;
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
		mode = (ImageView) view.findViewById(R.id.pay_mode_icon);
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

	
	
	
	
}
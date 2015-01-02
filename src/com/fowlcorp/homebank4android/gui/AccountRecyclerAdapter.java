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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Operation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountRecyclerAdapter extends RecyclerView.Adapter<OperationViewHolder> {
	private List<Operation> listOperation;
	private Calendar myDate;
	private MainActivity activity;

	public AccountRecyclerAdapter(List<Operation> listOperation, MainActivity activity) {
		this.listOperation = listOperation;
		this.activity = activity;
	}

	@Override
	public int getItemCount() {
		return listOperation.size();
	}

	@Override
	public void onBindViewHolder(OperationViewHolder holder, int position) {
		final Operation operation = listOperation.get(position);

		myDate = Calendar.getInstance();
		myDate.setTime(operation.getDate().getTime());
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		holder.getView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity.getApplicationContext(), DetailedCardActivity.class);
				try {
					intent.putExtra("Date", df.format(myDate.getTime()));
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Category", (operation.getCategory().getParent() == null ? "" : operation.getCategory().getParent().getName() + ": ") + operation.getCategory().getName());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Payee", operation.getPayee().getName());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Wording", operation.getWording());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Amount", String.valueOf(operation.getAmount()));
				} catch (Exception e) {
				}
				activity.startActivity(intent);
			}
		});

		double montantdec = Math.round(operation.getBalanceAccount() * 100);
		montantdec = montantdec / 100;

		try {
			holder.getDate().setText(activity.getString(R.string.cardLayout_date) + " " + df.format(myDate.getTime()));
		} catch (Exception e) {
		}
		try {
			holder.getTier().setText(activity.getString(R.string.cardLayout_tier) + " " + operation.getPayee().getName());
		} catch (Exception e) {
		}
		if(!operation.isSplit()){
			try {
				holder.getCategory().setText(activity.getString(R.string.cardLayout_category) + " " + (operation.getCategory().getParent() == null ? "" : operation.getCategory().getParent().getName() + ": ") + operation.getCategory().getName());
			} catch (Exception e) {
			}
			try {
				holder.getMemo().setText(activity.getString(R.string.cardLayout_memo) + " " + operation.getWording());
			} catch (Exception e) {
			}
			try {
				holder.getMontant().setText(colorText(activity.getString(R.string.cardLayout_montant) + " ", String.valueOf(operation.getAmount())));
			} catch (Exception e) {
			}
			try {
				holder.getSolde().setText(colorText(activity.getString(R.string.cardLayout_solde) + " ", String.valueOf(montantdec)));
			} catch (Exception e) {
			}
			try {
				switch (operation.getPayMode()) {
				case 1:
					holder.getMode().setImageResource(R.drawable.card);
					break;

				case 3:
					holder.getMode().setImageResource(R.drawable.espece);
					break;
				case 4:
					holder.getMode().setImageResource(R.drawable.transfert);
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		} else {
			holder.getUnSplitLinear().setVisibility(LinearLayout.GONE);
			
			LinearLayout splitLayout = holder.getSplitLinear();
			
			LayoutInflater inflater = activity.getLayoutInflater();
			for(int i=0;i<operation.getSplits().size();i++){
			View view = inflater.inflate(R.layout.split_layout, null);
			
			TextView category = (TextView) view.findViewById(R.id.splitLayout_category);
			//TextView memo = (TextView) view.findViewById(R.id.splitLayout_memo);
			TextView amount = (TextView) view.findViewById(R.id.splitLayout_amount);
			//System.out.println(activity.getString(R.string.cardLayout_category) + " " + (operation.getSplits().get(i).getCategory().getParent() == null ? "" : operation.getSplits().get(i).getCategory().getParent().getName() + ": ") + operation.getSplits().get(i).getCategory().getName());
			category.setText(activity.getString(R.string.cardLayout_category) + " " + (operation.getSplits().get(i).getCategory().getParent() == null ? "" : operation.getSplits().get(i).getCategory().getParent().getName() + ": ") + operation.getSplits().get(i).getCategory().getName());
			amount.setText(colorText(activity.getString(R.string.cardLayout_montant) + " ", String.valueOf(operation.getSplits().get(i).getAmount())));
			
			splitLayout.addView(view);
			
			try {
				holder.getMontant().setText(colorText(activity.getString(R.string.cardLayout_montant) + " ", String.valueOf(operation.getAmount())));
			} catch (Exception e) {
			}
			try {
				holder.getSolde().setText(colorText(activity.getString(R.string.cardLayout_solde) + " ", String.valueOf(montantdec)));
			} catch (Exception e) {
			}
			try {
				switch (operation.getPayMode()) {
				case 1:
					holder.getMode().setImageResource(R.drawable.card);
					break;

				case 3:
					holder.getMode().setImageResource(R.drawable.espece);
					break;
				case 4:
					holder.getMode().setImageResource(R.drawable.transfert);
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
			}
		}

	}

	@Override
	public OperationViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

		return new OperationViewHolder(v);
	}


	private Spannable colorText(String fieldName, String value) {
		Spannable span = new SpannableString(fieldName + value);
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

}
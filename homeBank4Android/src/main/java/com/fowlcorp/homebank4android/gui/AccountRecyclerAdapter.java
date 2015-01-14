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
import com.fowlcorp.homebank4android.model.Couple;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.model.PayMode;
import com.fowlcorp.homebank4android.utils.Round;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
	public void onBindViewHolder(final OperationViewHolder holder, int position) {
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
					intent.putExtra("Amount", String.valueOf(Round.roundAmount(operation.getAmount())));
				} catch (Exception e) {
				}
                Pair datePair = Pair.create(holder.getDate(), "date");
                Pair categoryPair = Pair.create(holder.getCategory(), "category");
                Pair wordingPair = Pair.create(holder.getMemo(), "wording");
                Pair payeePair = Pair.create(holder.getTier(), "payee");
                Pair amountPair = Pair.create(holder.getMontant(), "amount");
                Pair cardPair = Pair.create(holder.getCard(), "card");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        datePair,
                        categoryPair,
                        wordingPair,
                        payeePair,
                        amountPair,
                        cardPair);
                ActivityCompat.startActivity(activity, intent, options.toBundle());
                //activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
				//activity.startActivity(intent);
			}
		});

		try {
			holder.getDate().setText(activity.getString(R.string.Date) + " : " + df.format(myDate.getTime()));
		} catch (Exception e) {
		}
		try {
			holder.getTier().setText(activity.getString(R.string.Payee) + " : " + operation.getPayee().getName());
		} catch (Exception e) {
		}
		if(!operation.isSplit()){
			holder.getSplitLinear().removeAllViews();
			holder.getUnSplitLinear().setVisibility(LinearLayout.VISIBLE);
			try {
				holder.getCategory().setText(activity.getString(R.string.Category) + " : " + (operation.getCategory().getParent() == null ? "" : operation.getCategory().getParent().getName() + ": ") + operation.getCategory().getName());
			} catch (Exception e) {
			}
			try {
				holder.getMemo().setText(activity.getString(R.string.Wording) + " : " + operation.getWording());
			} catch (Exception e) {
			}
		} else {
			holder.getUnSplitLinear().setVisibility(LinearLayout.GONE);

			LinearLayout splitLayout = holder.getSplitLinear();
			splitLayout.removeAllViews();
			LayoutInflater inflater = activity.getLayoutInflater();
			for(Couple subOp : operation.getSplits()){
				View view = inflater.inflate(R.layout.split_layout, null);

				TextView category = (TextView) view.findViewById(R.id.splitLayout_category);
				//TextView memo = (TextView) view.findViewById(R.id.splitLayout_memo);
				TextView amount = (TextView) view.findViewById(R.id.splitLayout_amount);
				//System.out.println(activity.getString(R.string.cardLayout_category) + " " + (subOp.getCategory().getParent() == null ? "" :subOp.getCategory().getParent().getName() + ": ") + subOp.getCategory().getName());
				category.setText(activity.getString(R.string.Category) + " : " + (subOp.getCategory().getParent() == null ? "" : subOp.getCategory().getParent().getName() + ": ") + subOp.getCategory().getName());
				amount.setText(colorText(activity.getString(R.string.Amount) + " : ", String.valueOf(subOp.getAmount())));
				splitLayout.addView(view);
			}
		}
		try {
			holder.getMontant().setText(colorText(activity.getString(R.string.Amount) + " : ", String.valueOf(Round.roundAmount(operation.getAmount()))));
		} catch (Exception e) {
		}
		try {
			holder.getSolde().setText(colorText(activity.getString(R.string.Balance) + " : ", String.valueOf(Round.roundAmount(operation.getBalanceAccount()))));
		} catch (Exception e) {
		}

		try {
			switch (operation.getPayMode()) {
			case PayMode.CREDIT_CARD:
			case PayMode.DEBIT_CARD:
				holder.getMode().setImageResource(R.drawable.card);
				break;
			case PayMode.CASH:
				holder.getMode().setImageResource(R.drawable.espece);
				break;
			case PayMode.TRANSFERT:
				holder.getMode().setImageResource(R.drawable.transfert);
				break;
			case PayMode.ELECTRONIC_PAYMENT:
				holder.getMode().setImageResource(R.drawable.nfc);
				break;
			case PayMode.CHEQUE:
				holder.getMode().setImageResource(R.drawable.cheque);
				break;
			default:
				holder.getMode().setImageDrawable(null);
				break;
			}
		} catch (Exception e) {
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
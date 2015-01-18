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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Couple;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.model.PayMode;
import com.fowlcorp.homebank4android.utils.Round;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccountRecyclerAdapter extends RecyclerView.Adapter<OperationViewHolder> {
	private List<Operation> listOperation;
	private Calendar myDate;
	private Activity activity;

	public AccountRecyclerAdapter(List<Operation> listOperation, Activity activity) {
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
		holder.getRootLayout().setOnClickListener(new OnClickListener() {


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
				try {
					intent.putExtra("Type", operation.getPayMode());
				} catch (Exception e) {
				}
//                try {
//                    intent.putExtra("Info", operation.getInfo());
//                } catch (Exception e) {
//                }
				try {
					intent.putExtra("Reconciled", operation.isReconciled());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Reconciled", operation.isReconciled());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Remind", operation.isRemind());
				} catch (Exception e) {
				}
				try {
					intent.putExtra("Split", operation.isSplit());
				} catch (Exception e) {
				}

				Pair datePair = Pair.create(holder.getDate(), "date");
				Pair categoryPair = Pair.create(holder.getCategory(), "category");
				Pair wordingPair = Pair.create(holder.getWording(), "wording");
				Pair payeePair = Pair.create(holder.getPayee(), "payee");
				Pair amountPair = Pair.create(holder.getAmount(), "amount");
				Pair cardPair = Pair.create(holder.getCard(), "card");
				Pair iconPair = Pair.create(holder.getMode(), "icon");
				ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
						cardPair
				);
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
			holder.getPayee().setText(activity.getString(R.string.Payee) + " : " + operation.getPayee().getName());
		} catch (Exception e) {
		}
		if (!operation.isSplit()) {
			holder.getSplitLinear().removeAllViews();
			holder.getUnSplitLinear().setVisibility(LinearLayout.VISIBLE);
			try {
				holder.getCategory().setText(activity.getString(R.string.Category) + " : " + (operation.getCategory().getParent() == null ? "" : operation.getCategory().getParent().getName() + ": ") + operation.getCategory().getName());
			} catch (Exception e) {
			}
			try {
				holder.getWording().setText(activity.getString(R.string.Wording) + " : " + operation.getWording());
			} catch (Exception e) {
			}
		} else {
			holder.getUnSplitLinear().setVisibility(LinearLayout.GONE);

			LinearLayout splitLayout = holder.getSplitLinear();
			splitLayout.removeAllViews();
			LayoutInflater inflater = activity.getLayoutInflater();
			for (Couple subOp : operation.getSplits()) {
				View view = inflater.inflate(R.layout.split_layout, null);

				TextView category = (TextView) view.findViewById(R.id.splitLayout_category);
				//TextView memo = (TextView) view.findViewById(R.id.splitLayout_memo);
				TextView amount = (TextView) view.findViewById(R.id.splitLayout_amount);
				//System.out.println(activity.getString(R.string.cardLayout_category) + " " + (subOp.getCategory().getParent() == null ? "" :subOp.getCategory().getParent().getName() + ": ") + subOp.getCategory().getName());
				category.setText(activity.getString(R.string.Category) + " : " + (subOp.getCategory().getParent() == null ? "" : subOp.getCategory().getParent().getName() + ": ") + subOp.getCategory().getName());
				amount.setText(colorText(activity.getString(R.string.Amount) + " : ", "" + Round.roundAmount(subOp.getAmount())));
				splitLayout.addView(view);
			}
		}
		try {
			holder.getAmount().setText(colorText(activity.getString(R.string.Amount) + " : ", String.valueOf(Round.roundAmount(operation.getAmount()))));
		} catch (Exception e) {
		}
		try {
			holder.getBalance().setText(colorText(activity.getString(R.string.Balance) + " : ", String.valueOf(Round.roundAmount(operation.getBalanceAccount()))));
		} catch (Exception e) {
		}

		if (operation.isSplit()) {
			holder.getOption().setImageResource(R.drawable.split);
		} else if (operation.isRemind()) {
			holder.getOption().setImageResource(R.drawable.remind);
			holder.getCard().setCardBackgroundColor(Color.parseColor("#ffebee"));
		} else {
			holder.getOption().setImageDrawable(null);
		}

		if (!operation.isReconciled() && !operation.isRemind()) {
			holder.getCard().setCardBackgroundColor(Color.parseColor("#fff3e0"));
		} else if(operation.isReconciled()){
            holder.getCard().setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

		try {
			switch (operation.getPayMode()) {
				case PayMode.CREDIT_CARD:
					holder.getMode().setImageResource(R.drawable.mastercard);
					break;
				case PayMode.DEBIT_CARD:
					holder.getMode().setImageResource(R.drawable.card);
					break;
				case PayMode.CASH:
					holder.getMode().setImageResource(R.drawable.cash);
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
		Spannable span = new SpannableString(fieldName + value + getCurrency());
		span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}

	private String getCurrency() {
		String result;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		result = " " + sharedPreferences.getString("currency", "€");
		return result;
	}

}
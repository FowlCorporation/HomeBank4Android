package com.fowlcorp.homebank4android.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;

public class OverviewRecyclerAdapter extends RecyclerView.Adapter<OverviewViewHolder> {
    private List<Account> listAccount;
    private Context context;
    private Model model;
    
    private double soldeValue;
	private double futurValue;
	private double todayValue;
	
	private MainActivity activity;
    
    public OverviewRecyclerAdapter(List<Account> listAccount ,MainActivity activity, Model model) {
    	this.listAccount = listAccount;
    	this.model = model;
    	this.activity = activity;
	}

	@Override
	public int getItemCount() {
		return listAccount.size();
	}

	@Override
	public void onBindViewHolder(OverviewViewHolder holder, int position) {
		final Account account = listAccount.get(position);
		
		int key = account.getKey();
		model.setSelectedAccount(key);
		model.updateOperationAccountBalance();
		soldeValue = model.getSelectedBankAccountBalance();
		futurValue = model.getSelectedFutureAccountBalance();
		todayValue = model.getSelectedTodayAccountBalance();
		
		soldeValue = Math.round(soldeValue*100);
		soldeValue = soldeValue/100;
		
		futurValue = Math.round(futurValue*100);
		futurValue = futurValue/100;
		
		todayValue = Math.round(todayValue*100);
		todayValue = todayValue/100;
		
		holder.getItemView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = -1;
				for(int i = 0; i<activity.getDrawerList().size();i++){
					if(activity.getDrawerList().get(i).getKey() == account.getKey()){
						position = i;
						break;
					}
				}
				try {
					activity.getmNavigationDrawerFragment().selectItem(position);
					activity.onSectionAttached(position);
					activity.restoreActionBar();
				} catch (Exception e) {
				}
			}
		});
		
		holder.getTitle().setText(account.getName());
		holder.getSolde().setText(colorText(activity.getString(R.string.overViewCard_solde) + " ", String.valueOf(soldeValue)));
		holder.getFutur().setText(colorText(activity.getString(R.string.overViewCard_future) + " ", String.valueOf(futurValue)));
		holder.getToday().setText(colorText(activity.getString(R.string.overviewCard_today) + " ", String.valueOf(todayValue)));
				
	}

	@Override
	public OverviewViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_card_layout, parent, false);

		return new OverviewViewHolder(v);
	}

	
	private Spannable colorText(String fieldName, String value) {
        Spannable span = new SpannableString(fieldName + value);
        span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
  
}
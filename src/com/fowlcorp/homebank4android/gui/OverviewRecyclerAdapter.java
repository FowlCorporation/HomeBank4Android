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

import java.util.List;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.AccountType;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.Round;

public class OverviewRecyclerAdapter extends RecyclerView.Adapter<OverviewViewHolder> {
    private List<Account> listAccount;
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
        Account selectedAcc = model.getAccounts().get(model.getSelectedAccount());
        soldeValue = selectedAcc.getBankAccountBalance();
        futurValue = selectedAcc.getFutureAccountBalance();
        todayValue = selectedAcc.getTodayAccountBalance();
        

        soldeValue = Round.roundAmount(soldeValue);
        futurValue = Round.roundAmount(futurValue);
        todayValue = Round.roundAmount(todayValue);
		
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
		holder.getSolde().setText(colorText(activity.getString(R.string.balance) + " ", String.valueOf(soldeValue)));
		holder.getFutur().setText(colorText(activity.getString(R.string.future) + " ", String.valueOf(futurValue)));
		holder.getToday().setText(colorText(activity.getString(R.string.today) + " ", String.valueOf(todayValue)));
		
		switch (account.getType()) {
		case AccountType.BANK:
			holder.getIcon().setImageResource(R.drawable.bank);
			break;
		case AccountType.CASH:
			holder.getIcon().setImageResource(R.drawable.espece);
			break;
		default:
			holder.getIcon().setImageDrawable(null);
			break;
		}
				
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
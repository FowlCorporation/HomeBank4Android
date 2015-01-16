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

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.AccountType;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.Round;

import java.util.List;

public class OverviewRecyclerAdapter extends RecyclerView.Adapter<OverviewViewHolder> {
    private List<Account> listAccount;
    private Model model;

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
    public void onBindViewHolder(final OverviewViewHolder holder, int position) {
        final Account account = listAccount.get(position);

        int key = account.getKey();
        model.setSelectedAccount(key);
        //model.updateOperationAccountBalance();
        Account selectedAcc = model.getAccounts().get(model.getSelectedAccount());

        double balanceValue;
        double futureValue;
        double todayValue;

        balanceValue = selectedAcc.getBankAccountBalance();
        futureValue = selectedAcc.getFutureAccountBalance();
        todayValue = selectedAcc.getTodayAccountBalance();


        balanceValue = Round.roundAmount(balanceValue);
        futureValue = Round.roundAmount(futureValue);
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
                    //activity.getmNavigationDrawerFragment().selectItem(position);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager(); //get the fragment manager
                    FragmentTransaction tx = fragmentManager.beginTransaction(); //begin a transaction
                    //tx.addSharedElement(holder.getTitle(), "toolbar");
                    tx.replace(R.id.container,PagerSwipeFragment.newInstance(position, activity)).commit(); //invoke the account fragment
                    activity.onSectionAttached(position);
                    activity.restoreActionBar();
                } catch (Exception e) {
                }
            }
        });

        holder.getTitle().setText(account.getName());
        holder.getBalance().setText(colorText(activity.getString(R.string.Balance) + " : ", String.valueOf(balanceValue)));
        holder.getFuture().setText(colorText(activity.getString(R.string.Future) + " : ", String.valueOf(futureValue)));
        holder.getToday().setText(colorText(activity.getString(R.string.Today) + " : ", String.valueOf(todayValue)));

        switch (account.getType()) {
            case AccountType.BANK:
                holder.getIcon().setImageResource(R.drawable.bank);
                break;
            case AccountType.CASH:
                holder.getIcon().setImageResource(R.drawable.cash_account);
                break;
            case AccountType.ASSET:
                holder.getIcon().setImageResource(R.drawable.asset);
                break;
            case AccountType.CREDITCARD:
                holder.getIcon().setImageResource(R.drawable.card);
                break;
            case AccountType.LIABILITY:
                holder.getIcon().setImageResource(R.drawable.liability);
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
        Spannable span = new SpannableString(fieldName + value + getCurrency());
        span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    private String getCurrency(){
        String result;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        result = " "+sharedPreferences.getString("currency", "€");
        return result;
    }

}
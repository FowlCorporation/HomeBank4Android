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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;
import com.fowlcorp.homebank4android.utils.Round;

public class OverviewFragment extends Fragment{

	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;
	private ArrayList<DrawerItem> drawerList;

    private double soldeValue;
    private double futurValue;
    private double todayValue;

    private TextView title;
    private TextView solde;
    private TextView futur;
    private TextView today;
    private ImageView icon;

	private MainActivity activity;
	
	private LinearLayoutManager mLayoutManager;
	private OverviewRecyclerAdapter mAdapter;
    private LinearLayout overView;

    private static final String ARG_MODEL = "model";

    public OverviewFragment(){

    }
	
	public static final OverviewFragment newInstance(Model model)	{
		OverviewFragment f = new OverviewFragment();
        Bundle bdl = new Bundle(3);
        bdl.putSerializable(ARG_MODEL, model);
        f.setArguments(bdl);
        return f;
	}
	
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
        model = (Model) getArguments().getSerializable(ARG_MODEL);
        accountList = new ArrayList<>(model.getAccounts().values());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.recycle_layout, container,false);
		RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        LinearLayout overView = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);

        View overViewView = inflater.inflate(R.layout.overview_overview_card_layout, overView,true);

        title = (TextView) overViewView.findViewById(R.id.overview_card_title);
        solde = (TextView) overViewView.findViewById(R.id.overview_card_balance);
        futur = (TextView) overViewView.findViewById(R.id.overview_card_future);
        today = (TextView) overViewView.findViewById(R.id.overview_card_today);
        icon = (ImageView) overViewView.findViewById(R.id.overview_card_icon);

        Log.e("Debug",String.valueOf(model.getGrandTotalBank()));



		
		recycler.setHasFixedSize(false);

		mLayoutManager = new LinearLayoutManager(activity);
		recycler.setLayoutManager(mLayoutManager);

		mAdapter = new OverviewRecyclerAdapter(accountList, activity, model);
        recycler.setAdapter(mAdapter);

        updateOverViewView();
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(0);
        this.activity = (MainActivity) activity;
        drawerList = this.activity.getDrawerList();
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

    private void updateOverViewView(){
        soldeValue = Round.roundAmount(model.getGrandTotalBank());
        futurValue = Round.roundAmount(model.getGrandTotalFuture());
        todayValue = Round.roundAmount(model.getGrandTotalToday());

        title.setText(activity.getString(R.string.Overview));
        solde.setText(colorText(activity.getString(R.string.Balance) + " : ", String.valueOf(soldeValue)));
        futur.setText(colorText(activity.getString(R.string.Future) + " : ", String.valueOf(futurValue)));
        today.setText(colorText(activity.getString(R.string.Today) + " : ", String.valueOf(todayValue)));
        icon.setImageDrawable(getResources().getDrawable(R.drawable.overview));
    }
}

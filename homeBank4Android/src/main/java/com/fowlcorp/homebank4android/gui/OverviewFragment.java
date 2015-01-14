/*
 * Copyright © 2015 Fowl Corporation
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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
	
	private MainActivity activity;
	
	private LinearLayoutManager mLayoutManager;
	private OverviewRecyclerAdapter mAdapter;

	public OverviewFragment(MainActivity activity){
		this.activity = activity;
		model = activity.getModel();
		accountList = activity.getAccountList();
		drawerList = activity.getDrawerList();
	}
	
	public static final OverviewFragment newInstance(MainActivity activity)
	{
		 OverviewFragment f = new OverviewFragment(activity);
		    return f;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.recycle_layout, container,false);
		RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        LinearLayout overView = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);

        View overViewView = inflater.inflate(R.layout.overview_card_layout, overView,true);

        TextView title = (TextView) overViewView.findViewById(R.id.overview_card_title);
        TextView solde = (TextView) overViewView.findViewById(R.id.overview_card_solde);
        TextView futur = (TextView) overViewView.findViewById(R.id.overview_card_futur);
        TextView today = (TextView) overViewView.findViewById(R.id.overview_card_today);
        ImageView icon = (ImageView) overViewView.findViewById(R.id.overview_card_icon);

        soldeValue = Round.roundAmount(model.getGrandTotalBank());
        futurValue = Round.roundAmount(model.getGrandTotalFuture());
        todayValue = Round.roundAmount(model.getGrandTotalToday());

        title.setText(activity.getString(R.string.Overview));
        solde.setText(colorText(activity.getString(R.string.Balance) + " : ", String.valueOf(soldeValue)));
        futur.setText(colorText(activity.getString(R.string.Future) + " : ", String.valueOf(futurValue)));
        today.setText(colorText(activity.getString(R.string.Today) + " : ", String.valueOf(todayValue)));
        icon.setImageDrawable(null);

		
		recycler.setHasFixedSize(false);

		mLayoutManager = new LinearLayoutManager(activity);
		recycler.setLayoutManager(mLayoutManager);

		mAdapter = new OverviewRecyclerAdapter(accountList, activity, model);
        recycler.setAdapter(mAdapter);
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(0);
	}

    private Spannable colorText(String fieldName, String value) {
        Spannable span = new SpannableString(fieldName + value);
        span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}

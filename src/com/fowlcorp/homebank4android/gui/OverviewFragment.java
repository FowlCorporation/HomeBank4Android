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

import java.util.ArrayList;
import java.util.List;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.NavigationDrawerFragment;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverviewFragment extends Fragment{

	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;
	private ArrayList<DrawerItem> drawerList;
	
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
}

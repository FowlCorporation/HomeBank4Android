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

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;

public class AccountFragment extends Fragment{

	private static final String ARG_SECTION_NUMBER = "section_number";
	private int sectionNumber;
	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;
	private ArrayList<DrawerItem> drawerList;
	private MainActivity activity;

	private AccountRecyclerAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;


	public AccountFragment(MainActivity activity){//empty constructor
		this.activity = activity;
		model = activity.getModel();
		accountList = activity.getAccountList();
		drawerList = activity.getDrawerList();
	}

	public static final AccountFragment newInstance(int position, MainActivity activity)
	{
		AccountFragment f = new AccountFragment(activity);
		Bundle bdl = new Bundle(2);
		bdl.putInt(ARG_SECTION_NUMBER, position);
		f.setArguments(bdl);
		return f;
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER); //get the position of the account in the drawer
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		for(int i=0;i<accountList.size();i++){ //find the account in the drawerlist
			if(drawerList.get(sectionNumber).getKey() == accountList.get(i).getKey()){
				sectionNumber = i;
			}
		}
		int key = accountList.get(sectionNumber).getKey(); //compute the balance of the account
		model.setSelectedAccount(key);
		model.updateOperationAccountBalance();

		operation = model.getOperations(model.getAccounts().get(key)); //get the operations of the account

		View rootView = inflater.inflate(R.layout.recycle_layout, container, false);
		OverViewCard over = new OverViewCard(getActivity(), (ViewGroup) this.getView(), model);
		RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
		LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);


		mRecyclerView.setHasFixedSize(false);

		mLayoutManager = new LinearLayoutManager(activity);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new AccountRecyclerAdapter(operation, activity);
        mRecyclerView.setAdapter(mAdapter);

        overview.addView(over);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));//notify main activity
	}



}

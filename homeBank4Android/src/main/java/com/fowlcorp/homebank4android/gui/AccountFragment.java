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
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class AccountFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_MODEL = "model";
    private static final String ARG_DISPLAY_VALUE = "display_value";

	public static final int DISPLAY_ALL = 0;
	public static final int DISPLAY_PAID = 1;
	public static final int DISPLAY_UNPAID = 2;
	public static final int DISPLAY_REMIND = 3;

	private int sectionNumber; //the number of the section in the drawer
	private ArrayList<Account> accountList; //the list of account

    private Model model; //the data model

	private int displayValue;

	public AccountFragment(){//empty constructor
	}

	public static AccountFragment newInstance(int position, Model model, int displayValue)	{
		AccountFragment f = new AccountFragment();
		Bundle bdl = new Bundle(3);
        bdl.putInt(ARG_DISPLAY_VALUE, displayValue);
        bdl.putInt(ARG_SECTION_NUMBER, position);
        bdl.putSerializable(ARG_MODEL, model);
		f.setArguments(bdl);
        return f;
	}

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER); //get the position of the account in the drawer
        displayValue = getArguments().getInt(ARG_DISPLAY_VALUE);
        model = (Model) getArguments().getSerializable(ARG_MODEL);
        accountList = new ArrayList<>(model.getAccounts().values());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		int key = accountList.get(sectionNumber).getKey();

        List<Operation> operation = model.getOperations(model.getAccounts().get(key));
		ArrayList<Operation> listTemp = new ArrayList<>();

		switch (displayValue) {
			case DISPLAY_ALL:
				listTemp.addAll(operation); //get all the operation to display all
				break;
			case DISPLAY_PAID:
				for (Operation op : operation) {
					if (op.isReconciled()) {
						listTemp.add(op);
					}
				}
				break;
			case DISPLAY_UNPAID:
				for (Operation op : operation) {
					if (!op.isReconciled()) {
						listTemp.add(op);
					}
				}
				break;
			case DISPLAY_REMIND:
				for (Operation op : operation) {
					if (op.isRemind()) {
						listTemp.add(op);
					}
				}

		}

		ArrayList<Operation> listOperation = new ArrayList<>();
		for (int i = listTemp.size() - 1; i >= 0; i--) { //revert the sort
			listOperation.add(listTemp.get(i));
		}

		View rootView = inflater.inflate(R.layout.recycle_layout, container, false); //the recycler layout


		RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
		LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);
		OverviewCard over = new OverviewCard(getActivity(), inflater, overview, model); //create the overview card

		mRecyclerView.setHasFixedSize(false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mLayoutManager.setOrientation(HORIZONTAL);
		}
		mRecyclerView.setLayoutManager(mLayoutManager);
        AccountRecyclerAdapter mAdapter = new AccountRecyclerAdapter(listOperation, getActivity());
		mRecyclerView.setAdapter(mAdapter);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}


}

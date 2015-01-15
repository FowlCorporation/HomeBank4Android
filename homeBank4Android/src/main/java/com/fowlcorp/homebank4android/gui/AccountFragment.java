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

public class AccountFragment extends Fragment{

	private static final String ARG_SECTION_NUMBER = "section_number";

    public static final int DISPLAY_ALL = 0;
    public static final int DISPLAY_PAID = 1;
    public static final int DISPLAY_UNPAID = 2;
    public static final int DISPLAY_REMIND = 3;

	private int sectionNumber; //the number of the section in the drawer
	private ArrayList<Account> accountList; //the list of account
	private List<Operation> operation; //the list of operation of an account
	private Model model; //the data model
	private ArrayList<DrawerItem> drawerList; //the draweritem list
	private MainActivity activity;

	private AccountRecyclerAdapter mAdapter; //the adapter for the recycle view
	private RecyclerView.LayoutManager mLayoutManager; //the layout manager
	
    private int displayValue;


	public AccountFragment(MainActivity activity, int displayValue){//empty constructor
		this.activity = activity;
        this.displayValue = displayValue;
		model = activity.getModel();
		accountList = activity.getAccountList();
		drawerList = activity.getDrawerList();
	}
	
	public static final AccountFragment newInstance(int position, MainActivity activity, int displayValue)
	{
		AccountFragment f = new AccountFragment(activity, displayValue);
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

		/*for(int i=0;i<accountList.size();i++){ //find the account in the drawerlist
			if(drawerList.get(sectionNumber).getKey() == accountList.get(i).getKey()){
				sectionNumber = i;
			}
		}*/
		int key = accountList.get(sectionNumber).getKey(); //compute the balance of the account
		//model.setSelectedAccount(key);
		//model.setSelectedAccount(sectionNumber);
		//model.updateOperationAccountBalance();

		operation = model.getOperations(model.getAccounts().get(key)); //get the operations of the account
		ArrayList<Operation> listTemp = new ArrayList<Operation>();

        switch (displayValue){
            case DISPLAY_ALL:
                listTemp.addAll(operation); //get all the operation to display all
                break;
            case DISPLAY_PAID:
                for(Operation op : operation){
                    if(op.isReconciled()){
                        listTemp.add(op);
                    }
                }
                break;
            case DISPLAY_UNPAID:
                for(Operation op : operation){
                    if(!op.isReconciled()){
                        listTemp.add(op);
                    }
                }
                break;
            case DISPLAY_REMIND:
                for(Operation op : operation){
                    if(op.isRemind()){
                        listTemp.add(op);
                    }
                }

        }

		ArrayList<Operation> listOperation = new ArrayList<Operation>();
		for(int i=listTemp.size()-1;i>=0;i--){ //revert the sort
			listOperation.add(listTemp.get(i));
		}
		
		View rootView = inflater.inflate(R.layout.recycle_layout, container, false); //the recycler layout
		OverviewCard over = new OverviewCard(getActivity(), (ViewGroup) this.getView(), model); //create the overview card
		RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
		LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);

		mRecyclerView.setHasFixedSize(false);

		mLayoutManager = new LinearLayoutManager(activity);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new AccountRecyclerAdapter(listOperation, activity);
        mRecyclerView.setAdapter(mAdapter);

        overview.addView(over.getView());
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}



}

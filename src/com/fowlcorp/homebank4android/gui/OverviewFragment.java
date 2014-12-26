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

	public OverviewFragment(){

	}
	
	public static final OverviewFragment newInstance()
	{
		 OverviewFragment f = new OverviewFragment();
		    /*Bundle bdl = new Bundle(2);
		    bdl.putInt(ARG_SECTION_NUMBER, position);
		    f.setArguments(bdl);*/
		    return f;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		/*int key = accountList.get(sectionNumber).getKey();
        model.setSelectedAccount(key);
        model.updateOperationAccountBalance();
	    operation = model.getOperations(model.getAccounts().get(key));*/
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		//OverViewCard over = new OverViewCard(getActivity(), (ViewGroup) this.getView());
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragmentLinear);
		//LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);
		//overview.addView(over);
		for(int i=0;i<accountList.size();i++){
			OverviewCardView card = new OverviewCardView(getActivity(), (ViewGroup) this.getView(), accountList.get(i), model);
			layout.addView(card);
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(0);
		model = ((MainActivity) activity).getModel();
		accountList = ((MainActivity) activity).getAccountList();
		drawerList = ((MainActivity) activity).getDrawerList();
	}
}

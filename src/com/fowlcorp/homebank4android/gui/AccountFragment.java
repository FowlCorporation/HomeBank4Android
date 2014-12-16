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

public class AccountFragment extends Fragment{

	private static final String ARG_SECTION_NUMBER = "section_number";
	private int sectionNumber;
	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;

	public AccountFragment(){

	}
	
	public static final AccountFragment newInstance(int position)
	{
		 AccountFragment f = new AccountFragment();
		    Bundle bdl = new Bundle(2);
		    bdl.putInt(ARG_SECTION_NUMBER, position);
		    f.setArguments(bdl);
		    return f;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int key = accountList.get(sectionNumber).getKey();
        model.setSelectedAccount(key);
        model.updateOperationAccountBalance();
	    operation = model.getOperations(model.getAccounts().get(key));
	    System.out.println("section number : "+sectionNumber+" number of operation : "+operation.size());
//	    for(int i=0; i<model.getOperations().size();i++){
//	    	if(model.getOperations().get(i).getAccount().equals(model.getAccounts().get(key))){
//	    		operation.add(model.getOperations().get(i));
//	    	}
//	    }
	    System.out.println("number of operation : "+operation.size());
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		OverViewCard over = new OverViewCard(getActivity(), (ViewGroup) this.getView());
		//View overViewCard = inflater.inflate(R.layout.overviewcard,  container);
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragmentLinear);
		LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);
		overview.addView(over);
		for(int i=operation.size()-1; i>0; i--){
			AccountCardView card = new AccountCardView(getActivity(), (ViewGroup) this.getView(), operation.get(i));
			layout.addView(card);
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
		model = ((MainActivity) activity).getModel();
		accountList = ((MainActivity) activity).getAccountList();
	}
}

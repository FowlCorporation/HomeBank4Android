package com.fowlcorp.homebank4android.gui;

import java.util.ArrayList;

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
	private ArrayList<Operation> operation;
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
	    
	    int key = accountList.get(sectionNumber).getKey();
	    operation = new ArrayList<Operation>();
	    
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		OverViewCard over = new OverViewCard(getActivity(), (ViewGroup) this.getView());
		//View overViewCard = inflater.inflate(R.layout.overviewcard,  container);
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.fragmentLinear);
		LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.fragmentOverview);
		overview.addView(over);
		for(int i=0;i<100;i++){
			AccountCardView card = new AccountCardView(getActivity(), (ViewGroup) this.getView());
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

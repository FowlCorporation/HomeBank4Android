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
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.common.view.SlidingTabLayout;
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;

public class PagerSwipeFragment extends Fragment{

	private static final String ARG_SECTION_NUMBER = "section_number";
	private int sectionNumber;
	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;
	private ArrayList<DrawerItem> drawerList;
	private MainActivity activity;

	private AccountRecyclerAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	
	private SlidingTabLayout mSlidingTabLayout;


	public PagerSwipeFragment(MainActivity activity){//empty constructor
		this.activity = activity;
		model = activity.getModel();
		accountList = activity.getAccountList();
		drawerList = activity.getDrawerList();
	}

	public static final PagerSwipeFragment newInstance(int position, MainActivity activity)
	{
		PagerSwipeFragment f = new PagerSwipeFragment(activity);
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
	
		FragmentPagerAdapter pager = new CustomFragmentPagerAdapter(getChildFragmentManager(), sectionNumber, activity);
		View rootView = inflater.inflate(R.layout.pager_layout, container, false);
		
		ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(pager);
        
        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        
       // mSlidingTabLayout.setSelectedIndicatorColors(R.color.background_material_light);
        
        System.out.println(sectionNumber);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));//notify main activity
	}



}

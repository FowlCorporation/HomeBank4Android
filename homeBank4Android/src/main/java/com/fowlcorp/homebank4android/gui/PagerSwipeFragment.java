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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.common.view.SlidingTabLayout;
import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class PagerSwipeFragment extends Fragment{

	private static final String ARG_SECTION_NUMBER = "section_number";
	private int sectionNumber;
	private ArrayList<Account> accountList;
	private List<Operation> operation;
	private Model model;
	private ArrayList<DrawerItem> drawerList;
	//private MainActivity activity;
    private ViewGroup container;

	private AccountRecyclerAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
    private ViewPager mViewPager;
    private FragmentPagerAdapter pager;
    private LayoutInflater inflater;

    private static final String ARG_MODEL = "model";

    private View rootView;
	
	private SlidingTabLayout mSlidingTabLayout;

    public PagerSwipeFragment() {//empty constructor
    }

	public static final PagerSwipeFragment newInstance(int position, Model model) {
		PagerSwipeFragment f = new PagerSwipeFragment();
		Bundle bdl = new Bundle(2);
        bdl.putInt(ARG_SECTION_NUMBER, position);
        bdl.putSerializable(ARG_MODEL, model);
		f.setArguments(bdl);
		return f;
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER); //get the position of the account in the drawer
        model = (Model) getArguments().getSerializable(ARG_MODEL);
        accountList = new ArrayList<>(model.getAccounts().values());
	}


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    this.inflater = inflater;
        this.container = container;

        /*for(int i=0;i<accountList.size();i++){ //find the account in the drawerList
            if(drawerList.get(sectionNumber).getKey() == accountList.get(i).getKey()){
                sectionNumber = i;
                break;
            }
        }
        int key = accountList.get(sectionNumber).getKey(); //compute the balance of the account

		pager = new CustomFragmentPagerAdapter(getChildFragmentManager(), sectionNumber, key, getActivity(), model);*/
        pager =  getNewAdapter(getChildFragmentManager(), sectionNumber, getActivity(), model);
		rootView = inflater.inflate(R.layout.pager_layout, container, false);
		
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
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
        //this.activity = (MainActivity) activity;
        drawerList = ((MainActivity)activity).getDrawerList();
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("conf", "conf changed in pager");
        int position = mViewPager.getCurrentItem();
        mViewPager.setAdapter(getNewAdapter(getChildFragmentManager(), sectionNumber, getActivity(), model));
        mViewPager.invalidate();
        mViewPager.setCurrentItem(position);
        super.onConfigurationChanged(newConfig);
    }

    private CustomFragmentPagerAdapter getNewAdapter(FragmentManager frag, int sectionNumber, Activity activity, Model model){
        for(int i=0;i<accountList.size();i++){ //find the account in the drawerList
            if(drawerList.get(sectionNumber).getKey() == accountList.get(i).getKey()){
                sectionNumber = i;
                break;
            }
        }
        int key = accountList.get(sectionNumber).getKey(); //compute the balance of the account

        CustomFragmentPagerAdapter pager = new CustomFragmentPagerAdapter(getChildFragmentManager(), sectionNumber, key, getActivity(), model);
        return pager;
    }
}

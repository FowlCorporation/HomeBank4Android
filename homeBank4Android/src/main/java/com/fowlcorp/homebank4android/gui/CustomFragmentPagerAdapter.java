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

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private ArrayList<Fragment> fragList;
	private MainActivity activity;
    private ArrayList<Account> accountList; //the list of account
    private ArrayList<DrawerItem> drawerList; //the draweritem list
	
	public CustomFragmentPagerAdapter(FragmentManager mFragmentManager, int position, MainActivity activity) {
		super(mFragmentManager);
		
		this.activity = activity;
        drawerList = activity.getDrawerList();
        accountList = activity.getAccountList();
        for(int i=0;i<accountList.size();i++){ //find the account in the drawerlist
            if(drawerList.get(position).getKey() == accountList.get(i).getKey()){
                position = i;
            }
        }
        int key = accountList.get(position).getKey(); //compute the balance of the account
        activity.getModel().setSelectedAccount(key);
		
		fragList = new ArrayList<Fragment>();
		fragList.add(AccountFragment.newInstance(key, activity, AccountFragment.DISPLAY_ALL));
		fragList.add(AccountFragment.newInstance(key, activity, AccountFragment.DISPLAY_PAID));
		fragList.add(AccountFragment.newInstance(key, activity, AccountFragment.DISPLAY_UNPAID));
        fragList.add(AccountFragment.newInstance(key, activity, AccountFragment.DISPLAY_REMIND));
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragList.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
        case 0:
            return activity.getString(R.string.All);
        case 1:
            return activity.getString(R.string.Paid);
        case 2:
            return activity.getString(R.string.Unpaid);
        case 3:
            return activity.getString(R.string.Remind);

    }
 
    return null;
	}

}

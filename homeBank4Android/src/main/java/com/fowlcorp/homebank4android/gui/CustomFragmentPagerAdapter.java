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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fowlcorp.homebank4android.MainActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;

import android.app.Activity;

import java.util.ArrayList;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragList;
	private Activity activity;
    private Model model;

	public CustomFragmentPagerAdapter(FragmentManager mFragmentManager, int position, int key, final Activity activity, Model model) {
		super(mFragmentManager);
        //activity.onSectionAttached(position);//notify main activity

		this.activity = activity;

       /* for(int i=0;i<accountList.size();i++){ //find the account in the drawerList
			if (drawerList.get(position).getKey() == accountList.get(i).getKey()) {
				position = i;
                break;
			}
        }*/

        model.setSelectedAccount(key);

		fragList = new ArrayList<>();
		fragList.add(AccountFragment.newInstance(position, model, AccountFragment.DISPLAY_ALL));
		fragList.add(AccountFragment.newInstance(position, model, AccountFragment.DISPLAY_PAID));
		fragList.add(AccountFragment.newInstance(position, model, AccountFragment.DISPLAY_UNPAID));
        fragList.add(AccountFragment.newInstance(position, model, AccountFragment.DISPLAY_REMIND));

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

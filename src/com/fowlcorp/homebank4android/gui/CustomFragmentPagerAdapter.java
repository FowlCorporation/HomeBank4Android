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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private ArrayList<Fragment> fragList;
	private MainActivity activity;
	
	public CustomFragmentPagerAdapter(FragmentManager mFragmentManager, int position, MainActivity activity) {
		super(mFragmentManager);
		
		this.activity = activity;
		
		fragList = new ArrayList<Fragment>();
		fragList.add(AccountFragment.newInstance(position, activity, true, false));
		fragList.add(AccountFragment.newInstance(position, activity, false, true));
		fragList.add(AccountFragment.newInstance(position, activity, false, false));
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
            return activity.getString(R.string.all_operations);
        case 1:
            return activity.getString(R.string.payed_operations);
        case 2:
            return activity.getString(R.string.unpayed_operation);
    }
 
    return null;
	}

}

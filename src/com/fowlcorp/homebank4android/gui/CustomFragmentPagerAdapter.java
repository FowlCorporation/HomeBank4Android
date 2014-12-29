package com.fowlcorp.homebank4android.gui;

import java.util.ArrayList;

import com.fowlcorp.homebank4android.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private ArrayList<Fragment> fragList;
	
	public CustomFragmentPagerAdapter(FragmentManager mFragmentManager, int position, MainActivity activity) {
		super(mFragmentManager);
		
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
            return "Toutes";
        case 1:
            return "Rapprochée";
        case 2:
            return "Non rapprochée";
    }
 
    return null;
	}

}

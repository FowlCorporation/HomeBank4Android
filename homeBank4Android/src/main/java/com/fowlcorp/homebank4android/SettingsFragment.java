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

package com.fowlcorp.homebank4android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;


public class SettingsFragment extends PreferenceFragment {

	static final int DROP_PATH_OK = 1000;
	private SharedPreferences sharedPreferences; //preferences of the app

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.fragment_settings);
		findPreference("dropPath").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(getActivity(), FileChooserActivity.class);
				startActivityForResult(intent, DROP_PATH_OK); //start an activity to select a valide file
				return false;
			}
		});


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { //called when an activity whith result ends
		if (requestCode == DROP_PATH_OK) {
			if (resultCode == Activity.RESULT_OK) { //if the filechooser end correctly
				String result = data.getStringExtra("pathResult"); //store the new path in the preferences
				EditTextPreference dropPath = (EditTextPreference) findPreference("dropPath");
				//dropPath.setText(result);
				dropPath.getEditText().getText().clear();
				dropPath.getEditText().getText().append(result);
				dropPath.getEditText().invalidate();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}

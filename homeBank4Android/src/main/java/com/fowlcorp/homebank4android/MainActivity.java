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
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.fowlcorp.homebank4android.gui.AccountFragment;
import com.fowlcorp.homebank4android.gui.DrawerItem;
import com.fowlcorp.homebank4android.gui.OverviewFragment;
import com.fowlcorp.homebank4android.gui.PagerSwipeFragment;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.AccountType;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.DataParser;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	static final int DROP_PATH_OK = 1000;
	static final int FIRST_OK = 2000;
	static final int SETTINGS_OK = 3000;

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle; //the title of the current fragment
	private Model model; //datamodel
	private ArrayList<Account> accountList; //a list of the accounts
	private ArrayList<String> bankList; //a list of the bank name
	private ArrayList<DrawerItem> drawerList; //a list of the object to diplay in the drawer
	private DataParser dp; //the parser of the file
	private SharedPreferences sharedPreferences; //preferences of the app
	private ProgressBar progressBar;
	private DrawerLayout drawerLayout;

	private Toolbar toolBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

		super.onCreate(savedInstanceState); //restore the saved state


		setContentView(R.layout.toolbar_layout); //invoke the layout

		toolBar = (Toolbar) findViewById(R.id.toolbar);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


		setSupportActionBar(toolBar);

		//invoke the fragment
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setRetainInstance(true); //use for orientation change
		mTitle = getTitle(); //set the title of the fragment (name of the app by default)

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
			Intent intent = new Intent(getApplicationContext(), firstLaunchActivity.class);
			startActivityForResult(intent, FIRST_OK); //start an activity to select a valide file
		} else {

			AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

				@Override
				protected void onPreExecute() {
					progressBar.setVisibility(View.VISIBLE);
					drawerLayout.setVisibility(View.GONE);
					super.onPreExecute();
				}

				@Override
				protected String doInBackground(String... params) {


					try {
						dropBoxCall();
						doTEst();
					} catch (Exception e) {
						e.printStackTrace();
					}


					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					progressBar.setVisibility(View.GONE);
					drawerLayout.setVisibility(View.VISIBLE);
//					Log.d("Debug", "End of parsing");
//					Log.d("Debug", String.valueOf(model.getGrandTotalBank()));
					//model.updateGrandTotal();
					try {
						updateGUI();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			asyncTask.execute("");
		}
	}

	public boolean dropBoxCall() {
		//get the path of the file in the preference
		String pathPref = sharedPreferences.getString("dropPath", "");

		File localFile = new File(pathPref);
		try {
			dp = new DataParser(getApplicationContext(), localFile);
			return true;
		} catch (Exception e) { //exception : the file is corrupted or is not a homebank database
			Intent intent = new Intent(getApplicationContext(), DropBoxFileActivity.class);
			startActivityForResult(intent, DROP_PATH_OK); //start an activity to select a valide file
		}

		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { //called when an activity whith result ends
		if (requestCode == DROP_PATH_OK) {
			if (resultCode == Activity.RESULT_OK) { //if the filechooser end correctly
				String result = data.getStringExtra("pathResult"); //store the new path in the preferences
				sharedPreferences.edit().putString("dropPath", result).commit();
				dropBoxCall();
				doTEst();
				updateGUI();
			}
		} else if (requestCode == FIRST_OK) {
			sharedPreferences.edit().putBoolean("isFirstLaunch", false).commit();
			AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

				@Override
				protected void onPreExecute() {
					progressBar.setVisibility(View.VISIBLE);
					drawerLayout.setVisibility(View.GONE);
					super.onPreExecute();
				}

				@Override
				protected String doInBackground(String... params) {


					try {
						dropBoxCall();
						doTEst();
					} catch (Exception e) {
						e.printStackTrace();
					}


					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					progressBar.setVisibility(View.GONE);
					drawerLayout.setVisibility(View.VISIBLE);
//					Log.d("Debug", "End of parsing");
//					Log.d("Debug", String.valueOf(model.getGrandTotalBank()));
					//model.updateGrandTotal();
					try {
						updateGUI();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			asyncTask.execute("");

		} else if (requestCode == SETTINGS_OK) {
			updateGUI();
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) { //when a drawer item is selected
		// update the main content by replacing fragments
		try {
			System.out.println("replace fragment");
			FragmentManager fragmentManager = getSupportFragmentManager(); //get the fragment manager
			FragmentTransaction tx = fragmentManager.beginTransaction(); //begin a transaction
			if(model == null) { // model is not set yet
				return;
			}
			if (drawerList.get(position).isOverview()) { //if the item is the overview
                tx.replace(R.id.container, OverviewFragment.newInstance(model), "overview").commitAllowingStateLoss(); //invoke the overview fragment
			} else { //if it is an account
				PagerSwipeFragment pagerFrag = PagerSwipeFragment.newInstance(position, model);
				/*pagerFrag.getView().setFocusableInTouchMode(true);
				pagerFrag.getView().requestFocus();
				pagerFrag.getView().setOnKeyListener(new View.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							Log.i("tag", "onKey Back listener is working!!!");
							updateGUI();
							return true;
						}
						return false;
					}
				});*/
				tx.replace(R.id.container, pagerFrag).commit(); //invoke the account fragment
			}
		} catch (Exception e) {
			e.printStackTrace(); //debug
		}
	}


	public void onSectionAttached(int number) {
		mTitle = drawerList.get(number).getItemName(); //replace the current title bu the title of the fragment
	}

	public void restoreActionBar() {
		toolBar.setTitle(mTitle);
		//actionBar.setDisplayShowTitleEnabled(true); //display the title
		//actionBar.setTitle(mTitle); //set the title
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
		if (f instanceof PagerSwipeFragment) {
			updateGUI();
		} else if (f instanceof AccountFragment) {
			updateGUI();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) { //the settings button is selected
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivityForResult(intent, SETTINGS_OK); //start the activity of preferences
			return true;
		}
		if (id == R.id.action_about) { //the settings button is selected
			Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
			startActivity(intent); //start the activity of preferences
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void doTEst() {
		model = new Model(); //create the datamodel
		try { //add data to the model
			model.setAccounts(dp.parseAccounts());
			model.setCategories((dp.parseCategories()));
			model.setPayees(dp.parsePayees());
			model.setTags(dp.parseTags());
			model.setOperations(dp.parseOperations(model.getAccounts(), model.getCategories(), model.getPayees()));

			//create the list of account with the model
			accountList = new ArrayList<>(model.getAccounts().values());

			//debug
			/*System.err.println("Comptes " + model.getAccounts().size());
			System.err.println("Cat " + model.getCategories().size());
            System.err.println("Payees " + model.getPayees().size());
            System.err.println("Tags " + model.getTags().size());
            System.err.println("Opes " + model.getOperations().size());*/
		} catch (Exception e) {
			System.err.println(e);
			accountList = new ArrayList<>();//if the model is empty create an empty list
		}

		for (Account acc : model.getAccounts().values()) {
			model.updateOperationAccountBalance(acc.getKey());
		}

		bankList = new ArrayList<>(); //create a list of bank name
		drawerList = new ArrayList<>(); //create the list of the drawer items

		for (int i = 0; i < accountList.size(); i++) {//add data to the bank list
			if (!bankList.contains(getNameByType(accountList.get(i).getType()))) { //if the list doesn't contain the bank of this item
				bankList.add(getNameByType(accountList.get(i).getType())); //add the bankname to the list
			}
		}
		//add the overview item to the drawerlist
		drawerList.add(new DrawerItem(getResources().getString(R.string.Overview), R.drawable.overview, true, false));
		//add data to the drawerlist
		for (int i = 0; i < bankList.size(); i++) {//for each bank name
			if (bankList.get(i).equals(getNameByType(AccountType.BANK))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.bank, false, true));//add the bank to the drawer as a title
			} else if (bankList.get(i).equals(getNameByType(AccountType.CASH))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.cash_account, false, true));
			} else if (bankList.get(i).equals(getNameByType(AccountType.ASSET))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.asset, false, true));
			} else if (bankList.get(i).equals(getNameByType(AccountType.CREDITCARD))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.card, false, true));
			} else if (bankList.get(i).equals(getNameByType(AccountType.LIABILITY))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.liability, false, true));
			} else if (bankList.get(i).equals(getNameByType(AccountType.NONE))) {
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.notype, false, true));
			} else {
				drawerList.add(new DrawerItem(bankList.get(i), -1, false, true));//add the bank to the drawer as a title
			}
			for (int j = 0; j < accountList.size(); j++) {//for each account
				if (getNameByType(accountList.get(j).getType()).equals(bankList.get(i))) { //if the account correspond to the bank name
					switch (accountList.get(j).getType()) {
						case AccountType.BANK:
							drawerList.add(new DrawerItem(accountList.get(j).getName(), -1, accountList.get(j).getKey())); //add the account in the drawer list
							break;
						case AccountType.CASH:
							drawerList.add(new DrawerItem(accountList.get(j).getName(), -1, accountList.get(j).getKey())); //add the account in the drawer list
							break;
						default:
							drawerList.add(new DrawerItem(accountList.get(j).getName(), -1, accountList.get(j).getKey())); //add the account in the drawer list
					}

				}
			}
		}
	}

	public void updateGUI() {
		mNavigationDrawerFragment.update();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("on destroy");
	}

	/*----------------------------------------------------*/
	/*getters and setters*/

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}

	public ArrayList<DrawerItem> getDrawerList() {
		return drawerList;
	}

	public void setDrawerList(ArrayList<DrawerItem> drawerList) {
		this.drawerList = drawerList;
	}

	public NavigationDrawerFragment getmNavigationDrawerFragment() {
		return mNavigationDrawerFragment;
	}

	public void setmNavigationDrawerFragment(
			NavigationDrawerFragment mNavigationDrawerFragment) {
		this.mNavigationDrawerFragment = mNavigationDrawerFragment;
	}

	public String getNameByType(int index) {
		String result = "";
		switch (index) {
			case AccountType.NONE:
				result = getString(R.string.None);
				break;
			case AccountType.BANK:
				result = getString(R.string.Bank);
				break;
			case AccountType.ASSET:
				result = getString(R.string.Asset);
				break;
			case AccountType.CREDITCARD:
				result = getString(R.string.Credit_Card);
				break;
			case AccountType.LIABILITY:
				result = getString(R.string.Liability);
				break;
			case AccountType.CASH:
				result = getString(R.string.Cash);
				break;
		}

		return result;
	}

	@Override
	public ActionBar getSupportActionBar() {
		// TODO Auto-generated method stub
		return super.getSupportActionBar();
	}


}

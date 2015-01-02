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

package com.fowlcorp.homebank4android;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;
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

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	static final int REQUEST_LINK_TO_DBX = 0;
	static final int DROP_PATH_OK = 1000;

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle; //the title of the current fragment
	private DbxAccountManager dropBoxAccountMgr;
	private DbxFileSystem dbxFs;
	private Model model; //datamodel
	private ArrayList<Account> accountList; //a list of the accounts
	private ArrayList<String> bankList; //a list of the bank name
	private ArrayList<DrawerItem> drawerList; //a list of the object to diplay in the drawer
	private DbxFile file; //the homebank file
	private DataParser dp; //the parser of the file
	private SharedPreferences sharedPreferences; //preferences of the app

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //restore the saved state

		//Connect to the dropbox api with the id and key of the dropbox app
		dropBoxAccountMgr = DbxAccountManager.getInstance(getApplicationContext(), "40u2ttil28t3g8e","sjt7o80sdtdjsxi");

		if(!dropBoxAccountMgr.hasLinkedAccount()){ //if the user has not linked to the app before
			dropBoxAccountMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX); //launch an activity to link to dropbox
		} else {
			dropBoxCall();//connect to dropbox
		}
		
		doTEst();
		
		setContentView(R.layout.activity_main); //invoke the layout

		//invoke the fragment
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setRetainInstance(true); //use for orientation change
		mTitle = getTitle(); //set the title of the fragment (name of the app by default)

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));



	}

	public boolean dropBoxCall(){
		//get the path of the file in the preference
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String pathPref = sharedPreferences.getString("dropPath", "");
		Boolean isDropPath = sharedPreferences.getBoolean("isDropPath", false);

		if(isDropPath){
			try {
				dbxFs = DbxFileSystem.forAccount(dropBoxAccountMgr.getLinkedAccount());
				DbxPath path = new DbxPath(pathPref); //create the path of the file
				file = dbxFs.open(path); //open the file
			} catch (InvalidPathException | DbxException e) { //Dropbox exception
				e.printStackTrace();
			}
			try {
				dp = new DataParser(getApplicationContext(), file);
				return true;
			} catch (Exception e) { //exception : the file is corrupted or is not a homebank database
				Intent intent = new Intent(getApplicationContext(), DropBoxFileActivity.class);
				startActivityForResult(intent, DROP_PATH_OK); //start an activity to select a valide file
			}
		} else {
			File localFile = new File(pathPref);
			try {
				dp = new DataParser(getApplicationContext(), localFile);
				return true;
			} catch (Exception e) { //exception : the file is corrupted or is not a homebank database
				Intent intent = new Intent(getApplicationContext(), DropBoxFileActivity.class);
				startActivityForResult(intent, DROP_PATH_OK); //start an activity to select a valide file
			}
		}
		
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { //called when an activity whith result ends
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) { //if the dropbox link activity end correctly
				dropBoxCall();
			} else {
			}
		} else if(requestCode == DROP_PATH_OK){
			if(resultCode == Activity.RESULT_OK){ //if the filechooser end correctly
				String result = data.getStringExtra("pathResult"); //store the new path in the preferences
				Boolean isDrop = data.getBooleanExtra("isDropPath", false);
				sharedPreferences.edit().putBoolean("isDropPath", isDrop).commit();
				sharedPreferences.edit().putString("dropPath", result).commit();
				dropBoxCall();
				doTEst();
				updateGUI();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) { //when a drawer item is selected
		// update the main content by replacing fragments
		try {
			FragmentManager fragmentManager = getSupportFragmentManager(); //get the fragment manager
			FragmentTransaction tx = fragmentManager.beginTransaction(); //begin a transaction
			if(drawerList.get(position).isOverview()){ //if the item is the overview
				tx.replace(R.id.container,OverviewFragment.newInstance(this)).commit(); //invoke the overview fragment
			} else { //if it is an account
				tx.replace(R.id.container,PagerSwipeFragment.newInstance(position, this)).commit(); //invoke the account fragment
			}
		} catch (Exception e) {
			e.printStackTrace(); //debug
		}
	}

	public void onSectionAttached(int number) {
		mTitle = drawerList.get(number).getItemName(); //replace the current title bu the title of the fragment
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar(); //get the action bar
		actionBar.setDisplayShowTitleEnabled(true); //display the title
		actionBar.setTitle(mTitle); //set the title
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) { //the settings button is selected
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent); //start the activity of preferences
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void doTEst(){
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

		bankList = new ArrayList<String>(); //create a list of bank name
		drawerList = new ArrayList<DrawerItem>(); //create the list of the drawer items

		for(int i=0;i<accountList.size();i++){//add data to the bank list
			if(!bankList.contains(getNameByType(accountList.get(i).getType()))){ //if the list doesn't contain the bank of this item
				bankList.add(getNameByType(accountList.get(i).getType())); //add the bankname to the list	
			}
		}
		//add the overview item to the drawerlist
		drawerList.add(new DrawerItem(getResources().getString(R.string.overViewDrawerItem),R.drawable.home,true,false));
		//add data to the drawerlist
		for(int i=0;i<bankList.size();i++){//for each bank name
			if(bankList.get(i).equals(getNameByType(AccountType.BANK))){
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.bank, false, true));//add the bank to the drawer as a title
			} else if(bankList.get(i).equals(getNameByType(AccountType.CASH))){
				drawerList.add(new DrawerItem(bankList.get(i), R.drawable.espece, false, true));//add the bank to the drawer as a title
			} else {
				drawerList.add(new DrawerItem(bankList.get(i), -1, false, true));//add the bank to the drawer as a title
			}
			for(int j=0;j<accountList.size();j++){//for each account
				if(getNameByType(accountList.get(j).getType()).equals(bankList.get(i))){ //if the account correspond to the bank name
					switch (accountList.get(j).getType()) {
					case AccountType.BANK:
						drawerList.add(new DrawerItem(accountList.get(j).getName(), -1,accountList.get(j).getKey())); //add the account in the drawer list
						break;
					case AccountType.CASH:
						drawerList.add(new DrawerItem(accountList.get(j).getName(), -1,accountList.get(j).getKey())); //add the account in the drawer list
						break;
					default:
						drawerList.add(new DrawerItem(accountList.get(j).getName(), -1,accountList.get(j).getKey())); //add the account in the drawer list
					}
					
				}
			}
		}
	}
	
	public void updateGUI(){
		mNavigationDrawerFragment.update();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			dbxFs.shutDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("on destroy");
		if (isFinishing()) {

		} else { 

		}
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
	
	public String getNameByType(int index){
		String result ="";
		switch (index) {
		case AccountType.NONE:
			result = getString(R.string.account_type_none);
			break;
		case AccountType.BANK:
			result = getString(R.string.account_type_bank);
			break;
		case AccountType.ASSET:
			result = getString(R.string.account_type_asset);
			break;
		case AccountType.CREDITCARD:
			result = getString(R.string.account_type_creditCard);
			break;
		case AccountType.LIABILITY:
			result = getString(R.string.account_type_liability);
			break;
		case AccountType.CASH:
			result = getString(R.string.account_type_cash);
			break;
		}
		
		return result;
	}
}

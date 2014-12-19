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


import java.util.ArrayList;
import java.util.List;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.util.FolderLoader;
import com.fowlcorp.homebank4android.gui.AccountFragment;
import com.fowlcorp.homebank4android.gui.DrawerItem;
import com.fowlcorp.homebank4android.model.Account;
import com.fowlcorp.homebank4android.model.Model;
import com.fowlcorp.homebank4android.utils.DataParser;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	/**
	 * 
	 */
	private DbxAccountManager dropBoxAccountMgr;
	static final int REQUEST_LINK_TO_DBX = 0;
	private DbxFileSystem dbxFs;
	private Model model;
	private ArrayList<Account> accountList;
	private ArrayList<String> bankList;
	private ArrayList<DrawerItem> drawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doTEst();
		//		dropBoxAccountMgr = DbxAccountManager.getInstance(getApplicationContext(), "40u2ttil28t3g8e", 	
		//				"sjt7o80sdtdjsxi");
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setRetainInstance(true);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		//		if(!dropBoxAccountMgr.hasLinkedAccount()){
		//		dropBoxAccountMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		//		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				// ... Start using Dropbox files.
			} else {
				// ... Link failed or was cancelled by the user.
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		if(drawerList.get(position).isOverview()){

		} else if(drawerList.get(position).isHeader()){

		} else {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction tx = fragmentManager.beginTransaction();
			tx.replace(R.id.container,AccountFragment.newInstance(position)).commit();
		}
	}

	public void onSectionAttached(int number) {
		/*switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}*/
		mTitle = drawerList.get(number).getItemName();
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
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
		if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void doTEst(){
		System.out.println("run the test \0/");
		DataParser dp = new DataParser(getApplicationContext());
		//dp.runExample();
		model = new Model();
		model.setAccounts(dp.parseAccounts());
		model.setCategories((dp.parseCategories()));
		model.setPayees(dp.parsePayees());
		model.setOperations(dp.parseOperations(model.getAccounts(), model.getCategories(), model.getPayees()));

		accountList = new ArrayList<>(model.getAccounts().values());

		bankList = new ArrayList<String>();
		drawerList = new ArrayList<DrawerItem>();

		for(int i=0;i<accountList.size();i++){
			if(!bankList.contains(accountList.get(i).getBankName())){
				bankList.add(accountList.get(i).getBankName());
			}
		}
		drawerList.add(new DrawerItem(getResources().getString(R.string.overViewDrawerItem),
				-1,
				true,
				false));

		for(int i=0;i<bankList.size();i++){
			drawerList.add(new DrawerItem(bankList.get(i), -1, false, true));
			for(int j=0;j<accountList.size();j++){
				if(accountList.get(j).getBankName().equals(bankList.get(i))){
					drawerList.add(new DrawerItem(accountList.get(j).getName(), -1, false, false));
				}
			}
		}

		//		try {
			//			DbxFileSystem dbxFs = DbxFileSystem.forAccount(dropBoxAccountMgr.getLinkedAccount());
		//			List<DbxFileInfo> infos = dbxFs.listFolder(new DbxPath("/Bibichette/HomeBank Martin"));
		//			System.out.println("liste des fichier");
		//			for (DbxFileInfo info : infos) {
		//                System.out.println("    " + info.path + ", " + info.modifiedTime + '\n');
		//            }
		//			DbxPath path = new DbxPath("/Bibichette/HomeBank Martin/banque_martin.txt");
		//			FolderLoader folderLoader = new FolderLoader(getApplicationContext(), dropBoxAccountMgr, path);
		//			DbxFile file = dbxFs.open(path);
		//		} catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}


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

	@Override
	  protected void onDestroy() {
	    super.onDestroy();
	    System.out.println("on destroy");
	    if (isFinishing()) {
	      
	    } else { 
	      
	    }
	  }


}
